/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rnikolaus.osm;

import rnikolaus.osm.format.OsmNode;
import java.io.FileInputStream;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;


/**
 *
 * @author rapnik
 */
public class NewClass {

    public static void main(String[] args) throws Exception {

//        int buffersize =1024;
        FileInputStream in = new FileInputStream("niederbayern-latest.osm.bz2");
        final BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(in);

        final LinkedBlockingQueue<OsmNode> linkedBlockingQueue = new LinkedBlockingQueue<>(1000);

        Thread t = new Thread(new OsmParser(linkedBlockingQueue, bzIn));
        t.start();

        OsmNode s;
        while ((s = linkedBlockingQueue.poll(30, TimeUnit.SECONDS)) != null || t.isAlive()) {
            if (s != null) {
                if (s.hasTags()) {
                    System.out.println(s);
                }
            }
        }

        bzIn.close();

    }

}
