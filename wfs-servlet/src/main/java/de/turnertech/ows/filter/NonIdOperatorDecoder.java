package de.turnertech.ows.filter;

import java.util.Arrays;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import de.turnertech.ows.common.DepthXMLStreamReader;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlDecoder;

/**
 * Delegating Decoder
 */
public class NonIdOperatorDecoder implements XmlDecoder<NonIdOperator> {
    
    static final NonIdOperatorDecoder I = new NonIdOperatorDecoder();

    private NonIdOperatorDecoder() {}

    // TODO: Move to Filter?
    public static List<String> COMPARISON_OPERATORS = Arrays.asList("PropertyIsEqualTo", "PropertyIsNotEqualTo", "PropertyIsLessThan", "PropertyIsGreaterThan", "PropertyIsLessThanOrEqualTo", "PropertyIsGreaterThanOrEqualTo", "PropertyIsLike", "PropertyIsNull", "PropertyIsNil", "PropertyIsBetween");

    // TODO: Move to Filter?
    public static List<String> LOGICAL_OPERATORS = Arrays.asList("And", "Or", "Not");

    @Override
    public boolean canDecode(final DepthXMLStreamReader in) {
        return BinarySpatialOperatorDecoder.I.canDecode(in);
    }

    @Override
    public NonIdOperator decode(final DepthXMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        NonIdOperator returnOperator = null;
        if(COMPARISON_OPERATORS.contains(in.getLocalName())) {
            returnOperator = ComparisonOperatorDecoder.I.decode(in, owsContext);
        } else if(LOGICAL_OPERATORS.contains(in.getLocalName())) {
            returnOperator = LogicalOperatorDecoder.I.decode(in, owsContext);
        } else if(SpatialOperatorDecoder.I.canDecode(in)) {
            returnOperator = SpatialOperatorDecoder.I.decode(in, owsContext);
        }
        return returnOperator;
    }

}
