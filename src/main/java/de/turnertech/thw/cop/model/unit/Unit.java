package de.turnertech.thw.cop.model.unit;

import java.util.Optional;

import javax.xml.stream.XMLStreamWriter;

import de.turnertech.thw.cop.Logging;
import de.turnertech.thw.cop.gml.BoundingBox;
import de.turnertech.thw.cop.gml.FeatureType;
import de.turnertech.thw.cop.gml.IFeature;
import de.turnertech.thw.cop.gml.Point;
import de.turnertech.thw.cop.gml.SpatialReferenceSystemRepresentation;
import de.turnertech.thw.cop.ows.api.OwsContext;
import de.turnertech.thw.cop.util.OPTA;
import de.turnertech.thw.cop.util.PositionProvider;

public class Unit implements PositionProvider, IFeature {
    
    public final String GML_NAME = "Unit";

    /**
     * See OPTA in THW-FuRnR.
     * 
     * We use the free space to assign additional units. E.g. seperate members of a bergungsgruppe.
     */
    private String opta;

    private Point geometry;

    public Unit() {
        opta = "";
        geometry = new Point();
    }

    @Override
    public String getId() {
        return opta;
    }

    public void setOpta(String opta) {
        this.opta = opta;
    }

    public String getOpta() {
        return this.opta;
    }

    public void setLatitude(Double latitude) {
        this.geometry.setY(latitude);
    }

    @Override
    public double getLatitude() {
        return this.geometry.getY();
    }

    public void setLongitude(Double longitude) {
        this.geometry.setX(longitude);
    }

    @Override
    public double getLongitude() {
        return this.geometry.getX();
    }

    @Override
    public BoundingBox getBoundingBox() {
        return BoundingBox.from(this);
    }

    public static Optional<String> validate(Unit tracker) {
        if(!OPTA.isValid(tracker.opta)) {
            // Todo: Make this a more exmplanatory error.
            return Optional.of("OPTA is Invalid");
        }
        
        return Optional.empty();
    }

    
    public String toGmlString() {
        final String gmlId = getOpta().replace(' ', '_');

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<boscop:Unit gml:id=\"");
        stringBuilder.append(gmlId);
        stringBuilder.append("\"><boscop:geometry><gml:Point srsName=\"http://www.opengis.net/def/crs/EPSG/0/4326\" gml:id=\"");
        stringBuilder.append(gmlId);
        stringBuilder.append("-geometry\"><gml:pos>");
        stringBuilder.append(String.valueOf(getLatitude()));
        stringBuilder.append(" ");
        stringBuilder.append(String.valueOf(getLongitude()));
        stringBuilder.append("</gml:pos></gml:Point></boscop:geometry><boscop:opta>");
        stringBuilder.append(getOpta());
        stringBuilder.append("</boscop:opta></boscop:Unit>");
        return stringBuilder.toString();
    }

    @Override
    public void writeGml(XMLStreamWriter out, String localName, String namespaceURI, SpatialReferenceSystemRepresentation srs) {
        try {
            writeGmlStartElement(out, localName, namespaceURI);
            out.writeAttribute(OwsContext.GML_URI, "id", getId());
            
            out.writeStartElement(getFeatureType().getNamespace(), "opta");
            out.writeCharacters(getOpta());
            out.writeEndElement();

            out.writeStartElement(getFeatureType().getNamespace(), "geometry");
            geometry.writeGml(out);
            out.writeEndElement();

            out.writeEndElement();
        } catch (Exception e) {
            Logging.LOG.severe("Could not get GML for DirectPosition");
        }   
    }

    @Override
    public String getGmlName() {
        return GML_NAME;
    }

    @Override
    public FeatureType getFeatureType() {
        return UnitModel.INSTANCE.getFeatureType();
    }

}
