package uk.co.thomasc.steamkit.steam3.handlers.steamchat;

import uk.co.thomasc.steamkit.base.ClientMsg;
import uk.co.thomasc.steamkit.base.ClientMsgProtobuf;
import uk.co.thomasc.steamkit.base.IPacketMsg;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.*;
import uk.co.thomasc.steamkit.base.generated.steamlanguageinternal.msg.*;
import uk.co.thomasc.steamkit.steam3.handlers.ClientMsgHandler;
import uk.co.thomasc.steamkit.steam3.handlers.steamchat.callbacks.*;
import uk.co.thomasc.steamkit.steam3.handlers.steamchat.types.Chat;
import uk.co.thomasc.steamkit.types.steamid.SteamID;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * This handler is used for handling MUC/group/clan chat
 */
public final class SteamChat extends ClientMsgHandler {
    private final List<Chat> chats;

    public SteamChat() {
        chats = new ArrayList<Chat>();
    }

    /**
     * Attempts to join a chat room.
     *
     * @param steamId The SteamID of the chat room.
     */
    public void joinChat(SteamID steamId) {
        final SteamID chatId = steamId.clone(); // copy the steamid so we don't modify it
        final ClientMsg<MsgClientJoinChat> joinChat = new ClientMsg<MsgClientJoinChat>(MsgClientJoinChat.class);
        if (chatId.isClanAccount()) {
            // this steamid is incorrect, so we'll fix it up
            chatId.setAccountInstance(SteamID.ChatInstanceFlags.Clan);
            chatId.setAccountType(EAccountType.Chat);
        }
        joinChat.getBody().setSteamIdChat(chatId);
        getClient().send(joinChat);
    }

    /**
     * Attempts to create a chat room from a one-on-one chat.
     *
     * @param steamIdFriendChat the SteamID of the friend you are currently chatting with
     * @param steamIdInvited    the SteamID of the friend to invite to the chat
     */
    public void createChat(SteamID steamIdFriendChat, SteamID steamIdClan, SteamID steamIdInvited) {
        final ClientMsg<MsgClientCreateChat> createChat = new ClientMsg<MsgClientCreateChat>(MsgClientCreateChat.class);
        if (steamIdFriendChat != null) {
            createChat.getBody().setSteamIdFriendChat(steamIdFriendChat);
        }
        if (steamIdClan != null) {
            createChat.getBody().setSteamIdClan(steamIdClan);
        }
        if (steamIdInvited != null) {
            createChat.getBody().setSteamIdInvited(steamIdInvited);
        }
        createChat.getBody().setChatRoomType(EChatRoomType.MUC);
        createChat.getBody().setPermissionOfficer(EChatPermission.MemberDefault);
        createChat.getBody().setPermissionMember(EChatPermission.MemberDefault);
        createChat.getBody().setPermissionAll(EChatPermission.EveryoneDefault);
        createChat.getBody().setMembersMax(0);
        createChat.getBody().setChatFlags((byte) 1); // ?? Is this maybe EChatFlags?
        try {
            createChat.writeNullTermString("", Charset.forName("UTF8")); // ?? we need a \0
        } catch (final IOException e) {
            e.printStackTrace();
        }
        getClient().send(createChat);
    }

    /**
     * Attempts to leave a chat room.
     *
     * @param steamId The SteamID of the chat room.
     */
    public void leaveChat(SteamID steamId) {
        final SteamID chatId = steamId.clone(); // copy the steamid so we don't modify it
        final ClientMsg<MsgClientChatMemberInfo> leaveChat = new ClientMsg<MsgClientChatMemberInfo>(MsgClientChatMemberInfo.class);
        if (chatId.isClanAccount()) {
            // this steamid is incorrect, so we'll fix it up
            chatId.setAccountInstance(SteamID.ChatInstanceFlags.Clan);
            chatId.setAccountType(EAccountType.Chat);
        }
        leaveChat.getBody().setSteamIdChat(chatId);
        leaveChat.getBody().type = EChatInfoType.StateChange;
        try {
            leaveChat.write(getClient().getSteamId().convertToLong()); // ChatterActedOn
            leaveChat.write(EChatMemberStateChange.Left.v()); // StateChange
            leaveChat.write(getClient().getSteamId().convertToLong()); // ChatterActedBy
        } catch (final IOException e) {
            e.printStackTrace();
        }
        getClient().send(leaveChat);
    }

    /**
     * Sends a message to a chat room.
     *
     * @param steamIdChat The SteamID of the chat room.
     * @param type        The message type.
     * @param message     The message.
     */
    public void sendChatRoomMessage(SteamID steamIdChat, EChatEntryType type, String message) {
        final SteamID chatId = steamIdChat.clone(); // copy the steamid so we don't modify it
        if (chatId.isClanAccount()) {
            // this steamid is incorrect, so we'll fix it up
            chatId.setAccountInstance(SteamID.ChatInstanceFlags.Clan);
            chatId.setAccountType(EAccountType.Chat);
        }
        final ClientMsg<MsgClientChatMsg> chatMsg = new ClientMsg<MsgClientChatMsg>(MsgClientChatMsg.class);
        chatMsg.getBody().chatMsgType = type;
        chatMsg.getBody().setSteamIdChatRoom(chatId);
        chatMsg.getBody().setSteamIdChatter(getClient().getSteamId());
        try {
            chatMsg.writeNullTermString(message, Charset.forName("UTF8"));
        } catch (final IOException e) {
            e.printStackTrace();
        }
        getClient().send(chatMsg);
    }

    /**
     * Kicks the specified chat member from the given chat room.
     *
     * @param steamIdChat   The SteamID of chat room to kick the member from.
     * @param steamIdMember The SteamID of the member to kick from the chat.
     */
    public void kickChatMember(SteamID steamIdChat, SteamID steamIdMember) {
        final SteamID chatId = steamIdChat.clone(); // copy the steamid so we don't modify it
        final ClientMsg<MsgClientChatAction> kickMember = new ClientMsg<MsgClientChatAction>(MsgClientChatAction.class);
        if (chatId.isClanAccount()) {
            // this steamid is incorrect, so we'll fix it up
            chatId.setAccountInstance(SteamID.ChatInstanceFlags.Clan);
            chatId.setAccountType(EAccountType.Chat);
        }
        kickMember.getBody().setSteamIdChat(chatId);
        kickMember.getBody().setSteamIdUserToActOn(steamIdMember);
        kickMember.getBody().chatAction = EChatAction.Kick;
        getClient().send(kickMember);
    }

    /**
     * Bans the specified chat member from the given chat room.
     *
     * @param steamIdChat   The SteamID of chat room to ban the member from.
     * @param steamIdMember The SteamID of the member to ban from the chat.
     */
    public void banChatMember(SteamID steamIdChat, SteamID steamIdMember) {
        final SteamID chatId = steamIdChat.clone(); // copy the steamid so we don't modify it
        final ClientMsg<MsgClientChatAction> banMember = new ClientMsg<MsgClientChatAction>(MsgClientChatAction.class);
        if (chatId.isClanAccount()) {
            // this steamid is incorrect, so we'll fix it up
            chatId.setAccountInstance(SteamID.ChatInstanceFlags.Clan);
            chatId.setAccountType(EAccountType.Chat);
        }
        banMember.getBody().setSteamIdChat(chatId);
        banMember.getBody().setSteamIdUserToActOn(steamIdMember);
        banMember.getBody().chatAction = EChatAction.Ban;
        getClient().send(banMember);
    }

    /**
     * Handles a client message. This should not be called directly.
     */
    @Override
    public void handleMsg(IPacketMsg packetMsg) {
        switch (packetMsg.getMsgType()) {
            case ClientChatEnter:
                handleChatEnter(packetMsg);
                break;

            case ClientChatMsg:
                handleChatMsg(packetMsg);
                break;

            case ClientChatMemberInfo:
                handleChatMemberInfo(packetMsg);
                break;

            case ClientChatActionResult:
                handleChatActionResult(packetMsg);
                break;

            case ClientChatInvite:
                handleChatInvite(packetMsg);
                break;

            case ClientCreateChatResponse:
                handleCreateChatResponse(packetMsg);
                break;
        }
    }

    void handleChatEnter(IPacketMsg packetMsg) {
        final ClientMsg<MsgClientChatEnter> chatEnter = new ClientMsg<MsgClientChatEnter>(packetMsg, MsgClientChatEnter.class);
        final ChatEnterCallback callback = new ChatEnterCallback(chatEnter.getBody(), chatEnter.getPayload());
        getClient().postCallback(callback);
        // Add this chat to the list of chats
        if (callback.getEnterResponse() == EChatRoomEnterResponse.Success) {
            Chat newChat = new Chat();
            newChat.steamId = callback.getChatID();
            newChat.members = callback.getChatMembers();
            chats.add(newChat);
        }
    }

    void handleChatMsg(IPacketMsg packetMsg) {
        final ClientMsg<MsgClientChatMsg> chatMsg = new ClientMsg<MsgClientChatMsg>(packetMsg, MsgClientChatMsg.class);
        byte[] msgData = new byte[0];
        try {
            msgData = chatMsg.getPayload().readBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final ChatMsgCallback callback = new ChatMsgCallback(chatMsg.getBody(), msgData);
        getClient().postCallback(callback);
    }

    void handleChatMemberInfo(IPacketMsg packetMsg) {
        final ClientMsg<MsgClientChatMemberInfo> membInfo = new ClientMsg<MsgClientChatMemberInfo>(packetMsg, MsgClientChatMemberInfo.class);
        try {
            byte[] payload = membInfo.getPayload().readBytes();
            final ChatMemberInfoCallback callback = new ChatMemberInfoCallback(membInfo.getBody(), payload);
            getClient().postCallback(callback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void handleChatActionResult(IPacketMsg packetMsg) {
        final ClientMsg<MsgClientChatActionResult> actionResult = new ClientMsg<MsgClientChatActionResult>(packetMsg, MsgClientChatActionResult.class);
        final ChatActionResultCallback callback = new ChatActionResultCallback(actionResult.getBody());
        getClient().postCallback(callback);
    }

    void handleCreateChatResponse(IPacketMsg packetMsg) {
        final ClientMsg<MsgClientCreateChatResponse> actionResult = new ClientMsg<MsgClientCreateChatResponse>(packetMsg, MsgClientCreateChatResponse.class);
        final ChatCreateResponseCallback callback = new ChatCreateResponseCallback(actionResult.getBody());
        getClient().postCallback(callback);
        if (callback.getResult() == EResult.OK) {
            joinChat(callback.getChatID());
        }
    }

    void handleChatInvite(IPacketMsg packetMsg) {
        final ClientMsgProtobuf<SteammessagesClientserver.CMsgClientChatInvite> chatInvite = new ClientMsgProtobuf<SteammessagesClientserver.CMsgClientChatInvite>(SteammessagesClientserver.CMsgClientChatInvite.class, packetMsg);
        final ChatInviteCallback callback = new ChatInviteCallback(chatInvite.getBody());
        getClient().postCallback(callback);
        // maybe not do this automatically
        joinChat(callback.getChatRoomID());
    }

    public List<Chat> getChats() {
        return this.chats;
    }
}
