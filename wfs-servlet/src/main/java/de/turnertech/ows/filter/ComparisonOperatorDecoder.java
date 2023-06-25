package de.turnertech.ows.filter;

import java.util.Arrays;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import de.turnertech.ows.common.OwsContext;

public class ComparisonOperatorDecoder {
    
    public static List<String> COMPARISON_OPERATORS = Arrays.asList("PropertyIsEqualTo", "PropertyIsNotEqualTo", "PropertyIsLessThan", "PropertyIsGreaterThan", "PropertyIsLessThanOrEqualTo", "PropertyIsGreaterThanOrEqualTo", "PropertyIsLike", "PropertyIsNull", "PropertyIsNil", "PropertyIsBetween");

    public static List<String> BINARY_COMPARISON_OPERATORS = Arrays.asList("PropertyIsEqualTo", "PropertyIsNotEqualTo", "PropertyIsLessThan", "PropertyIsGreaterThan", "PropertyIsLessThanOrEqualTo", "PropertyIsGreaterThanOrEqualTo");

    private ComparisonOperatorDecoder() {
        
    }

    public static ComparisonOperator decode(final XMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        ComparisonOperator returnOperator = null;

        if(BINARY_COMPARISON_OPERATORS.contains(in.getLocalName())) {
            returnOperator = BinaryComparisonOperatorDecoder.decode(in, owsContext);
        }

        return returnOperator;
    }

}
