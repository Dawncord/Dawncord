package org.dimas4ek;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.dimas4ek.commands.SlashCommand;
import org.dimas4ek.event.listeners.EventListener;
import org.dimas4ek.test.CommandTest;
import org.dimas4ek.test.PingCommand;
import org.dimas4ek.test.PingCommand2;
import org.dimas4ek.utils.Constants;
import org.dimas4ek.websocket.listeners.InteractionListener;
import org.dimas4ek.websocket.listeners.MainListener;
import org.json.JSONObject;

import java.io.IOException;

public class Dawncord {
    private WebSocket webSocket;
    
    public static void main(String[] args) {
        Dawncord dawncord = new Dawncord();
        
        dawncord.createDefault("NzU0Mzk0NTI2OTYwODQ0ODgx.G0vMrq.uYGFWhkP1dJyUJd6s1POVCa_tiOOtFcWPDe2dI")
            .addSlashCommands(new CommandTest(), new PingCommand(), new PingCommand2())
            .build();
    }
    
    public Dawncord createDefault(String BOT_TOKEN) {
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
                if (response.body() != null) {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    Constants.APPLICATION_ID =jsonObject.getString("id");
                    Constants.CLIENT_KEY = jsonObject.getString("verify_key");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Dawncord addSlashCommands(SlashCommand... slashCommands) {
        for (SlashCommand command : slashCommands) {
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
            .put("op", 2) // Identify opcode
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