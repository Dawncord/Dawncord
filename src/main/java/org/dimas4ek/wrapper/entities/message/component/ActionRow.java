package org.dimas4ek.wrapper.entities.message.component;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.wrapper.types.ChannelType;
import org.dimas4ek.wrapper.types.ComponentType;
import org.dimas4ek.wrapper.types.SelectMenuType;
import org.dimas4ek.wrapper.utils.EnumUtils;

import java.util.ArrayList;
import java.util.List;

public class ActionRow {
    private final List<ButtonBuilder> buttons = new ArrayList<>();
    private final List<SelectMenuBuilder> selectMenus = new ArrayList<>();

    public ActionRow(JsonNode actionRow) {
        JsonNode components = actionRow.get("components");

        for (JsonNode component : components) {
            int type = component.get("type").asInt();

            if (type == ComponentType.BUTTON.getValue()) {
                buttons.add(
                        new ButtonBuilder(
                                component.get("style").asInt(),
                                component.has("custom_id")
                                        ? component.get("custom_id").asText()
                                        : component.get("url").asText(),
                                component.get("label").asText()
                        )
                );
            }
            if (type != ComponentType.ACTION.getValue()
                    || type != ComponentType.BUTTON.getValue()
                    || type != ComponentType.TEXT_INPUT.getValue()) {
                SelectMenuBuilder selectMenu = new SelectMenuBuilder(
                        EnumUtils.getEnumObjectFromInt(type, SelectMenuType.class), component.get("custom_id").asText()
                );
                if (component.has("options")) {
                    selectMenu.addOptions(getSelectMenuOptions(component.get("options")));
                }
                if (component.has("channel_types")) {
                    selectMenu.setChannelTypes(EnumUtils.getEnumList(component.get("channel_types"), ChannelType.class).toArray(new ChannelType[0]));
                }
                if (component.has("placeholder")) {
                    selectMenu.addPlaceholder(component.get("placeholder").asText());
                }
                if (component.has("default_values")) {
                    selectMenu.setDefaultValues(getDefaultValues(component.get("default_values")));
                }
                if (component.has("min_values")) {
                    selectMenu.setMinValues(component.get("min_values").asInt());
                }
                if (component.has("max_values")) {
                    selectMenu.setMaxValues(component.get("max_values").asInt());
                }
                if (component.has("disabled")) {
                    selectMenu.disabled();
                }

                selectMenus.add(selectMenu);
            }
        }
    }

    public List<ButtonBuilder> getButtons() {
        return buttons;
    }

    public List<SelectMenuBuilder> getSelectMenus() {
        return selectMenus;
    }

    private List<SelectMenuBuilder.DefaultValue> getDefaultValues(JsonNode defaultValuesArray) {
        List<SelectMenuBuilder.DefaultValue> defaultValues = new ArrayList<>();
        for (int i = 0; i < defaultValuesArray.size(); i++) {
            JsonNode defaultValueJson = defaultValuesArray.get(i);
            SelectMenuBuilder.DefaultValue defaultValue = new SelectMenuBuilder.DefaultValue(
                    defaultValueJson.get("id").asText(),
                    EnumUtils.getEnumObject(defaultValueJson, "value", SelectMenuType.class)
            );
            defaultValues.add(defaultValue);
        }

        return defaultValues;
    }

    private List<SelectOption> getSelectMenuOptions(JsonNode optionsArray) {
        List<SelectOption> selectOptions = new ArrayList<>();
        for (int i = 0; i < optionsArray.size(); i++) {
            JsonNode optionJson = optionsArray.get(i);
            SelectOption option = new SelectOption(optionJson.get("label").asText(), optionJson.get("value").asText());
            if (optionJson.has("description")) {
                option.setDescription(optionJson.get("description").asText());
            }
            if (optionJson.has("default")) {
                option.setDefault(true);
            }
            selectOptions.add(option);
        }
        return selectOptions;
    }
}
