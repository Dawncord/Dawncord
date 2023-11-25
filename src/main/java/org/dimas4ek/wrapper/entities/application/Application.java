package org.dimas4ek.wrapper.entities.application;

import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.application.team.Team;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.image.ApplicationIcon;
import org.dimas4ek.wrapper.types.ActivityType;
import org.dimas4ek.wrapper.types.ApplicationFlag;

import java.util.List;

public interface Application extends IApplication {
    Guild getGuild();

    int getGuildCount();

    List<String> getRedirectURIs();

    String getInteractionEndpointUrl();

    String getVerificationUrl();

    String getCustomInstallUrl();

    ApplicationIcon getIcon();

    ApplicationIcon getCoverImage();

    ActivityType getType();

    User getOwner();

    Team getTeam();

    boolean isPublicBot();

    boolean isBotRequiresOAuth();

    String getTOSUrl();

    String getPPUrl();

    String getVerifyKey();

    List<ApplicationFlag> getFlags();

    List<String> getTags();

    boolean isMonetized();

    InstallParams getInstallParams();
}
