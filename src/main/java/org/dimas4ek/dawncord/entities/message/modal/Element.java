package org.dimas4ek.dawncord.entities.message.modal;

import org.dimas4ek.dawncord.types.TextInputStyle;

public class Element {
    private final String label;
    private final String customId;
    private final TextInputStyle style;
    private String placeholder;
    private Boolean required;
    private Integer minLength;
    private Integer maxLength;

    public Element(String label, String customId, TextInputStyle style) {
        this.label = label;
        this.customId = customId;
        this.style = style;
    }

    public Element setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    public Element setRequired(Boolean required) {
        this.required = required;
        return this;
    }

    public Element setMinLength(Integer minLength) {
        this.minLength = minLength;
        return this;
    }

    public Element setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public String getCustomId() {
        return customId;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public TextInputStyle getStyle() {
        return style;
    }

    public Boolean getRequired() {
        return required;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }
}
