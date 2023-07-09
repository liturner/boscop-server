package de.turnertech.ows.gml;

import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Optional;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.Logging;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.srs.SpatialReferenceSystem;
import de.turnertech.ows.srs.SpatialReferenceSystemConverter;
import de.turnertech.ows.srs.SpatialReferenceSystemRepresentation;

public class Envelope extends Rectangle2D implements GmlElement {

    @Deprecated
    public static final String GML_NAME = "boundedBy";

    public static final QName QNAME = new QName(OwsContext.GML_URI, "Envelope");

    protected DirectPosition upperCorner;

    protected DirectPosition lowerCorner;

    // Use with care! This is an inverted and unusable box!
    public Envelope() {
        this(java.lang.Double.POSITIVE_INFINITY, java.lang.Double.POSITIVE_INFINITY, java.lang.Double.NEGATIVE_INFINITY, java.lang.Double.NEGATIVE_INFINITY);
    }

    public Envelope(double south, double west, double north, double east) {
        this.lowerCorner = new DirectPosition(SpatialReferenceSystem.EPSG4326, west, south);
        this.upperCorner = new DirectPosition(SpatialReferenceSystem.EPSG4326, east, north);
    }

    public Envelope(final DirectPosition lowerCorner, final DirectPosition upperCorner) {
        this.lowerCorner = lowerCorner;
        this.upperCorner = upperCorner;
    }

    public boolean contains(double latitude, double longitute) {
        if(java.lang.Double.isNaN(latitude) || java.lang.Double.isNaN(longitute)) return false;
        return !(latitude > upperCorner.getY() || latitude < lowerCorner.getY() || longitute > upperCorner.getX() || longitute < lowerCorner.getX());
    }

    public boolean intersects(Envelope other) {
        if(this.getNorth() < other.getSouth()) return false;
        if(this.getSouth() > other.getNorth()) return false;
        if(this.getEast() < other.getWest()) return false;
        if(this.getWest() > other.getEast()) return false;
        return true;
    }

    public static Envelope from(Envelope other) {
        return new Envelope(other.getSouth(), other.getWest(), other.getNorth(), other.getEast());
    }

    public static Envelope from(DirectPositionList posList) {
        double maxSouth = java.lang.Double.POSITIVE_INFINITY;
        double maxWest = java.lang.Double.POSITIVE_INFINITY;
        double maxNorth = java.lang.Double.NEGATIVE_INFINITY;
        double maxEast = java.lang.Double.NEGATIVE_INFINITY;

        for(DirectPosition position : posList) {
            if(position.getY() > maxNorth) maxNorth = position.getY();
            if(position.getY() < maxSouth) maxSouth = position.getY();
            if(position.getX() > maxEast) maxEast = position.getX();
            if(position.getX() < maxWest) maxWest = position.getX();
        }

        // Catch BBOX with a size of 0 (causes errors in many clients, happens with only 1 point)
        if(maxSouth == maxNorth) {
            maxNorth += 0.00001;
            maxSouth -= 0.00001;
        }
        if(maxEast == maxWest) {
            maxEast += 0.00001;
            maxWest -= 0.00001;
        }

        return new Envelope(maxSouth, maxWest, maxNorth, maxEast);
    }

    public void expandToFit(Envelope other) {
        if(other == null) return;
        if(other.getNorth() > getNorth()) upperCorner.setY(other.getNorth());
        if(other.getSouth() < getSouth()) lowerCorner.setY(other.getSouth());
        if(other.getEast() > getEast()) upperCorner.setX(other.getEast());
        if(other.getWest() < getWest()) lowerCorner.setX(other.getWest());
    }

    /**
     * @return the south
     */
    public double getSouth() {
        return lowerCorner.getY();
    }

    /**
     * @return the west
     */
    public double getWest() {
        return lowerCorner.getX();
    }

    /**
     * @return the north
     */
    public double getNorth() {
        return upperCorner.getY();
    }

    /**
     * @return the east
     */
    public double getEast() {
        return upperCorner.getX();
    }

    @Override
    public void writeGml(XMLStreamWriter out, String localName, String namespaceURI, SpatialReferenceSystemRepresentation srsRepresentation) {
        DecimalFormat decimalFormat = new DecimalFormat("0.", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        decimalFormat.setMaximumFractionDigits(8);

        DirectPosition sw = new DirectPosition(getWest(), getSouth());
        DirectPosition ne = new DirectPosition(getEast(), getNorth());

        Optional<DirectPosition> transformedPos = SpatialReferenceSystemConverter.convertDirectPosition(sw, srsRepresentation.getSrs());
        if (transformedPos.isPresent()) {
            sw = transformedPos.get();
        }

        transformedPos = SpatialReferenceSystemConverter.convertDirectPosition(ne, srsRepresentation.getSrs());
        if (transformedPos.isPresent()) {
            ne = transformedPos.get();
        }

        try {
            writeGmlStartElement(out, localName, namespaceURI);
                out.writeStartElement(GmlElement.NAMESPACE, "Envelope");
                out.writeAttribute(GmlElement.NAMESPACE, "srsName", srsRepresentation.toString());
                    out.writeStartElement(GmlElement.NAMESPACE, "lowerCorner");
                        out.writeCharacters(sw.toString());
                    out.writeEndElement();
                    out.writeStartElement(GmlElement.NAMESPACE, "upperCorner");
                        out.writeCharacters(ne.toString());
                    out.writeEndElement();
                out.writeEndElement();
            out.writeEndElement();
        } catch (Exception e) {
            Logging.LOG.severe("Could not get GML for BoundingBox");
        }
    }

    @Override
    public String getGmlName() {
        return GML_NAME;
    }

    @Override
    public void setRect(double x, double y, double w, double h) {
        upperCorner.setLocation(x + w, y);
        lowerCorner.setLocation(x, y - h);
    }

    @Override
    public int outcode(double x, double y) {
        int returnCode = 0;
        if(x < getWest()) returnCode = returnCode | OUT_LEFT;
        if(x > getEast()) returnCode = returnCode | OUT_RIGHT;
        if(y < getSouth()) returnCode = returnCode | OUT_BOTTOM;
        if(y > getNorth()) returnCode = returnCode | OUT_TOP;
        return returnCode;
    }

    @Override
    public Rectangle2D createIntersection(Rectangle2D r) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createIntersection'");
    }

    @Override
    public Rectangle2D createUnion(Rectangle2D r) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createUnion'");
    }

    @Override
    public double getX() {
        return lowerCorner.getX();
    }

    @Override
    public double getY() {
        return upperCorner.getY();
    }

    @Override
    public double getWidth() {
        return upperCorner.getX() - lowerCorner.getX();
    }

    @Override
    public double getHeight() {
        return upperCorner.getY() - lowerCorner.getY();
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isEmpty'");
    }

}
