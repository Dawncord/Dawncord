package org.dimas4ek.event.listeners;

import org.dimas4ek.commands.SlashCommand;

import java.util.ArrayList;
import java.util.List;

public abstract class EventListener {
    private static final List<SlashCommand> eventListeners = new ArrayList<>();
    
    public static void addEventListener(SlashCommand eventListener) {
        eventListeners.add(eventListener);
    }
    
    public static List<SlashCommand> getEventListeners() {
        return eventListeners;
    }
}

/*public class EventListener {
    private final static Logger logger = LoggerFactory.getLogger(EventListener.class);
    private List<SlashCommand> eventListeners = new ArrayList<>();
    
    public void addEventListener(SlashCommand eventListener) {
        eventListeners.add(eventListener);
    }
    
    public void handleInteractions(Javalin app) {
        app.post("/interactions", ctx -> {
            logger.info(ctx.req().getMethod() + " " + ctx.req().getServletPath());
            
            String requestBody = ctx.body();
            JSONObject object = new JSONObject(requestBody);
            int type = object.getInt("type");
            var data = object.getJSONObject("data");
            
            SlashCommandInteractionEvent event = new SlashCommandInteractionEvent(ctx);
            
            if (type == InteractionType.APPLICATION_COMMAND.getType()) {
                String name = data.getString("name");
                
                for (SlashCommand listener : eventListeners) {
                    if (listener.name().equalsIgnoreCase(name)) {
                        listener.onSlashCommandInteraction(event);
                        break;
                    }
                }
            }
        });
    }
}*/

