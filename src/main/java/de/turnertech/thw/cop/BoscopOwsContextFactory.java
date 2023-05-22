package de.turnertech.thw.cop;

import de.turnertech.thw.cop.model.area.AreaModel;
import de.turnertech.thw.cop.model.hazard.HazardModel;
import de.turnertech.thw.cop.model.unit.UnitModel;
import de.turnertech.thw.cop.ows.api.DefaultModelProvider;
import de.turnertech.thw.cop.ows.api.ModelProvider;
import de.turnertech.thw.cop.ows.api.OwsContextFactory;

public class BoscopOwsContextFactory extends OwsContextFactory {

    @Override
    public ModelProvider createModelProvider() {
        DefaultModelProvider modelProvider = new DefaultModelProvider();
        modelProvider.putModel(AreaModel.TYPENAME, AreaModel.INSTANCE);
        modelProvider.putModel(HazardModel.TYPENAME, HazardModel.INSTANCE);
        modelProvider.putModel(UnitModel.TYPENAME, UnitModel.INSTANCE);
        return modelProvider;
    }
    
}
