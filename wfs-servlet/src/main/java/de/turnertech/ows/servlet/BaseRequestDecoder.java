package de.turnertech.ows.servlet;

import javax.xml.stream.XMLStreamException;

import de.turnertech.ows.common.DepthXMLStreamReader;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlDecoder;

public class BaseRequestDecoder implements XmlDecoder<BaseRequest> {

    @Override
    public boolean canDecode(DepthXMLStreamReader in) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canDecode'");
    }

    @Override
    public BaseRequest decode(DepthXMLStreamReader in, OwsContext owsContext) throws XMLStreamException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'decode'");
    }
    
}
