package org.dimas4ek.wrapper.event;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.GuildMember;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.channel.GuildChannelImpl;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.interaction.InteractionData;
import org.dimas4ek.wrapper.slashcommand.option.OptionData;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SlashCommandEventImpl implements SlashCommandEvent {
    private final InteractionData data;

    public SlashCommandEventImpl(InteractionData data) {
        this.data = data;
    }

    @Override
    public String getCommandName() {
        return data.getSlashCommand().getName();
    }

    @Override
    public void reply(String message) {
        if (message == null || message.isEmpty()) {
            System.out.println("Empty data.getResponse() text");
        }

        JSONObject jsonObject = new JSONObject()
                .put("type", 4)
                .put("data", new JSONObject()
                        .put("content", message));

        String url = "/interactions/" + data.getResponse().getInteractionId() + "/" + data.getResponse().getInteractionToken() + "/callback";

        ApiClient.post(jsonObject, url);
    }

    @Override
    public GuildMember getMember() {
        return data.getResponse().getGuildMember();
    }

    @Override
    public User getUser() {
        return getMember().getUser();
    }

    @Override
    public Guild getGuild() {
        return data.getResponse().getGuild();
    }

    @Override
    public GuildChannel getChannel() {
        return data.getResponse().getGuildChannel();
    }

    @Override
    public GuildChannel getChannelById(String channelId) {
        return new GuildChannelImpl(ApiClient.getJsonObject("/channels/" + channelId));
    }

    @Override
    public GuildChannel getChannelById(long channelId) {
        return getChannelById(String.valueOf(channelId));
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> optionDataList = new ArrayList<>();
        for (Map<String, Object> map : data.getOptions()) {
            OptionData optionData = new OptionData(map);
            optionDataList.add(optionData);
        }

        return optionDataList;
    }

    @Override
    public OptionData getOption(String name) {
        return getOptions().stream().filter(option -> option.getData().get("name").equals(name)).findAny().orElse(null);
    }
}
