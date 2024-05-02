package io.github.dawncord.api.entities;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.types.OnlineStatus;
import io.github.dawncord.api.utils.EnumUtils;

/**
 * Represents the client's status across different platforms.
 */
public class ClientStatus {
    private final JsonNode status;
    private OnlineStatus mobileStatus;
    private OnlineStatus desktopStatus;
    private OnlineStatus webStatus;

    /**
     * Constructs a ClientStatus object with the given status JSON node.
     *
     * @param status The JSON node representing the client's status.
     */
    public ClientStatus(JsonNode status) {
        this.status = status;
    }

    /**
     * Retrieves the client's status on mobile.
     *
     * @return The client's status on mobile.
     */
    public OnlineStatus getMobileStatus() {
        if (mobileStatus == null) {
            mobileStatus = EnumUtils.getEnumObject(status, "mobile", OnlineStatus.class);
        }
        return mobileStatus;
    }

    /**
     * Retrieves the client's status on desktop.
     *
     * @return The client's status on desktop.
     */
    public OnlineStatus getDesktopStatus() {
        if (desktopStatus == null) {
            desktopStatus = EnumUtils.getEnumObject(status, "desktop", OnlineStatus.class);
        }
        return desktopStatus;
    }

    /**
     * Retrieves the client's status on the web.
     *
     * @return The client's status on the web.
     */
    public OnlineStatus getWebStatus() {
        if (webStatus == null) {
            webStatus = EnumUtils.getEnumObject(status, "web", OnlineStatus.class);
        }
        return webStatus;
    }
}
