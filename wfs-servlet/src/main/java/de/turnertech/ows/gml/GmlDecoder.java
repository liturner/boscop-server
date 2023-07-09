package de.turnertech.ows.gml;

import org.w3c.dom.Node;

@Deprecated
public interface GmlDecoder<T extends GmlElement> {
    
    @Deprecated
    public T decode(Node root, GmlDecoderContext context);

}
