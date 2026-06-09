package io.github.dawncord.api.action.guildsticker;

import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;

/**
 * Represents an action for updating a guild sticker.
 *
 * @see Sticker
 */
public class GuildStickerModifyAction extends GuildStickerAction {
    private final String stickerId;

    /**
     * Create a new {@link GuildStickerModifyAction}
     *
     * @param guildId   The ID of the guild in which the sticker is being modified.
     * @param stickerId The ID of the sticker being modified.
     */
    public GuildStickerModifyAction(String guildId, String stickerId) {
        super(guildId);
        this.stickerId = stickerId;
    }

    @Override
    protected void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, Routes.Guild.Sticker.Get(guildId, stickerId));
            hasChanges = false;
        }
    }
}
