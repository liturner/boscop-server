package de.turnertech.ows.filter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.common.OwsContext;

public class FilterEncoder {
    
    public static void encode(final XMLStreamWriter out, final Filter filter, final OwsContext owsContext) throws XMLStreamException {
        out.writeStartElement(owsContext.getXmlNamespacePrefix(OwsContext.FES_URI), "Filter", OwsContext.FES_URI);
        out.writeNamespace(owsContext.getXmlNamespacePrefix(OwsContext.FES_URI), OwsContext.FES_URI);
        out.writeNamespace(owsContext.getXmlNamespacePrefix(OwsContext.GML_URI), OwsContext.GML_URI);
        out.writeNamespace(owsContext.getXmlNamespacePrefix(OwsContext.XSI_URI), OwsContext.XSI_URI);

        OperatorEncoder.encode(out, filter.getFilter(), owsContext);

        out.writeEndElement();
    }

}
