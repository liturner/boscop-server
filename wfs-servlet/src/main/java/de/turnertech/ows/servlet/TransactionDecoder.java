package de.turnertech.ows.servlet;

import javax.xml.stream.XMLStreamException;

import de.turnertech.ows.common.DepthXMLStreamReader;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlDecoder;

public class TransactionDecoder implements XmlDecoder<Transaction> {

    @Override
    public boolean canDecode(DepthXMLStreamReader in) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canDecode'");
    }

    @Override
    public Transaction decode(DepthXMLStreamReader in, OwsContext owsContext) throws XMLStreamException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'decode'");
    }
    
}
