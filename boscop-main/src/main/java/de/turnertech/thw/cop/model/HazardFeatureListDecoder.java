package de.turnertech.thw.cop.model;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.turnertech.ows.common.Model;
import de.turnertech.ows.gml.FeatureListDecoder;
import de.turnertech.ows.gml.FeatureType;
import de.turnertech.ows.gml.GmlDecoderContext;
import de.turnertech.ows.gml.IFeature;
import de.turnertech.thw.cop.Logging;

public class HazardFeatureListDecoder extends FeatureListDecoder {
    
    @Override
    public Model decode(File file) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setIgnoringElementContentWhitespace(true);
        factory.setIgnoringComments(true);
        FeatureType featureType = HazardModel.INSTANCE.getFeatureType();
        // TODO: factory.setSchema(loadSchemaFromConfig...);

        try(FileInputStream fis = new FileInputStream(file)) {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(fis);
            Element root = document.getDocumentElement();

            NodeList features = root.getElementsByTagNameNS(featureType.getNamespace(), featureType.getName());
            GmlDecoderContext decoderContext = new GmlDecoderContext();

            for(int i = 0; i < features.getLength(); ++i) {
                Node feature = features.item(i);
                
                IFeature decodedFeature = null; // FeatureDecoder.decode(feature, decoderContext, featureType);
                if(decodedFeature == null) {
                    Logging.LOG.severe("HazardFeatureListDecoder: Could not decode feature!");
                    continue;
                }

                HazardModel.INSTANCE.add(decodedFeature);
            }

        } catch (Exception e) {
            Logging.LOG.severe("HazardFeatureListDecoder: Could not decode model");
        }

        Logging.LOG.fine("HazardFeatureListDecoder: Decoded Hazard FeatureList");
        return HazardModel.INSTANCE;
    }

}
