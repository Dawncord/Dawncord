package org.dimas4ek.wrapper.entities.message.embed;


import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Embed {
    private final JSONObject embed;

    public Embed(JSONObject embed) {
        this.embed = embed;
    }

    public String getType() {
        return embed.getString("type");
    }
    public String getTitle(){
        return embed.getString("title");
    }
    public List<Field> getFields(){
        JSONArray fields = embed.getJSONArray("fields");
        return JsonUtils.getEntityList(fields, Field::new);
    }
    public Image getImage(){
        return new Image(embed.getJSONObject("image"));
    }
    public Image getThumbnail(){
        return new Image(embed.getJSONObject("thumbnail"));
    }
}
