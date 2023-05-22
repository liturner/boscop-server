package de.turnertech.thw.cop;

import java.util.Arrays;
import java.util.Map;

import de.turnertech.thw.cop.model.area.AreaModel;
import de.turnertech.thw.cop.model.hazard.HazardModel;
import de.turnertech.thw.cop.model.unit.UnitModel;
import de.turnertech.thw.cop.ows.api.DefaultModelProvider;
import de.turnertech.thw.cop.ows.api.ModelProvider;
import de.turnertech.thw.cop.ows.api.OwsContextFactory;
import de.turnertech.thw.cop.ows.api.WfsCapabilities;

public class BoscopOwsContextFactory extends OwsContextFactory {

    @Override
    public ModelProvider createModelProvider() {
        DefaultModelProvider modelProvider = new DefaultModelProvider();
        modelProvider.putModel(AreaModel.INSTANCE.getFeatureType(), AreaModel.INSTANCE);
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
