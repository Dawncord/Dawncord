package org.dimas4ek.wrapper.interaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.dimas4ek.wrapper.slashcommand.SlashCommand;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class InteractionData {
    private List<Map<String, Object>> options;
    private SlashCommand slashCommand;
    private Interaction response;
}
