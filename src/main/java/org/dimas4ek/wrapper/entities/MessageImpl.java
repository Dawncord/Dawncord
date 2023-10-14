package org.dimas4ek.wrapper.entities;

public class MessageImpl implements Message{
    private final String content;
    private final User user;

    public MessageImpl(String content, User user) {
        this.content = content;
        this.user = user;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public User getUser() {
        return user;
    }
}
