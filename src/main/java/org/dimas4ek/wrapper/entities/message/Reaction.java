package org.dimas4ek.wrapper.entities.message;

import java.util.List;

public interface Reaction {
    int getTotal();

    int getNormalCount();

    int getBurstCount();

    boolean isMe();

    boolean isMeBurst();

    String getEmoji();

    List<String> getBurstColors();
}
