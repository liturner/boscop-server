package de.turnertech.thw.cop.gml;

import org.w3c.dom.Node;

public interface GmlDecoder<T extends GmlElement> {
    
    public T decode(Node root);

}
