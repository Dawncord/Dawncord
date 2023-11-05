package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.action.ChannelModifyAction;
import org.dimas4ek.wrapper.action.InviteCreateAction;
import org.dimas4ek.wrapper.entities.PermissionOverride;
import org.dimas4ek.wrapper.entities.thread.Thread;
import org.dimas4ek.wrapper.types.PermissionOverrideType;
import org.dimas4ek.wrapper.types.PermissionType;
import org.slf4j.helpers.CheckReturnValue;

import java.util.List;

public interface GuildChannel extends Channel {
    TextChannel asText();

    VoiceChannel asVoice();

    Thread asThread();

    GuildForum asForum();

    ChannelModifyAction modify();

    List<PermissionOverride> getPermissions();

    PermissionOverride getPermissionById(String permissionId);

    PermissionOverride getPermissionById(long permissionId);

    void editPermission(String permissionId, PermissionOverrideType type, List<PermissionType> denied, List<PermissionType> allowed);

    void deletePermission(int permissionId);

    List<Invite> getInvites();

    @CheckReturnValue
    InviteCreateAction createInvite();

    boolean hasActiveThreads();

    int getActiveThreadsCount();

    List<Thread> getActiveThreads();

    List<Thread> getPublicArchiveThreads();

    List<Thread> getPrivateArchiveThreads();

    List<Thread> getJoinedPrivateArchiveThreads();
}
