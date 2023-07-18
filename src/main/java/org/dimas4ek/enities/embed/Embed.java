package org.dimas4ek.enities.embed;

import java.util.ArrayList;
import java.util.List;

public class Embed {
    private final String title;
    private final String description;
    private final List<Field> fields;
    private String author;
    private String footer;
    
    public Embed(String title, String description) {
        this.title = title;
        this.description = description;
        this.fields = new ArrayList<>();
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public List<Field> getFields() {
        return fields;
    }
    
    public void addField(String name, String value) {
        this.fields.add(new Field(name, value));
    }
    
    public void addField(Field field) {
        this.fields.add(field);
    }
    
    public void addAuthor(String author) {
        this.author = author;
    }
    
    public void addFooter(String footer) {
        this.footer = footer;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public String getFooter() {
        return footer;
    }
}


