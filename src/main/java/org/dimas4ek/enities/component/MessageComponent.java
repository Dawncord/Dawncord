package org.dimas4ek.enities.component;

import java.util.List;

public class MessageComponent {
    private static List<ActionRow> actionRows;
    
    public MessageComponent(List<ActionRow> actionRows) {
        this.actionRows = actionRows;
    }
    
    public static MessageComponent of(ActionRow... actionRows) {
        MessageComponent.actionRows = List.of(actionRows);
        return null;
    }
    
    public List<ActionRow> getActionRows() {
        return actionRows;
    }
}
