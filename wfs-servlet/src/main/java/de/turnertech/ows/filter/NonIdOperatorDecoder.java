package de.turnertech.ows.filter;

import java.util.Arrays;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import de.turnertech.ows.common.OwsContext;

/**
 * Delegating Decoder
 */
public class NonIdOperatorDecoder {
    
    // TODO: Move to Filter?
    public static List<String> COMPARISON_OPERATORS = Arrays.asList("PropertyIsEqualTo", "PropertyIsNotEqualTo", "PropertyIsLessThan", "PropertyIsGreaterThan", "PropertyIsLessThanOrEqualTo", "PropertyIsGreaterThanOrEqualTo", "PropertyIsLike", "PropertyIsNull", "PropertyIsNil", "PropertyIsBetween");

    // TODO: Move to Filter?
    public static List<String> LOGICAL_OPERATORS = Arrays.asList("And", "Or", "Not");

    public static NonIdOperator decode(final XMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        NonIdOperator returnOperator = null;
        System.out.println("Non Id Operator Decoder");
        if(COMPARISON_OPERATORS.contains(in.getLocalName())) {
            System.out.println("Go To Comparator Decoder");
        } else if(LOGICAL_OPERATORS.contains(in.getLocalName())) {
            returnOperator = LogicalOperatorDecoder.decode(in, owsContext);
        }
        return returnOperator;
    }

}
