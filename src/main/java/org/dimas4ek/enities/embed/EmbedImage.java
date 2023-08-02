package org.dimas4ek.enities.embed;

public class EmbedImage {
    private String url;
    private String proxyUrl;
    private int width;
    private int height;
    
    public EmbedImage() {
    }
    
    public EmbedImage(String url) {
        this.url = url;
        this.width = 0;
        this.height = 0;
    }
    
    public EmbedImage(String url, int width, int height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getProxyUrl() {
        return proxyUrl;
    }
    
    public void setProxyUrl(String proxyUrl) {
        this.proxyUrl = proxyUrl;
    }
    
    public int getWidth() {
        return width;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
}
