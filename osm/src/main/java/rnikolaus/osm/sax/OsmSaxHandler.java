/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rnikolaus.osm.sax;

import rnikolaus.osm.format.OsmWay;
import rnikolaus.osm.format.OsmMember;
import rnikolaus.osm.format.OsmTag;
import rnikolaus.osm.format.OsmRelation;
import rnikolaus.osm.format.OsmNode;
import rnikolaus.osm.format.OsmRefNode;
import java.util.concurrent.LinkedBlockingQueue;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author rapnik
 */
public class OsmSaxHandler extends DefaultHandler{
    
    
    OsmNode currentNode;
    OsmWay currentWay;
    OsmRelation currentRelation;

    LinkedBlockingQueue<OsmNode> result;
   
    public OsmSaxHandler(LinkedBlockingQueue<OsmNode> result) {
        this.result = result;
    }
    

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("node")){
            currentNode = new OsmNode(attributes);
        }else if (qName.equalsIgnoreCase("tag")){
            final OsmTag osmTag = new OsmTag(attributes);
            if (currentNode!=null){
                currentNode.addTag(osmTag);
            }else if (currentWay!=null){
                currentWay.addTag(osmTag);
            }else if (currentRelation!=null){
                currentRelation.addTag(osmTag);
            }
        }else if (qName.equalsIgnoreCase("way")){
            currentWay=new OsmWay(attributes);
        }else if (qName.equalsIgnoreCase("nd")){
            currentWay.addNode(new OsmRefNode(attributes));
        } else if (qName.equalsIgnoreCase("relation")){
            currentRelation = new OsmRelation(attributes);
            
        } else if (qName.equalsIgnoreCase("member")){
            currentRelation.addMember(new OsmMember(attributes));
        }
        //else System.out.println(qName);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("node")){
            result.offer(currentNode);
            
            currentNode = null;
        }else if (qName.equalsIgnoreCase("way")){
            result.offer(currentWay);
            currentWay = null;
        }else if (qName.equalsIgnoreCase("relation")){
            result.offer(currentRelation);
            currentRelation = null;
        }
        
    }

    
    

    
    
    
    
}
