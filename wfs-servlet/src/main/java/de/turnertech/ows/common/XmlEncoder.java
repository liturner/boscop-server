package de.turnertech.ows.common;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public interface XmlEncoder<T> {

    void encode(final XMLStreamWriter out, final T value, final OwsContext owsContext) throws XMLStreamException;

}
