package org.dimas4ek.wrapper.entities.message.component;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.types.ComponentType;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActionRow {
    private final JSONObject actionRow;

    public ActionRow(JSONObject actionRow) {
        this.actionRow = actionRow;
    }

    public String getType() {
        for (ComponentType type : ComponentType.values()) {
            if (actionRow.getInt("type") == type.getValue()) {
                return type.toString();
            }
        }
        return null;
    }

    public List<Button> getButtons() {
        JSONArray buttons = actionRow.getJSONArray("components");

        List<Button> buttonList = new ArrayList<>();
        for (Button button : JsonUtils.getEntityList(buttons, Button::new)) {
            if (button.getType().equals(ComponentType.Button.name())) {
                buttonList.add(button);
            }
        }

        return buttonList;
    }

    public SelectMenu getSelectMenu() {
        JSONArray selectMenus = actionRow.getJSONArray("components");

        for (SelectMenu selectMenu : JsonUtils.getEntityList(selectMenus, SelectMenu::new)) {
            if (selectMenu.getType().equals(ComponentType.String.name())) {
                return selectMenu;
            }
        }

        return null;
    }
}
