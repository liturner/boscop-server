package de.turnertech.ows.filter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlEncoder;

class IdOperatorEncoder implements XmlEncoder<IdOperator> {
    
    final static IdOperatorEncoder I = new IdOperatorEncoder();

    private IdOperatorEncoder() {}

    @Override
    public void encode(final XMLStreamWriter out, final IdOperator idOperator, final OwsContext owsContext) throws XMLStreamException {
        out.writeEmptyElement(owsContext.getXmlNamespacePrefix(OwsContext.FES_URI), "ResourceId", OwsContext.FES_URI);
        out.writeAttribute("rid", idOperator.getId().toString());
    }

}
