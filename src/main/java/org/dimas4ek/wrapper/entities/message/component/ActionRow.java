package org.dimas4ek.wrapper.entities.message.component;

import org.dimas4ek.wrapper.types.ChannelType;
import org.dimas4ek.wrapper.types.ComponentType;
import org.dimas4ek.wrapper.types.SelectMenuType;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActionRow {
    private final JSONObject actionRow;

    public ActionRow(JSONObject actionRow) {
        this.actionRow = actionRow;
    }

    public List<ButtonBuilder> getButtons() {
        List<ButtonBuilder> buttons = new ArrayList<>();
        JSONArray components = actionRow.getJSONArray("components");
        for (int i = 0; i < components.length(); i++) {
            JSONObject component = components.getJSONObject(i);
            if (component.getInt("type") == ComponentType.BUTTON.getValue()) {
                buttons.add(new ButtonBuilder(
                        component.getInt("style"),
                        component.has("custom_id")
                                ? component.getString("custom_id")
                                : component.getString("url"),
                        component.getString("label"))
                );
            }
        }

        return buttons;
    }

    public List<SelectMenuBuilder> getSelectMenus() {
        List<SelectMenuBuilder> selectMenus = new ArrayList<>();
        JSONArray components = actionRow.getJSONArray("components");
        for (int i = 0; i < components.length(); i++) {
            JSONObject component = components.getJSONObject(i);
            if (component.getInt("type") != ComponentType.ACTION.getValue()
                    || component.getInt("type") != ComponentType.BUTTON.getValue()
                    || component.getInt("type") != ComponentType.TEXT_INPUT.getValue()) {
                SelectMenuBuilder selectMenu = new SelectMenuBuilder(
                        getSelectMenuType(component.getInt("type")),
                        component.getString("custom_id"));
                if (component.has("options")) {
                    selectMenu.addOptions(getSelectMenuOptions(component.getJSONArray("options")));
                }
                if (component.has("channel_types")) {
                    selectMenu.setChannelTypes(getChannelTypes(component.getJSONArray("channel_types")));
                }
                if (component.has("placeholder")) {
                    selectMenu.addPlaceholder(component.getString("placeholder"));
                }
                if (component.has("default_values")) {
                    selectMenu.setDefaultValues(getDefaultValues(component.getJSONArray("default_values")));
                }
                if (component.has("min_values")) {
                    selectMenu.setMinValues(component.getInt("min_values"));
                }
                if (component.has("max_values")) {
                    selectMenu.setMaxValues(component.getInt("max_values"));
                }
                if (component.has("disabled")) {
                    selectMenu.disabled();
                }

                selectMenus.add(selectMenu);
            }
        }

        return selectMenus;
    }

    private List<SelectMenuBuilder.DefaultValue> getDefaultValues(JSONArray defaultValuesArray) {
        List<SelectMenuBuilder.DefaultValue> defaultValues = new ArrayList<>();
        for (int i = 0; i < defaultValuesArray.length(); i++) {
            JSONObject defaultValueJson = defaultValuesArray.getJSONObject(i);
            SelectMenuBuilder.DefaultValue defaultValue = new SelectMenuBuilder.DefaultValue(
                    defaultValueJson.getString("id"),
                    getSelectMenuType(defaultValueJson.getInt("type"))
            );
            defaultValues.add(defaultValue);
        }

        return defaultValues;
    }

    private ChannelType[] getChannelTypes(JSONArray channelTypesArray) {
        ChannelType[] channelTypes = new ChannelType[channelTypesArray.length()];
        for (int i = 0; i < channelTypesArray.length(); i++) {
            channelTypes[i] = (getChannelType(channelTypesArray.getInt(i)));
        }
        return channelTypes;
    }

    private ChannelType getChannelType(int type) {
        return EnumUtils.getEnumObjectFromInt(type, ChannelType.class);
        /*for (ChannelType selectMenuType : ChannelType.values()) {
            if (type == selectMenuType.getValue()) {
                return selectMenuType;
            }
        }
        return null;*/
    }

    private List<SelectOption> getSelectMenuOptions(JSONArray optionsArray) {
        List<SelectOption> selectOptions = new ArrayList<>();
        for (int i = 0; i < optionsArray.length(); i++) {
            JSONObject optionJson = optionsArray.getJSONObject(i);
            SelectOption option = new SelectOption(optionJson.getString("label"), optionJson.getString("value"));
            if (optionJson.has("description")) {
                option.setDescription(optionJson.getString("description"));
            }
            if (optionJson.has("default")) {
                option.setDefault(true);
            }
            selectOptions.add(option);
        }
        return selectOptions;
    }

    private SelectMenuType getSelectMenuType(int type) {
        return EnumUtils.getEnumObjectFromInt(type, SelectMenuType.class);
        /*for (SelectMenuType selectMenuType : SelectMenuType.values()) {
            if (type == selectMenuType.getValue()) {
                return selectMenuType;
            }
        }
        return null;*/
    }
}
