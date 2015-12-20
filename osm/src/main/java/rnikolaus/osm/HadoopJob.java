/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rnikolaus.osm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 *
 * @author rapnik
 */
public class HadoopJob {

    public static class KeyToValueMapper
            extends Mapper<Text, NullWritable, NullWritable, Text> {

        public void map(Text key, Object value, Context context
        ) throws IOException, InterruptedException {
            context.write(NullWritable.get(), key);
        }
    }

    public static void main(String[] args) throws Exception {
        Job job = Job.getInstance();
        job.setJarByClass(HadoopJob.class);
        job.setJobName("OSMTest");
        job.setMapperClass(KeyToValueMapper.class);
        job.setNumReduceTasks(0);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);
        job.setInputFormatClass(InputFormat.class);
        List<String> other_args = new ArrayList<>();
        for (int i = 0; i < args.length; ++i) {
            System.out.println(args[i]);
            other_args.add(args[i]);
        }

        FileInputFormat.setInputPaths(job, new Path(other_args.get(0)));
        FileOutputFormat.setOutputPath(job, new Path(other_args.get(1)));

        job.waitForCompletion(true);
        System.exit(0);
    }
}
