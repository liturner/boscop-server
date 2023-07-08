package de.turnertech.ows.common;

import javax.xml.stream.XMLStreamException;

public interface XmlDecoder<T> {
    
    boolean canDecode(final DepthXMLStreamReader in);

    T decode(final DepthXMLStreamReader in, final OwsContext owsContext) throws XMLStreamException;

}
