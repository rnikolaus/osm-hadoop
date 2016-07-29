
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
        return get("type").toString()+get("ref").toString();
    }
    
    
    
}
