package de.turnertech.ows.filter;

import java.util.Arrays;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import de.turnertech.ows.common.DepthXMLStreamReader;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlDecoder;

public class ComparisonOperatorDecoder implements XmlDecoder<ComparisonOperator> {
    
    static final ComparisonOperatorDecoder I = new ComparisonOperatorDecoder();

    public static List<String> COMPARISON_OPERATORS = Arrays.asList("PropertyIsEqualTo", "PropertyIsNotEqualTo", "PropertyIsLessThan", "PropertyIsGreaterThan", "PropertyIsLessThanOrEqualTo", "PropertyIsGreaterThanOrEqualTo", "PropertyIsLike", "PropertyIsNull", "PropertyIsNil", "PropertyIsBetween");

    public static List<String> BINARY_COMPARISON_OPERATORS = Arrays.asList("PropertyIsEqualTo", "PropertyIsNotEqualTo", "PropertyIsLessThan", "PropertyIsGreaterThan", "PropertyIsLessThanOrEqualTo", "PropertyIsGreaterThanOrEqualTo");

    private ComparisonOperatorDecoder() {}

    @Override
    public boolean canDecode(DepthXMLStreamReader in) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canDecode'");
    }

    @Override
    public ComparisonOperator decode(final DepthXMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        ComparisonOperator returnOperator = null;

        if(BINARY_COMPARISON_OPERATORS.contains(in.getLocalName())) {
            returnOperator = BinaryComparisonOperatorDecoder.I.decode(in, owsContext);
        }

        return returnOperator;
    }

}
