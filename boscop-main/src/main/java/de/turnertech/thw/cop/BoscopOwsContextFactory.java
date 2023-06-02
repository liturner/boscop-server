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
import de.turnertech.thw.cop.model.AreaModel;
import de.turnertech.thw.cop.model.HazardFeatureListDecoder;
import de.turnertech.thw.cop.model.HazardModel;
import de.turnertech.thw.cop.model.UnitModel;

public class BoscopOwsContextFactory extends OwsContextFactory {

    @Override
    public ModelProvider createModelProvider() {
        DefaultModelProvider modelProvider = new DefaultModelProvider();
        modelProvider.putModel(AreaModel.INSTANCE.getFeatureType(), AreaModel.INSTANCE);

        File hazardStorage = Paths.get(Settings.getDataDirectory().toString(), HazardModel.TYPENAME + ".gml").toFile();
        HazardFeatureListDecoder hazardFeatureListDecoder = new HazardFeatureListDecoder();
        Model hazardFeatureList = hazardFeatureListDecoder.decode(hazardStorage);
        modelProvider.putModel(HazardModel.INSTANCE.getFeatureType(), HazardModel.INSTANCE);

        modelProvider.putModel(UnitModel.INSTANCE.getFeatureType(), UnitModel.INSTANCE);
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
