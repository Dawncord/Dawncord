package org.dimas4ek;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.dimas4ek.commands.Option;
import org.dimas4ek.commands.SlashCommand;
import org.dimas4ek.enities.types.OptionType;
import org.dimas4ek.event.Event;
import org.dimas4ek.event.listeners.EventListener;
import org.dimas4ek.event.option.creation.OptionCreationEvent;
import org.dimas4ek.event.option.creation.OptionData;
import org.dimas4ek.event.slashcommand.creation.SlashCommandCreationEvent;
import org.dimas4ek.event.slashcommand.creation.SlashCommandCreationResponse;
import org.dimas4ek.event.slashcommand.creation.SlashCommandCreationResponseImpl;
import org.dimas4ek.test.CommandTest;
import org.dimas4ek.test.PingCommand;
import org.dimas4ek.test.PingCommand2;
import org.dimas4ek.utils.Constants;
import org.dimas4ek.websocket.listeners.InteractionListener;
import org.dimas4ek.websocket.listeners.MainListener;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class Dawncord {
    private WebSocket webSocket;
    
    public static void main(String[] args) {
        Dawncord dawncord = new Dawncord();
        
        dawncord.create("NzU0Mzk0NTI2OTYwODQ0ODgx.GQI9bJ.PCBlXPPXOQRgLWDw3nwJyaMfWHT24xXuZjW4w8")
            .addSlashCommands(new CommandTest(), new PingCommand(), new PingCommand2())
            .build();
        
        dawncord.createGlobalSlashCommands(
            SlashCommand.create(
                "test2",
                "test2",
                Option.addOptions(List.of(
                    new OptionData(OptionType.STRING, "name3", "desc1", true),
                    new OptionData(OptionType.STRING, "name4", "desc2", false)
                ))
            )
        ).execute();
    }
    
    public SlashCommandCreationResponse createGlobalSlashCommands(SlashCommandCreationEvent event) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", event.getName());
        jsonObject.put("description", event.getDescription());
        
        if (event.getOptions() != null) {
            if (event.getOptions().size() == 1) {
                jsonObject.put("options", new JSONArray()
                    .put(new JSONObject()
                             .put("type", event.getOptions().get(0).getType().getValue())
                             .put("name", event.getOptions().get(0).getName())
                             .put("description", event.getOptions().get(0).getDescription())
                             .put("required", event.getOptions().get(0).isRequired())
                    )
                );
            } else {
                JSONArray jsonArray = new JSONArray();
                for (OptionCreationEvent option : event.getOptions()) {
                    jsonArray.
                        put(new JSONObject()
                                .put("type", option.getType().getValue())
                                .put("name", option.getName())
                                .put("description", option.getDescription())
                                .put("required", option.isRequired())
                        );
                }
                jsonObject.put("options", jsonArray);
            }
        }
        
        return new SlashCommandCreationResponseImpl(jsonObject);
    }
    
    public Dawncord create(String BOT_TOKEN) {
        WebSocketFactory factory = new WebSocketFactory();
        try {
            webSocket = factory.createSocket(Constants.GATEWAY);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        webSocket.addListener(new MainListener());
        webSocket.addListener(new InteractionListener());
        
        assignConstants(BOT_TOKEN);
        
        return this;
    }
    
    private static void assignConstants(String BOT_TOKEN) {
        Constants.BOT_TOKEN = BOT_TOKEN;
        
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url(Constants.API_URL + "/applications/@me")
            .addHeader("Authorization", "Bot " + BOT_TOKEN)
            .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                try (ResponseBody body = response.body()) {
                    if (body != null) {
                        JSONObject jsonObject = new JSONObject(body.string());
                        Constants.APPLICATION_ID = jsonObject.getString("id");
                        Constants.CLIENT_KEY = jsonObject.getString("verify_key");
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Dawncord addSlashCommands(Event... slashCommands) {
        for (Event command : slashCommands) {
            EventListener.addEventListener(command);
        }
        return this;
    }
    
    public void build() {
        try {
            webSocket.connect();
        } catch (WebSocketException e) {
            throw new RuntimeException(e);
        }
        
        JSONObject identify = new JSONObject()
            .put("op", 2)
            .put("d", new JSONObject()
                .put("token", Constants.BOT_TOKEN)
                .put("intents", 513)
                .put("properties", new JSONObject()
                    .put("os", "linux")
                    .put("browser", "discord-java-gateway")
                    .put("device", "discord-java-gateway"))
            );
        
        webSocket.sendText(identify.toString());
    }
}