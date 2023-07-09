package de.turnertech.ows.gml;

import java.awt.geom.Line2D;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.Logging;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.srs.SpatialReferenceSystemRepresentation;

/**
 * gml:LineString
 */
public class LineString implements GmlElement, BoundingBoxProvider, Iterable<Line2D> {
    
    @Deprecated
    public static final String GML_NAME = "LineString";

    public static final QName QNAME = new QName(OwsContext.GML_URI, "LineString");

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
    public Envelope getBoundingBox() {
        return posList.getBoundingBox();
    }

    @Override
    public Iterator<Line2D> iterator() {
        return new LineStringIterator();
    }

    class LineStringIterator implements Iterator<Line2D> {

        private int index = 0;

        public boolean hasNext() {
            return getPosList().size() > index + 1;
        }

        public Line2D next() {
            return new Line2D.Double(getPosList().get(index++), getPosList().get(index++));
        }

   }

}
