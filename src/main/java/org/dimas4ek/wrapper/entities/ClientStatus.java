package org.dimas4ek.wrapper.entities;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.wrapper.types.OnlineStatus;
import org.dimas4ek.wrapper.utils.EnumUtils;

public class ClientStatus {
    private final JsonNode status;
    private OnlineStatus mobileStatus;
    private OnlineStatus desktopStatus;
    private OnlineStatus webStatus;

    public ClientStatus(JsonNode status) {
        this.status = status;
    }

    public OnlineStatus getMobileStatus() {
        if (mobileStatus == null) {
            mobileStatus = EnumUtils.getEnumObject(status, "mobile", OnlineStatus.class);
        }
        return mobileStatus;
    }

    public OnlineStatus getDesktopStatus() {
        if (desktopStatus == null) {
            desktopStatus = EnumUtils.getEnumObject(status, "desktop", OnlineStatus.class);
        }
        return desktopStatus;
    }

    public OnlineStatus getWebStatus() {
        if (webStatus == null) {
            webStatus = EnumUtils.getEnumObject(status, "web", OnlineStatus.class);
        }
        return webStatus;
    }
}
