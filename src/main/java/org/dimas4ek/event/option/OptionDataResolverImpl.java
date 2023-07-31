package org.dimas4ek.event.option;

import org.dimas4ek.enities.types.OptionType;
import org.json.JSONObject;

public class OptionDataResolverImpl implements OptionDataResolver{
    private final JSONObject option;
    
    public OptionDataResolverImpl(JSONObject option) {
        this.option = option;
    }
    
    @Override
    public String getName() {
        return option.getString("name");
    }
    
    @Override
    public OptionType getType() {
        return getOptionType(option.getInt("type"));
    }
    
    @Override
    public String getValue() {
        return option.getString("value");
    }
    
    private OptionType getOptionType(int type) {
        for (OptionType optionType : OptionType.values()) {
            if (type == optionType.getValue()) {
                return optionType;
            }
        }
        return null;
    }
}
