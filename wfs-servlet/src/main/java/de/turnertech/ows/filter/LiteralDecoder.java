package de.turnertech.ows.filter;

import javax.xml.stream.XMLStreamException;

import de.turnertech.ows.common.DepthXMLStreamReader;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlDecoder;

/**
 * Leaf Decoder
 * 
 * Note, must consume the input to its return element
 */
class LiteralDecoder implements XmlDecoder<Literal> {
    
    static final LiteralDecoder I = new LiteralDecoder();

    private LiteralDecoder() {}

    @Override
    public boolean canDecode(DepthXMLStreamReader in) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canDecode'");
    }

    @Override
    public Literal decode(final DepthXMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        
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
