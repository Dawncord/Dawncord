package org.dimas4ek.wrapper.entities;

import org.json.JSONObject;

import java.util.List;

public interface GuildBan {
    User getUser();
    String getReason();
}
