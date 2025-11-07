package io.github.dawncord.api.types;

public enum SkuType {
    DURABLE(2, "Durable one-time purchase"),
    CONSUMABLE(3, "Consumable one-time purchase"),
    SUBSCRIPTION(5, "Represents a recurring subscription"),
    SUBSCRIPTION_GROUP(6, "System-generated group for each SUBSCRIPTION SKU created");

    private final int value;
    private final String description;

    SkuType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
