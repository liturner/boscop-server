package de.turnertech.ows.common;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.gml.BoundingBox;
import de.turnertech.ows.gml.FeatureType;
import de.turnertech.ows.gml.IFeature;
import de.turnertech.ows.gml.SpatialReferenceSystemRepresentation;

public class Gml32ModelEncoder implements ModelEncoder {

    @Override
    public void encode(Model model, OutputStream out, OwsContext owsContext, OwsRequestContext requestContext) throws XMLStreamException {

        XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
        XMLStreamWriter xmlStreamWriter = outputFactory.createXMLStreamWriter(out, "UTF-8");
        Collection<IFeature> features = model.getAll();
        BoundingBox actualBoundingBox = model.getBoundingBox();

        xmlStreamWriter.writeStartDocument("UTF-8", "1.0");
        
        xmlStreamWriter.writeStartElement("FeatureCollection");
        xmlStreamWriter.writeNamespace(owsContext.getXmlNamespacePrefix(OwsContext.GML_URI), OwsContext.GML_URI);
        List<String> writtenNamespaces = new ArrayList<>(features.size());
        FeatureType typename = model.getFeatureType();
        if(!writtenNamespaces.contains(typename.getNamespace())) {
            xmlStreamWriter.writeNamespace(owsContext.getXmlNamespacePrefix(typename.getNamespace()), typename.getNamespace());
            writtenNamespaces.add(typename.getNamespace());
        }
        
        xmlStreamWriter.writeDefaultNamespace(OwsContext.GML_URI);
        xmlStreamWriter.writeNamespace(owsContext.getXmlNamespacePrefix(OwsContext.XSI_URI), OwsContext.XSI_URI);

        final String gmlSchema = owsContext.getXmlNamespaceSchema(OwsContext.GML_URI);
        if(gmlSchema != null) {
            xmlStreamWriter.writeAttribute("xsi:schemaLocation", OwsContext.GML_URI + " " + gmlSchema);
        }

        if(features.size() > 0 && actualBoundingBox != null) {
            actualBoundingBox.writeGml(xmlStreamWriter);
        }

        for(IFeature feature : features) {
            xmlStreamWriter.writeStartElement(OwsContext.GML_URI, "featureMember");
            feature.writeGml(xmlStreamWriter, feature.getFeatureType().getName(), feature.getFeatureType().getNamespace(), SpatialReferenceSystemRepresentation.EPSG4327_URI);
            xmlStreamWriter.writeEndElement();
        }

        xmlStreamWriter.writeEndElement();
        xmlStreamWriter.writeEndDocument();
    }
    
}
