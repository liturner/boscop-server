package de.turnertech.ows.filter;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;

import de.turnertech.ows.common.DepthXMLStreamReader;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlDecoder;

public class FilterDecoder implements XmlDecoder<Filter> {

    static final FilterDecoder I = new FilterDecoder();

    private FilterDecoder() {}

    @Override
    public boolean canDecode(DepthXMLStreamReader in) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canDecode'");
    }

    @Override
    public Filter decode(final DepthXMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        System.out.println("Filter Decoder");
        Operator filter = null;
        while(in.hasNext()) {
            int xmlEvent = in.next();

                //Process start element.
            if (xmlEvent == XMLStreamConstants.START_ELEMENT) {
                System.out.println("Start Element: " + in.getLocalName());

                if(!"Filter".equals(in.getLocalName())) {
                    filter = OperatorDecoder.I.decode(in, owsContext);
                }
            }

            if (xmlEvent == XMLStreamConstants.END_ELEMENT) {
                System.out.println("End Element: " + in.getLocalName());
            }
        }
        return new Filter(filter);
    }
    
}
