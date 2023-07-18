package org.dimas4ek.enities.embed;

/**
 * The Field class represents a field with a name and a value.
 */
public class Field {
    private final String name;
    private final String value;
    private final boolean inline;
    
    /**
     * Creates a new Field object with the specified name and value.
     *
     * @param name  the name of the field
     * @param value the value of the field
     */
    public Field(String name, String value) {
        this.name = name;
        this.value = value;
        this.inline = false;
    }
    
    /**
     * Creates a new Field object with the specified name, value, and inline property.
     *
     * @param name   the name of the field
     * @param value  the value of the field
     * @param inline specifies whether the field should be displayed inline or not
     */
    public Field(String name, String value, boolean inline) {
        this.name = name;
        this.value = value;
        this.inline = inline;
    }
    
    public String getName() {
        return name;
    }
    
    public String getValue() {
        return value;
    }
    
    public boolean isInline() {
        return inline;
    }
}
