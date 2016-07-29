
package rnikolaus.osm.format;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import org.xml.sax.Attributes;

/**
 *
 * @author rapnik
 */
public class OsmWay extends OsmNode{
    List<String> l = new ArrayList<>();

    public OsmWay(Attributes atts) {
        super(atts);
    }
    public void addNode(OsmRefNode node){
        l.add(node.toString());
    }

    @Override
    protected String getPrefix() {
        return "way";
    }
    

   
    @Override
    public String toString() {
        JSONObject result = new JSONObject(getResult());
        result.put("refs", l);
        return result.toString();
    }
    
}
