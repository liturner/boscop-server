package de.turnertech.ows.gml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.Logging;
import de.turnertech.ows.srs.SpatialReferenceSystem;
import de.turnertech.ows.srs.SpatialReferenceSystemConverter;
import de.turnertech.ows.srs.SpatialReferenceSystemRepresentation;

/**
 * gml:posList
 */
public class DirectPositionList extends ArrayList<DirectPosition> implements GmlElement, BoundingBoxProvider {
    
    private SpatialReferenceSystem srs;

    public static final String GML_NAME = "posList";

    public DirectPositionList() {
        this(10);
    }

    public DirectPositionList(int initialCapacity) {
        super(initialCapacity);
        this.srs = SpatialReferenceSystem.EPSG4326;
    }

    public DirectPositionList(DirectPosition... positions) {
        super(Arrays.asList(positions));
        this.srs = SpatialReferenceSystem.EPSG4326;
    }

    public SpatialReferenceSystem getSrs() {
        return srs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeGml(XMLStreamWriter out, String localName, String namespaceURI, SpatialReferenceSystemRepresentation srsRepresentation) {
        try {
            writeGmlStartElement(out, localName, namespaceURI);

            String[] outPosList = new String[this.size()];
            for(int i = 0; i < this.size(); ++i) {
                DirectPosition outPos = this.get(i);
                Optional<DirectPosition> transformedPos = SpatialReferenceSystemConverter.convertDirectPosition(outPos, srsRepresentation.getSrs());
                if (transformedPos.isPresent()) {
                    outPos = transformedPos.get();
                }
                outPosList[i] = outPos.toString();
            }
            out.writeAttribute("srsDimension", Byte.toString(srsRepresentation.getSrs().getDimension()));
            out.writeCharacters(String.join(" ", outPosList));
            out.writeEndElement();
        } catch (Exception e) {
            Logging.LOG.severe("Could not get GML for DirectPositionList");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getGmlName() {
        return GML_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((srs == null) ? 0 : srs.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        DirectPositionList other = (DirectPositionList) obj;
        return srs == other.srs;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return BoundingBox.from(this);
    }

}
