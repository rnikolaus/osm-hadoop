/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rnikolaus.osm.sax;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import org.xml.sax.Attributes;

/**
 *
 * @author rapnik
 */
public class SaxNode {

    protected HashMap<String, Object> stuff = new HashMap<>();
    Pattern num = Pattern.compile("-?\\d+\\.\\d+");
    NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);

    public SaxNode(Attributes atts) {
        List<String> filterList = getFilter();
        for (int i = 0; i < atts.getLength(); i++) {
            final String name = atts.getQName(i).replace(";", ":").trim();
            if (filterList.contains(name)) {
                continue;
            }
            String value = atts.getValue(i).replace(";", ":").trim();
            if (name.equals("id")) {
                value = getPrefix() + value;
            } else if (num.matcher(value).matches()) {
                try {
                    stuff.put(name, nf.parse(value));
                    continue;
                } catch (ParseException ex) {
                    //shouldnt happen, but we'll ignore it anyway
                }
            }
            stuff.put(name, value);
        }
    }

    protected List<String> getFilter() {
        return Collections.EMPTY_LIST;
    }

    protected String getPrefix() {
        return "";
    }

    public Object get(String key) {
        return stuff.get(key);
    }

    public boolean contains(String value) {
        return stuff.values().contains(value);
    }

    public boolean containsStartingWith(String value) {
        for (Object o : stuff.values()) {
            if (o instanceof String) {
                if (((String) o).startsWith(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return stuff.toString();
    }

}
