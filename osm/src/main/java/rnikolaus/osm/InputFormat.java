/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rnikolaus.osm;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import rnikolaus.osm.format.OsmNode;

/**
 *
 * @author rapnik
 */
public class InputFormat extends FileInputFormat<Text, NullWritable> {

    private long start;
    private long end;
    private FSDataInputStream fsin;

    @Override
    public RecordReader<Text, NullWritable> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        return new RecordReader<Text, NullWritable>() {

            private OsmNode currentOsmNode;
            private Thread parserThread;
            private final LinkedBlockingQueue<OsmNode> linkedBlockingQueue = new LinkedBlockingQueue<>(1000);

            @Override
            public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
                FileSplit fileSplit = (FileSplit) split;
                start = fileSplit.getStart();
                end = start + fileSplit.getLength();
                Path file = fileSplit.getPath();
                FileSystem fs = file.getFileSystem(context.getConfiguration());
                fsin = fs.open(fileSplit.getPath());
                fsin.seek(start);
                final BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(fsin);
                parserThread = new Thread(new OsmParser(linkedBlockingQueue, bzIn));
                parserThread.start();
            }

            @Override
            public boolean nextKeyValue() throws IOException, InterruptedException {
                OsmNode n;
                while ((n = linkedBlockingQueue.poll(30, TimeUnit.SECONDS)) != null || parserThread.isAlive()) {
                    if (n != null) {
                        currentOsmNode = n;
                        return true;
                    }
                }
                return false;
            }

            @Override
            public Text getCurrentKey() throws IOException, InterruptedException {
                return new Text(currentOsmNode.toString());
            }

            @Override
            public NullWritable getCurrentValue() throws IOException, InterruptedException {
                return NullWritable.get();
            }

            @Override
            public float getProgress() throws IOException, InterruptedException {
                return (fsin.getPos() - start) / (float) (end - start);
            }

            @Override
            public void close() throws IOException {
                fsin.close();
            }
        };
    }

}
