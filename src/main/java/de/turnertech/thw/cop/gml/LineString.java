package de.turnertech.thw.cop.gml;

import javax.xml.stream.XMLStreamWriter;

import de.turnertech.thw.cop.Logging;

/**
 * gml:LineString
 */
public class LineString implements GmlElement {
    
    public static final String GML_NAME = "LineString";

    private DirectPositionList posList;

    public LineString() {
        this(new DirectPositionList());
    }

    public LineString(DirectPositionList posList) {
        this.posList = posList;
    }

    public DirectPositionList getPosList() {
        return posList;
    }

    public void setPosList(DirectPositionList posList) {
        this.posList = posList;
    }

    @Override
    public void writeGml(XMLStreamWriter out, String localName, String namespaceURI) {
        try {
            writeGmlStartElement(out, localName, namespaceURI);
            
            posList.writeGml(out);

            out.writeEndElement();
        } catch (Exception e) {
            Logging.LOG.severe("Could not get GML for LinearRing");
        }        
    }

    @Override
    public String getGmlName() {
        return GML_NAME;
    }

}
