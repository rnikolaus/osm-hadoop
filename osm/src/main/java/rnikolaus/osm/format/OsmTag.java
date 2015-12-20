/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rnikolaus.osm.format;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import rnikolaus.osm.sax.SaxNode;

/**
 *
 * @author rapnik
 */
public class OsmTag extends SaxNode{

    public OsmTag(Attributes atts) {
        super(atts);
    }
    @Override
    protected List<String> getFilter() {
        ArrayList<String> l = new ArrayList<>();
        l.add("created_by");
        l.add("converted_by");
        return l;
    }
    public String getKey(){
        return get("k");
    }
    public String getValue(){
        return get("v");
    }
    
}
