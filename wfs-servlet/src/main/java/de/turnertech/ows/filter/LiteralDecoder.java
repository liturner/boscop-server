package de.turnertech.ows.filter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import de.turnertech.ows.common.OwsContext;

/**
 * Leaf Decoder
 * 
 * Note, must consume the input to its return element
 */
class LiteralDecoder {
    
    private LiteralDecoder() {

    }

    public static Literal decode(final XMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        
        System.out.println(">" + "Literal");

        // This consumes the element and content, moving us to the element
        final String elementText = in.getElementText();
        Object typedObject = null;

        try {
            typedObject = Long.valueOf(elementText);
        } catch (Exception e) {
            // Do Nothing
        }

        if(typedObject == null) {
            try {
                typedObject = Double.valueOf(elementText);
            } catch (Exception e) {
                // Do Nothing
            }

            if(typedObject == null) {
                typedObject = elementText;
            }
        }

        final Literal returnLiteral = new Literal(typedObject);

        return returnLiteral;
    }

}
