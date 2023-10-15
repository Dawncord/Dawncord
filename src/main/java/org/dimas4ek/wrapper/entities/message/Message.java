package org.dimas4ek.wrapper.entities.message;

import org.dimas4ek.wrapper.entities.User;

public interface Message {
    String getContent();
    User getUser();
}
