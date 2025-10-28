package io.github.dawncord.api.action.emoji;

import io.github.dawncord.api.action.Action;

public abstract class EmojiAction extends Action<EmojiAction> {
    protected final String guildId;

    protected EmojiAction(String guildId) {
        super();
        this.guildId = guildId;
    }

    /**
     * Sets the name property for the emoji.
     *
     * @param name the name to set
     * @return the modified EmojiCreateAction object
     */
    public EmojiAction setName(String name) {
        return setProperty("name", name);
    }

    /**
     * Sets the roles property for the emoji.
     *
     * @param roleIds the IDs of the roles to set
     * @return the modified EmojiCreateAction object
     */
    public EmojiAction setRoles(String... roleIds) {
        return setProperty("roles", roleIds);
    }
}
