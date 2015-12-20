/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rnikolaus.osm.format;

import org.xml.sax.Attributes;
import rnikolaus.osm.sax.SaxNode;

/**
 *
 * @author rapnik
 */
public class OsmMember extends SaxNode{

    public OsmMember(Attributes atts) {
        super(atts);
    }

    @Override
    public String toString() {
        return get("type")+get("ref");
    }
    
    
    
}
