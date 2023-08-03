package org.dimas4ek.enities.component;

import java.util.List;

public class ActionRow {
    private static List<Button> buttons;
    
    public ActionRow() {
    }
    
    public static ActionRow of(Button... buttons) {
        ActionRow.buttons = List.of(buttons);
        return new ActionRow();
    }
    
    public List<Button> getButtons() {
        return buttons;
    }
}
