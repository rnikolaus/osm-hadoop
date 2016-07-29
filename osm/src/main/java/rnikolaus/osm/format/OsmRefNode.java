
package rnikolaus.osm.format;

import org.xml.sax.Attributes;
import rnikolaus.osm.sax.SaxNode;

/**
 *
 * @author rapnik
 */
public class OsmRefNode extends SaxNode{

    public OsmRefNode(Attributes atts) {
        super(atts);
    }

    @Override
    public String toString() {
        return "node"+get("ref");        
    }
    
    
    
}
