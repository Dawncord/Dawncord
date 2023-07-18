package org.dimas4ek.enities.embed;

import java.util.ArrayList;
import java.util.List;

public class EmbedBuilder {
    private final String title;
    private final String description;
    private final List<Field> fields;
    private String author;
    private String footer;
    
    public EmbedBuilder(Embed embed) {
        this.title = embed.getTitle();
        this.description = embed.getDescription();
        this.fields = new ArrayList<>(embed.getFields());
        this.author = embed.getAuthor();
        this.footer = embed.getFooter();
    }
    
    public Embed build() {
        Embed newEmbed = new Embed(this.title, this.description);
        newEmbed.getFields().addAll(this.fields);
        newEmbed.addAuthor(this.author);
        newEmbed.addFooter(this.footer);
        return newEmbed;
    }
}
