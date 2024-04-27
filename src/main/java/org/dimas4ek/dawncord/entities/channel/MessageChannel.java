package org.dimas4ek.dawncord.entities.channel;

import org.dimas4ek.dawncord.action.MessageModifyAction;
import org.dimas4ek.dawncord.action.PollCreateAction;
import org.dimas4ek.dawncord.action.ThreadCreateAction;
import org.dimas4ek.dawncord.entities.channel.thread.Thread;
import org.dimas4ek.dawncord.entities.guild.GuildMember;
import org.dimas4ek.dawncord.entities.message.Message;
import org.dimas4ek.dawncord.entities.message.poll.Poll;
import org.dimas4ek.dawncord.event.CreateEvent;
import org.dimas4ek.dawncord.event.ModifyEvent;

import java.util.List;
import java.util.function.Consumer;

public interface MessageChannel extends Channel {
    List<Message> getMessages();

    Message getLastMessage();

    Message getMessageById(String messageId);

    Message getMessageById(long messageId);

    int getRateLimit();

    boolean isNsfw();

    void deleteMessages(int count);

    void setTyping();

    List<Message> getPinnedMessages();

    void pinMessage(String messageId);

    void pinMessage(long messageId);

    void unpinMessage(String messageId);

    void unpinMessage(long messageId);

    CreateEvent<Thread> startThread(String messageId, Consumer<ThreadCreateAction> handler);

    CreateEvent<Thread> startThread(long messageId, Consumer<ThreadCreateAction> handler);

    CreateEvent<Thread> startThread(Consumer<ThreadCreateAction> handler);

    CreateEvent<Thread> startThread();

    ModifyEvent<Message> modifyMessageById(String messageId, Consumer<MessageModifyAction> handler);

    ModifyEvent<Message> modifyMessageById(long messageId, Consumer<MessageModifyAction> handler);

    CreateEvent<Poll> createPoll(Consumer<PollCreateAction> handler);

    Poll getPoll(String messageId);

    Poll getPoll(long messageId);

    List<Poll> getPolls();

    List<Poll> getPolls(String question);

    List<GuildMember> getPollVoters(String messageId, String answerId);
}
