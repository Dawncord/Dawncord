package io.github.dawncord.api.types;

/**
 * Represents flags that can be applied to a Discord SKU.
 */
public enum SkuFlags {
    AVAILABLE(1 << 2, "SKU is available for purchase"),
    GUILD_SUBSCRIPTION(1 << 7, "Recurring SKU that can be purchased by a user and applied to a single server. Grants access to every user in that server"),
    USER_SUBSCRIPTION(1 << 8, "Recurring SKU purchased by a user for themselves. Grants access to the purchasing user in every server");

    private final long value;
    private final String description;

    SkuFlags(long value, String description) {
        this.value = value;
        this.description = description;
    }

    public long getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
