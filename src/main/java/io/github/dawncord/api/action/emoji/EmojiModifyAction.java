package io.github.dawncord.api.action.emoji;

import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.Emoji;

/**
 * Represents an action for updating an emoji.
 *
 * @see Emoji
 */
public class EmojiModifyAction extends EmojiAction {
    private final String emojiId;

    /**
     * Create a new {@link EmojiModifyAction}
     *
     * @param guildId The ID of the guild where the emoji exists.
     * @param emojiId The ID of the emoji to modify.
     */
    public EmojiModifyAction(String guildId, String emojiId) {
        this.emojiId = emojiId;
        super(guildId);
    }

    @Override
    protected void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, Routes.Guild.Emoji.Get(guildId, emojiId));
            hasChanges = false;
        }
    }
}
