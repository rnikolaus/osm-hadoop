/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rnikolaus.osm.format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject;
import org.xml.sax.Attributes;
import rnikolaus.osm.sax.SaxNode;

/**
 *
 * @author rapnik
 */
public class OsmNode extends SaxNode{
    List<OsmTag> tags = new ArrayList<>();

    public OsmNode(Attributes atts) {
        super(atts);
    }
    public void addTag(OsmTag tag){
        if(
//                !tag.contains("created_by")
//                &&!tag.contains("converted_by")
//                &&!tag.containsStartingWith("TMC:")
//                &&!tag.contains("source")
                tag.containsStartingWith("addr:")||tag.contains("postal_code")
                ){
            tags.add(tag);
        }
    }

    @Override
    protected String getPrefix() {
        return "node";
    }
    
    

    @Override
    protected List<String> getFilter() {
        ArrayList<String> l = new ArrayList<>();
        l.add("version");
        l.add("timestamp");
        l.add("changeset");
        l.add("uid");
        l.add("user");
        return l;
    }

    public boolean hasTags(){
        return !tags.isEmpty();
    }
    private Map<String,String> createTagMap(){
        HashMap<String,String> result = new HashMap<>();
        for (OsmTag tag:tags){
            result.put(tag.getKey(), tag.getValue());
        }
        return result;
    }
    
    
    @Override
    public String toString() {
        JSONObject result = new JSONObject(getResult());
        return result.toString(); //To change body of generated methods, choose Tools | Templates.
    }

    protected HashMap getResult() {
        HashMap result = new HashMap(stuff);
        result.put("tags", createTagMap());
        return result;
    }
    
        
    
    
    
    
}
