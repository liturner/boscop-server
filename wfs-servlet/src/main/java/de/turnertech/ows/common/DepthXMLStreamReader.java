package de.turnertech.ows.common;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class DepthXMLStreamReader implements XMLStreamReader {

    private final XMLStreamReader xmlStreamReader;

    private int depth;

    public DepthXMLStreamReader(final XMLStreamReader xmlStreamReader) {
        this.xmlStreamReader = xmlStreamReader;
    }

    public XMLStreamReader getReader() {
        return this.xmlStreamReader;
    }

    public int getDepth() {
        return depth;
    }

    public void close() throws XMLStreamException {
        xmlStreamReader.close();
    }

    public int getAttributeCount() {
        return xmlStreamReader.getAttributeCount();
    }

    public String getAttributeLocalName(int index) {
        return xmlStreamReader.getAttributeLocalName(index);
    }

    public QName getAttributeName(int index) {
        return xmlStreamReader.getAttributeName(index);
    }

    public String getAttributeNamespace(int index) {
        return xmlStreamReader.getAttributeNamespace(index);
    }

    public String getAttributePrefix(int index) {
        return xmlStreamReader.getAttributePrefix(index);
    }

    public String getAttributeType(int index) {
        return xmlStreamReader.getAttributeType(index);
    }

    public String getAttributeValue(int index) {
        return xmlStreamReader.getAttributeValue(index);
    }

    public String getAttributeValue(String namespace, String localName) {
        return xmlStreamReader.getAttributeValue(namespace, localName);
    }

    public String getCharacterEncodingScheme() {
        return xmlStreamReader.getCharacterEncodingScheme();
    }

    public String getElementText() throws XMLStreamException {
        String ret = xmlStreamReader.getElementText();
        while (xmlStreamReader.getEventType() != XMLStreamConstants.END_ELEMENT) {
            xmlStreamReader.next();
        }
        depth--;
        return ret;
    }

    public String getEncoding() {
        return xmlStreamReader.getEncoding();
    }

    public int getEventType() {
        return xmlStreamReader.getEventType();
    }

    public String getLocalName() {
        return xmlStreamReader.getLocalName();
    }

    public Location getLocation() {
        return xmlStreamReader.getLocation();
    }

    public QName getName() {
        return xmlStreamReader.getName();
    }

    public NamespaceContext getNamespaceContext() {
        return xmlStreamReader.getNamespaceContext();
    }

    public int getNamespaceCount() {
        return xmlStreamReader.getNamespaceCount();
    }

    public String getNamespacePrefix(int index) {
        return xmlStreamReader.getNamespacePrefix(index);
    }

    public String getNamespaceURI() {
        return xmlStreamReader.getNamespaceURI();
    }

    public String getNamespaceURI(int index) {
        return xmlStreamReader.getNamespaceURI(index);
    }

    public String getNamespaceURI(String prefix) {
        return xmlStreamReader.getNamespaceURI(prefix);
    }

    public String getPIData() {
        return xmlStreamReader.getPIData();
    }

    public String getPITarget() {
        return xmlStreamReader.getPITarget();
    }

    public String getPrefix() {
        return xmlStreamReader.getPrefix();
    }

    public Object getProperty(String name) {
        return xmlStreamReader.getProperty(name);
    }

    public String getText() {
        return xmlStreamReader.getText();
    }

    public char[] getTextCharacters() {
        return xmlStreamReader.getTextCharacters();
    }

    public int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) throws XMLStreamException {
        return xmlStreamReader.getTextCharacters(sourceStart, target, targetStart, length);
    }

    public int getTextLength() {
        return xmlStreamReader.getTextLength();
    }

    public int getTextStart() {
        return xmlStreamReader.getTextStart();
    }

    public String getVersion() {
        return xmlStreamReader.getVersion();
    }

    public boolean hasName() {
        return xmlStreamReader.hasName();
    }

    public boolean hasNext() throws XMLStreamException {
        return xmlStreamReader.hasNext();
    }

    public boolean hasText() {
        return xmlStreamReader.hasText();
    }

    public boolean isAttributeSpecified(int index) {
        return xmlStreamReader.isAttributeSpecified(index);
    }

    public boolean isCharacters() {
        return xmlStreamReader.isCharacters();
    }

    public boolean isEndElement() {
        return xmlStreamReader.isEndElement();
    }

    public boolean isStandalone() {
        return xmlStreamReader.isStandalone();
    }

    public boolean isStartElement() {
        return xmlStreamReader.isStartElement();
    }

    public boolean isWhiteSpace() {
        return xmlStreamReader.isWhiteSpace();
    }

    public int next() throws XMLStreamException {
        int next = xmlStreamReader.next();

        if (next == START_ELEMENT) {
            depth++;
        } else if (next == END_ELEMENT) {
            depth--;
        }

        return next;
    }

    public int nextTag() throws XMLStreamException {
        int eventType = next();
        while (eventType == XMLStreamConstants.CHARACTERS && isWhiteSpace()
                || eventType == XMLStreamConstants.CDATA && isWhiteSpace()
                // skip whitespace
                || eventType == XMLStreamConstants.SPACE
                || eventType == XMLStreamConstants.PROCESSING_INSTRUCTION
                || eventType == XMLStreamConstants.COMMENT) {
            eventType = next();
        }
        if (eventType != XMLStreamConstants.START_ELEMENT && eventType != XMLStreamConstants.END_ELEMENT) {
            throw new XMLStreamException("expected start or end tag", getLocation());
        }
        return eventType;
    }

    public void require(int type, String namespaceURI, String localName) throws XMLStreamException {
        xmlStreamReader.require(type, namespaceURI, localName);
    }

    public boolean standaloneSet() {
        return xmlStreamReader.standaloneSet();
    }

    public int hashCode() {
        return xmlStreamReader.hashCode();
    }

    public boolean equals(Object obj) {
        return xmlStreamReader.equals(obj);
    }

    public String toString() {
        return xmlStreamReader.toString();
    }
}