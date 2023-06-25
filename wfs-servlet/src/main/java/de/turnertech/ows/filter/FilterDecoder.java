package de.turnertech.ows.filter;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import de.turnertech.ows.common.OwsContext;

public class FilterDecoder {

    public static Filter decode(final XMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        System.out.println("Filter Decoder");
        Operator filter = null;
        while(in.hasNext()) {
            int xmlEvent = in.next();

                //Process start element.
            if (xmlEvent == XMLStreamConstants.START_ELEMENT) {
                System.out.println("Start Element: " + in.getLocalName());

                if(!"Filter".equals(in.getLocalName())) {
                    filter = OperatorDecoder.decode(in, owsContext);
                }
            }

            if (xmlEvent == XMLStreamConstants.END_ELEMENT) {
                System.out.println("End Element: " + in.getLocalName());
            }
        }
        return new Filter(filter);
    }
    
}
