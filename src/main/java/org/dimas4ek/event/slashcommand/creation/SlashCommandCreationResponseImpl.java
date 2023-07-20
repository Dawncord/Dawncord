package org.dimas4ek.event.slashcommand.creation;

import org.dimas4ek.api.ApiClient;
import org.dimas4ek.utils.Constants;
import org.json.JSONObject;

public class SlashCommandCreationResponseImpl implements SlashCommandCreationResponse{
    private final JSONObject jsonObject;
    
    public SlashCommandCreationResponseImpl(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
    
    @Override
    public void execute() {
        System.out.println(jsonObject.toString(4));
        
        ApiClient.postApiRequest("/applications/" + Constants.APPLICATION_ID + "/commands", jsonObject);
    }
}
