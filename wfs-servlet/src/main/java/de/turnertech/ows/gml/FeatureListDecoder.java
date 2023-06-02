package de.turnertech.ows.gml;

import java.io.File;

import org.w3c.dom.Node;

import de.turnertech.ows.common.Model;

public abstract class FeatureListDecoder {
    
    public abstract Model decode(File file);

    protected Model decode(Node xmlRootNode, GmlDecoderContext gmlContext, FeatureType featureType) {

        

        return null;
    }

}
