package de.turnertech.ows.filter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlEncoder;

/**
 * Delegating Encoder
 */
public class ExpressionEncoder implements XmlEncoder<Expression> {
    
    static final ExpressionEncoder I = new ExpressionEncoder();

    private ExpressionEncoder() {}

    @Override
    public void encode(final XMLStreamWriter out, final Expression expression, final OwsContext owsContext) throws XMLStreamException {
        if(expression instanceof Literal) {
            LiteralEncoder.encode(out, (Literal)expression, owsContext);
        }
        if(expression instanceof ValueReference) {
            ValueReferenceEncoder.I.encode(out, (ValueReference)expression, owsContext);
        }
    }

}
