package de.turnertech.ows.gml;

import org.w3c.dom.Node;

public interface GmlDecoder<T extends GmlElement> {
    
    public T decode(Node root, GmlDecoderContext context);

}
