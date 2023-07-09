package de.turnertech.ows.servlet;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;

import de.turnertech.ows.common.DepthXMLStreamReader;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlDecoder;
import de.turnertech.ows.parameter.OwsServiceValue;
import de.turnertech.ows.parameter.WfsVersionValue;

public class TransactionDecoder implements XmlDecoder<Transaction> {

    public static final TransactionDecoder I = new TransactionDecoder();

    private TransactionDecoder() {}

    @Override
    public boolean canDecode(final DepthXMLStreamReader in) {
        return Transaction.QNAME.equals(in.getName());
    }

    @Override
    public Transaction decode(final DepthXMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        final int myDepth = in.getDepth() - 1;
        final Transaction returnObject = new Transaction();

        for(int i = 0; i < in.getAttributeCount(); ++i) {
            final QName attributeName = in.getAttributeName(i);
            if(attributeName.getLocalPart().equals("service")) {
                returnObject.setService(OwsServiceValue.valueOfIgnoreCase(in.getAttributeValue(i)));
            } else if(attributeName.getLocalPart().equals("version")) {
                returnObject.setVersion(WfsVersionValue.valueOfIgnoreCase(in.getAttributeValue(i)));
            }
        }

        while(in.hasNext()) {
            int xmlEvent = in.next();

            if (xmlEvent == XMLStreamConstants.START_ELEMENT) {
                if(InsertDecoder.I.canDecode(in)) {
                    returnObject.getActions().add(InsertDecoder.I.decode(in, owsContext));
                }
            } else if (xmlEvent == XMLStreamConstants.END_ELEMENT && in.getDepth() == myDepth) {
                break;
            }
        }

        return returnObject;
    }
    
}
