package de.turnertech.thw.cop;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;

import de.turnertech.ows.common.DefaultModelProvider;
import de.turnertech.ows.common.Model;
import de.turnertech.ows.common.ModelProvider;
import de.turnertech.ows.common.OwsContextFactory;
import de.turnertech.ows.common.WfsCapabilities;
import de.turnertech.thw.cop.model.AnnotationModel;
import de.turnertech.thw.cop.model.AreaFeatureListDecoder;
import de.turnertech.thw.cop.model.AreaModel;
import de.turnertech.thw.cop.model.HazardFeatureListDecoder;
import de.turnertech.thw.cop.model.HazardModel;
import de.turnertech.thw.cop.model.UnitModel;

public class BoscopOwsContextFactory extends OwsContextFactory {

    @Override
    public ModelProvider createModelProvider() {
        DefaultModelProvider modelProvider = new DefaultModelProvider();

        File storage = Paths.get(Settings.getDataDirectory().toString(), AreaModel.TYPENAME + ".gml").toFile();
        AreaFeatureListDecoder areaFeatureListDecoder = new AreaFeatureListDecoder();
        Model featureList = areaFeatureListDecoder.decode(storage);
        modelProvider.putModel(featureList.getFeatureType(), featureList);

        storage = Paths.get(Settings.getDataDirectory().toString(), HazardModel.TYPENAME + ".gml").toFile();
        HazardFeatureListDecoder hazardFeatureListDecoder = new HazardFeatureListDecoder();
        featureList = hazardFeatureListDecoder.decode(storage);
        modelProvider.putModel(featureList.getFeatureType(), featureList);

        modelProvider.putModel(UnitModel.INSTANCE.getFeatureType(), UnitModel.INSTANCE);
        modelProvider.putModel(AnnotationModel.INSTANCE.getFeatureType(), AnnotationModel.INSTANCE);
        return modelProvider;
    }

    @Override
    public WfsCapabilities getWfsCapabilities() {
        WfsCapabilities wfsCapabilities = new WfsCapabilities();

        wfsCapabilities.setServiceTitle("BOSCOP WFS");
        wfsCapabilities.setFeatureTypes(Arrays.asList(AreaModel.INSTANCE.getFeatureType(), HazardModel.INSTANCE.getFeatureType(), UnitModel.INSTANCE.getFeatureType()));

        return wfsCapabilities;
    }

    @Override
    public Map<String, String> getNamespacePrefixMap() {
        Map<String, String> returnMap = super.getNamespacePrefixMap();
        returnMap.put(Constants.Model.NAMESPACE, Constants.Model.PREFIX);
        return returnMap;
    }
    
}
