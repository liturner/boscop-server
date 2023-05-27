package de.turnertech.thw.cop.ows.filter;

import org.w3c.dom.Node;

public class OgcFilterDecoder {
    
    public static OgcFilter getFilter(Node ogcFilter) {
        OgcFilter returnFilter = new OgcFilter();

        for(int i = 0; i < ogcFilter.getChildNodes().getLength(); ++i) {
            Node filterPart = ogcFilter.getChildNodes().item(i);
            
            if("FeatureId".equals(filterPart.getNodeName())) {
                returnFilter.getFeatureIdFilters().add(filterPart.getAttributes().getNamedItem("fid").getNodeValue());
            }
        }

        return returnFilter;
    }

}
