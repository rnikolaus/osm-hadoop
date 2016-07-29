
package rnikolaus.osm.format;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import org.xml.sax.Attributes;

/**
 *
 * @author rapnik
 */
public class OsmRelation extends OsmNode{
    List<String> l = new ArrayList<>();
    

    public OsmRelation(Attributes atts) {
        super(atts);
    }
    public void addMember(OsmMember member){
        l.add(member.toString());
    }

    @Override
    protected String getPrefix() {
        return "relation";
    }

    
    @Override
    public String toString() {
        JSONObject result = new JSONObject(getResult());
        result.put("member", l);
        return result.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
