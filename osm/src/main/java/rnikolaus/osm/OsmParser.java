/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rnikolaus.osm;

import java.io.InputStream;
import java.util.concurrent.LinkedBlockingQueue;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import rnikolaus.osm.format.OsmNode;
import rnikolaus.osm.sax.OsmSaxHandler;

/**
 *
 * @author rapnik
 */
public class OsmParser implements Runnable{
    private final LinkedBlockingQueue<OsmNode> linkedBlockingQueue;
    private final InputStream inputStream;

    public OsmParser(LinkedBlockingQueue<OsmNode> linkedBlockingQueue, InputStream inputStream) {
        this.linkedBlockingQueue = linkedBlockingQueue;
        this.inputStream = inputStream;
    }
    
            @Override
            public void run() {
                try {
                    SAXParserFactory factory = SAXParserFactory.newInstance();
                    SAXParser saxParser = factory.newSAXParser();
                    OsmSaxHandler osmSaxHandler = new OsmSaxHandler(linkedBlockingQueue);
                    saxParser.parse(inputStream, osmSaxHandler);
                    inputStream.close();

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }

        
    
}
