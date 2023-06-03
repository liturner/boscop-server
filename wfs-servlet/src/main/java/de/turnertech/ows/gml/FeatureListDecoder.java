package de.turnertech.ows.gml;

import java.io.File;

import de.turnertech.ows.common.Model;

public abstract class FeatureListDecoder {
    
    public abstract Model decode(File file);

}
