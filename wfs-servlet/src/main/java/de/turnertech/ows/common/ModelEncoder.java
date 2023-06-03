package de.turnertech.ows.common;

import java.io.OutputStream;

import javax.xml.stream.XMLStreamException;

public interface ModelEncoder {
    
    public void encode(Model model, OutputStream out, OwsContext owsContext, OwsRequestContext requestContext) throws XMLStreamException;

}
