package org.dimas4ek.wrapper.slashcommand;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@NoArgsConstructor
@AllArgsConstructor
public class SlashCommandBuilder {
    private String name;
    private String description;

    public SlashCommand build() {
        return new SlashCommand(
                new JSONObject()
                        .put("name", name)
                        .put("description", description)
        );
    }
}
