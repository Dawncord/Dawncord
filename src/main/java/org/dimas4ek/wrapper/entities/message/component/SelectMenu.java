package org.dimas4ek.wrapper.entities.message.component;

import org.dimas4ek.wrapper.types.ComponentType;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class SelectMenu {
    private final JSONObject selectMenu;

    public SelectMenu(JSONObject selectMenu) {
        this.selectMenu = selectMenu;
    }

    public String getType() {
        for (ComponentType type : ComponentType.values()) {
            if (selectMenu.getInt("type") == type.getValue()) {
                return type.toString();
            }
        }
        return null;
    }

    public String getCustomId() {
        return selectMenu.getString("custom_id");
    }

    public String getPlaceholder() {
        return selectMenu.getString("placeholder");
    }

    public int getMinValues() {
        return selectMenu.getInt("min_values");
    }

    public int getMaxValues() {
        return selectMenu.getInt("max_values");
    }

    public List<Option> getOptions() {
        JSONArray options = selectMenu.getJSONArray("options");
        return JsonUtils.getEntityList(options, Option::new);
    }

    public static class Option {
        private final JSONObject option;

        public Option(JSONObject option) {
            this.option = option;
        }

        public String getLabel() {
            return option.getString("label");
        }

        public String getValue() {
            return option.getString("value");
        }

        public String getDescription() {
            return option.getString("description");
        }

        public Emoji getEmoji() {
            return new Emoji(option.getJSONObject("emoji"));
        }

        public boolean isDefault() {
            return option.getBoolean("default");
        }
    }
}
