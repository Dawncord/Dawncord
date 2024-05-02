package io.github.dawncord.api.entities.application;

import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.application.team.Team;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.image.ApplicationIcon;
import io.github.dawncord.api.types.ActivityType;
import io.github.dawncord.api.types.ApplicationFlag;

import java.util.List;

/**
 * Represents an application.
 * Application is an interface providing methods to access properties of an application.
 */
public interface Application extends IApplication {

    /**
     * Retrieves the guild associated with this application.
     *
     * @return The guild associated with this application.
     */
    Guild getGuild();

    /**
     * Retrieves the count of guilds associated with this application.
     *
     * @return The count of guilds associated with this application.
     */
    int getGuildCount();

    /**
     * Retrieves the redirect URIs of this application.
     *
     * @return The redirect URIs of this application.
     */
    List<String> getRedirectURIs();

    /**
     * Retrieves the interaction endpoint URL of this application.
     *
     * @return The interaction endpoint URL of this application.
     */
    String getInteractionEndpointUrl();

    /**
     * Retrieves the verification URL of this application.
     *
     * @return The verification URL of this application.
     */
    String getVerificationUrl();

    /**
     * Retrieves the custom install URL of this application.
     *
     * @return The custom install URL of this application.
     */
    String getCustomInstallUrl();

    /**
     * Retrieves the icon of this application.
     *
     * @return The icon of this application.
     */
    ApplicationIcon getIcon();

    /**
     * Retrieves the cover image of this application.
     *
     * @return The cover image of this application.
     */
    ApplicationIcon getCoverImage();

    /**
     * Retrieves the type of this application's activity.
     *
     * @return The type of this application's activity.
     */
    ActivityType getType();

    /**
     * Retrieves the owner of this application.
     *
     * @return The owner of this application.
     */
    User getOwner();

    /**
     * Retrieves the team associated with this application.
     *
     * @return The team associated with this application.
     */
    Team getTeam();

    /**
     * Checks if this application is a public bot.
     *
     * @return True if this application is a public bot, false otherwise.
     */
    boolean isPublicBot();

    /**
     * Checks if this application's bot requires OAuth.
     *
     * @return True if this application's bot requires OAuth, false otherwise.
     */
    boolean isBotRequiresOAuth();

    /**
     * Retrieves the Terms of Service (TOS) URL of this application.
     *
     * @return The TOS URL of this application.
     */
    String getTOSUrl();

    /**
     * Retrieves the Privacy Policy (PP) URL of this application.
     *
     * @return The PP URL of this application.
     */
    String getPPUrl();

    /**
     * Retrieves the verify key of this application.
     *
     * @return The verify key of this application.
     */
    String getVerifyKey();

    /**
     * Retrieves the flags associated with this application.
     *
     * @return The flags associated with this application.
     */
    List<ApplicationFlag> getFlags();

    /**
     * Retrieves the tags associated with this application.
     *
     * @return The tags associated with this application.
     */
    List<String> getTags();

    /**
     * Checks if this application is monetized.
     *
     * @return True if this application is monetized, false otherwise.
     */
    boolean isMonetized();

    /**
     * Retrieves the install parameters of this application.
     *
     * @return The install parameters of this application.
     */
    InstallParams getInstallParams();
}
