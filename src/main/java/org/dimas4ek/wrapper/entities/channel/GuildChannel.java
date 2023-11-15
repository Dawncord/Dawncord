package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.action.GuildChannelModifyAction;
import org.dimas4ek.wrapper.action.GuildChannelPositionModifyAction;
import org.dimas4ek.wrapper.action.InviteCreateAction;
import org.dimas4ek.wrapper.entities.PermissionOverride;
import org.dimas4ek.wrapper.entities.thread.Thread;
import org.dimas4ek.wrapper.types.PermissionOverrideType;
import org.dimas4ek.wrapper.types.PermissionType;

import java.util.List;
import java.util.function.Consumer;

public interface GuildChannel extends Channel {
    TextChannel asText();

    VoiceChannel asVoice();

    Thread asThread();

    GuildForum asForum();

    void modify(Consumer<GuildChannelModifyAction> handler);

    List<PermissionOverride> getPermissions();

    PermissionOverride getPermissionById(String permissionId);

    PermissionOverride getPermissionById(long permissionId);

    void editPermission(String permissionId, PermissionOverrideType type, List<PermissionType> denied, List<PermissionType> allowed);

    void deletePermission(int permissionId);

    List<Invite> getInvites();

    void createInvite(Consumer<InviteCreateAction> handler);

    boolean hasActiveThreads();

    int getActiveThreadsCount();

    List<Thread> getActiveThreads();

    List<Thread> getPublicArchiveThreads();

    List<Thread> getPrivateArchiveThreads();

    List<Thread> getJoinedPrivateArchiveThreads();

    void modifyPosition(Consumer<GuildChannelPositionModifyAction> handler);
}
