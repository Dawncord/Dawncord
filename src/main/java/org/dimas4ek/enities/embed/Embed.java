package org.dimas4ek.enities.embed;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Embed {
    private String title;
    private String description;
    private final List<Field> fields;
    private String author;
    private String footer;
    private int color = 536870911;
    private String timeStamp;
    private EmbedImage image;
    private EmbedImage thumbnail;
    
    public Embed(String title) {
        this.title = title;
        this.fields = new ArrayList<>();
    }
    
    public Embed(String title, String description) {
        this.title = title;
        this.description = description;
        this.fields = new ArrayList<>();
    }
    
    public Embed() {
        this.fields = new ArrayList<>();
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Embed setTitle(String title) {
        this.title = title;
        return this;
    }
    
    public Embed setTimeStamp(LocalDateTime timeStamp) {
        ZoneId zoneId = ZoneId.systemDefault();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'").withZone(zoneId);
        this.timeStamp = formatter.format(timeStamp);
        return this;
    }
    
    public String getTimeStamp() {
        return timeStamp;
    }
    
    public Embed addImage(String url) {
        this.image = new EmbedImage(url);
        return this;
    }
    
    public Embed addImage(String url, int width, int height) {
        this.image = new EmbedImage(url, width, height);
        return this;
    }
    
    public EmbedImage getImage() {
        return image;
    }
    
    public Embed addThumbnail(String url) {
        this.thumbnail = new EmbedImage(url);
        return this;
    }
    
    public Embed addThumbnail(String url, int width, int height) {
        this.thumbnail = new EmbedImage(url, width, height);
        return this;
    }
    
    public EmbedImage getThumbnail() {
        return thumbnail;
    }
    
    public Embed setDescription(String description) {
        this.description = description;
        return this;
    }
    
    public List<Field> getFields() {
        return fields;
    }
    
    public Embed addField(String name, String value) {
        this.fields.add(new Field(name, value));
        return this;
    }
    
    public Embed addField(Field field) {
        this.fields.add(field);
        return this;
    }
    
    public Embed addFields(List<Field> fields) {
        this.fields.addAll(fields);
        return this;
    }
    
    public Embed addAuthor(String author) {
        this.author = author;
        return this;
    }
    
    public Embed addFooter(String footer) {
        this.footer = footer;
        return this;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public String getFooter() {
        return footer;
    }
    
    public Embed setColor(Color color) {
        this.color = color == null ? 536870911 : color.getRGB();
        return this;
    }
    
    public Color getColor() {
        return color != 536870911 ? new Color(color) : null;
    }
    
    public int getColorRaw() {
        return color;
    }
}


