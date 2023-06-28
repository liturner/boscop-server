package de.turnertech.ows.filter;

import java.util.Arrays;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import de.turnertech.ows.common.OwsContext;

/**
 * Delegating Decoder
 */
public class NonIdOperatorDecoder {
    
    private NonIdOperatorDecoder() {
        
    }

    // TODO: Move to Filter?
    public static List<String> COMPARISON_OPERATORS = Arrays.asList("PropertyIsEqualTo", "PropertyIsNotEqualTo", "PropertyIsLessThan", "PropertyIsGreaterThan", "PropertyIsLessThanOrEqualTo", "PropertyIsGreaterThanOrEqualTo", "PropertyIsLike", "PropertyIsNull", "PropertyIsNil", "PropertyIsBetween");

    // TODO: Move to Filter?
    public static List<String> LOGICAL_OPERATORS = Arrays.asList("And", "Or", "Not");

    public static boolean canDecode(final QName element) {
        return BinarySpatialOperatorDecoder.canDecode(element);
    }

    public static NonIdOperator decode(final XMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        NonIdOperator returnOperator = null;
        if(COMPARISON_OPERATORS.contains(in.getLocalName())) {
            returnOperator = ComparisonOperatorDecoder.decode(in, owsContext);
        } else if(LOGICAL_OPERATORS.contains(in.getLocalName())) {
            returnOperator = LogicalOperatorDecoder.decode(in, owsContext);
        } else if(SpatialOperatorDecoder.canDecode(in.getName())) {
            returnOperator = SpatialOperatorDecoder.decode(in, owsContext);
        }
        return returnOperator;
    }

}
