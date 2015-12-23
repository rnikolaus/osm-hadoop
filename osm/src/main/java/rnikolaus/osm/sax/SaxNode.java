/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rnikolaus.osm.sax;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.xml.sax.Attributes;

/**
 *
 * @author rapnik
 */
public class SaxNode {
    protected HashMap<String,String> stuff = new HashMap<>();

    public SaxNode(Attributes atts) {
        List<String> filterList = getFilter();
         for ( int i = 0; i < atts.getLength(); i++ ){
             final String name = atts.getQName( i ).replace(";", ":").trim();
             if (filterList.contains(name)) continue;
              String value = atts.getValue( i ).replace(";", ":").trim();
             if (name.equals("id"))value = getPrefix()+value;
             stuff.put(name, value);
         }
    }
    protected  List<String> getFilter(){
        return Collections.EMPTY_LIST;
    }
    protected String getPrefix(){
        return "";
    }
    
    public String get(String key){
        return stuff.get(key);
    }
    public boolean contains(String value){
        return stuff.values().contains(value);
    }
    public boolean containsStartingWith(String value){
        for (String s: stuff.values()){
            if (s.startsWith(value)) return true;
        }
        return false;
    }

    @Override
    public String toString() {  
       return stuff.toString();
    }
    
    
}
