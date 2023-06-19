package de.turnertech.ows.gml;

import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.Logging;
import de.turnertech.ows.srs.SpatialReferenceSystemRepresentation;

/**
 * gml:LineString
 */
public class LineString implements GmlElement, BoundingBoxProvider {
    
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
    public void writeGml(XMLStreamWriter out, String localName, String namespaceURI, SpatialReferenceSystemRepresentation srs) {
        try {
            writeGmlStartElement(out, localName, namespaceURI);
            out.writeAttribute(GmlElement.NAMESPACE, "srsName", srs.toString());

            posList.writeGml(out, DirectPositionList.GML_NAME, DirectPositionList.NAMESPACE, srs);

            out.writeEndElement();
        } catch (Exception e) {
            Logging.LOG.severe("Could not get GML for LinearRing");
        }        
    }

    @Override
    public String getGmlName() {
        return GML_NAME;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return posList.getBoundingBox();
    }

}
