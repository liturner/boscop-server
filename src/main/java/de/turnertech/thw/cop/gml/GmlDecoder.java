package de.turnertech.thw.cop.gml;

import java.util.List;

import org.w3c.dom.Node;

public interface GmlDecoder {
    
    public List<GmlElement> decode(Node root);

}
