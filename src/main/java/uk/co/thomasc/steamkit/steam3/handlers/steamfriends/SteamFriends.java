package uk.co.thomasc.steamkit.steam3.handlers.steamfriends;

import uk.co.thomasc.steamkit.base.ClientMsg;
import uk.co.thomasc.steamkit.base.ClientMsgProtobuf;
import uk.co.thomasc.steamkit.base.IPacketMsg;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.*;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.*;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.*;
import uk.co.thomasc.steamkit.base.generated.steamlanguageinternal.msg.MsgClientSetIgnoreFriend;
import uk.co.thomasc.steamkit.base.generated.steamlanguageinternal.msg.MsgClientSetIgnoreFriendResponse;
import uk.co.thomasc.steamkit.steam3.handlers.ClientMsgHandler;
import uk.co.thomasc.steamkit.steam3.handlers.steamfriends.callbacks.*;
import uk.co.thomasc.steamkit.steam3.handlers.steamfriends.types.AccountCache;
import uk.co.thomasc.steamkit.steam3.handlers.steamfriends.types.Clan;
import uk.co.thomasc.steamkit.steam3.handlers.steamfriends.types.User;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.JobCallback;
import uk.co.thomasc.steamkit.types.JobID;
import uk.co.thomasc.steamkit.types.gameid.GameID;
import uk.co.thomasc.steamkit.types.steamid.SteamID;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This handler handles all interaction with other users on the Steam3 network.
 */
public final class SteamFriends extends ClientMsgHandler {
    private final Object listLock = new Object();
    private final List<SteamID> friendList;
    private final List<SteamID> clanList;
    public AccountCache cache;

    public SteamFriends() {
        friendList = new ArrayList<SteamID>();
        clanList = new ArrayList<SteamID>();
        cache = new AccountCache();
    }

    /**
     * Gets the local user's persona name.
     *
     * @return The name.
     */
    public String getPersonaName() {
        return cache.getLocalUser().name;
    }

    /**
     * Sets the local user's persona name and broadcasts it over the network.
     *
     * @param name The name.
     */
    public void setPersonaName(String name) {
        // cache the local name right away, so that early calls to SetPersonaState don't reset the set name
        cache.getLocalUser().name = name;
        final ClientMsgProtobuf<CMsgClientChangeStatus> stateMsg = new ClientMsgProtobuf<CMsgClientChangeStatus>(CMsgClientChangeStatus.class, EMsg.ClientChangeStatus);
        stateMsg.getBody().personaState = cache.getLocalUser().personaState.v();
        stateMsg.getBody().playerName = name;
        getClient().send(stateMsg);
    }

    /**
     * Gets the local user's persona state.
     *
     * @return The persona state.
     */
    public EPersonaState getPersonaState() {
        return cache.getLocalUser().personaState;
    }

    /**
     * Sets the local user's persona state and broadcasts it over the network.
     *
     * @param state The state.
     */
    public void setPersonaState(EPersonaState state) {
        cache.getLocalUser().personaState = state;
        final ClientMsgProtobuf<CMsgClientChangeStatus> stateMsg = new ClientMsgProtobuf<CMsgClientChangeStatus>(CMsgClientChangeStatus.class, EMsg.ClientChangeStatus);
        stateMsg.getBody().personaState = state.v();
        stateMsg.getBody().playerName = cache.getLocalUser().name;
        getClient().send(stateMsg);
    }

    /**
     * Gets the friend count of the local user.
     *
     * @return The number of friends.
     */
    public int getFriendCount() {
        synchronized (listLock) {
            return friendList.size();
        }
    }

    /**
     * Gets a friend by index.
     *
     * @param index The index.
     * @return A valid steamid of a friend if the index is in range; otherwise a
     * steamid representing 0.
     */
    public SteamID getFriendByIndex(int index) {
        synchronized (listLock) {
            if (index < 0 || index >= friendList.size()) {
                return new SteamID();
            }
            return friendList.get(index);
        }
    }

    /**
     * Gets the persona name of a friend.
     *
     * @param steamId The steam id.
     * @return The name.
     */
    public String getFriendPersonaName(SteamID steamId) {
        return cache.getUser(steamId).name;
    }

    /**
     * Gets the nickname/alias of a friend.
     *
     * @param steamId The steam id.
     * @return The nickname.
     */
    public String getFriendNickname(SteamID steamId) {
        return cache.getUser(steamId).nickname;
    }

    /**
     * Gets the persona state of a friend.
     *
     * @param steamId The steam id.
     * @return The persona state.
     */
    public EPersonaState getFriendPersonaState(SteamID steamId) {
        return cache.getUser(steamId).personaState;
    }

    /**
     * Gets the relationship of a friend.
     *
     * @param steamId The steam id.
     * @return The relationship of the friend to the local user.
     */
    public EFriendRelationship getFriendRelationship(SteamID steamId) {
        return cache.getUser(steamId).relationship;
    }

    /**
     * Gets the last time a friend logged off.
     *
     * @param steamId The steam id.
     * @return The unix timestamp representing the last time a friend logged off.
     */
    public long getFriendLastLogoff(SteamID steamId) {
        return cache.getUser(steamId).lastLogoff;
    }

    /**
     * Gets the last time a friend logged on.
     *
     * @param steamId The steam id.
     * @return The unix timestamp representing the last time a friend logged on.
     */
    public long getFriendLastLogon(SteamID steamId) {
        return cache.getUser(steamId).lastLogon;
    }

    /**
     * Gets the game name of a friend playing a game.
     *
     * @param steamId The steam id.
     * @return The game name of a friend playing a game, or null if they haven't
     * been cached yet.
     */
    public String getFriendGamePlayedName(SteamID steamId) {
        return cache.getUser(steamId).gameName;
    }

    /**
     * Gets the GameID of a friend playing a game.
     *
     * @param steamId The steam id.
     * @return The gameid of a friend playing a game, or 0 if they haven't been
     * cached yet.
     */
    public GameID getFriendGamePlayed(SteamID steamId) {
        return cache.getUser(steamId).gameId;
    }

    /**
     * Gets a SHA-1 hash representing the friend's avatar.
     *
     * @param steamId The SteamID of the friend to get the avatar of.
     * @return A byte array representing a SHA-1 hash of the friend's avatar.
     */
    public byte[] getFriendAvatar(SteamID steamId) {
        return cache.getUser(steamId).avatarHash;
    }

    /**
     * Gets the count of clans the local user is a member of.
     *
     * @return The number of clans this user is a member of.
     */
    public int getClanCount() {
        synchronized (listLock) {
            return clanList.size();
        }
    }

    /**
     * Gets a clan SteamID by index.
     *
     * @param index The index.
     * @return A valid steamid of a clan if the index is in range; otherwise a
     * steamid representing 0.
     */
    public SteamID getClanByIndex(int index) {
        synchronized (listLock) {
            if (index < 0 || index >= clanList.size()) {
                return new SteamID();
            }
            return clanList.get(index);
        }
    }

    /**
     * Gets the name of a clan.
     *
     * @param steamId The clan SteamID.
     * @return The name.
     */
    public String getClanName(SteamID steamId) {
        return cache.getClans().getAccount(steamId).name;
    }

    /**
     * Gets the relationship of a clan.
     *
     * @param steamId The clan steamid.
     * @return The relationship of the clan to the local user.
     */
    public EClanRelationship getClanRelationship(SteamID steamId) {
        return cache.getClans().getAccount(steamId).relationship;
    }

    /**
     * Gets a SHA-1 hash representing the clan's avatar.
     *
     * @param steamId The SteamID of the clan to get the avatar of.
     * @return A byte array representing a SHA-1 hash of the clan's avatar, or
     * null if the clan could not be found.
     */
    public byte[] getClanAvatar(SteamID steamId) {
        return cache.getClans().getAccount(steamId).avatarHash;
    }

    /**
     * Sends a chat message to a friend.
     *
     * @param target  The target to send to.
     * @param type    The type of message to send.
     * @param message The message to send.
     */
    public void sendChatMessage(SteamID target, EChatEntryType type, String message) {
        final ClientMsgProtobuf<CMsgClientFriendMsg> chatMsg = new ClientMsgProtobuf<CMsgClientFriendMsg>(CMsgClientFriendMsg.class, EMsg.ClientFriendMsg);
        chatMsg.getBody().steamid = target.convertToLong();
        chatMsg.getBody().chatEntryType = type.v();
        chatMsg.getBody().message = message.getBytes();
        getClient().send(chatMsg);
    }

    /**
     * Sends a friend request to a user.
     *
     * @param accountNameOrEmail The account name or email of the user.
     */
    public void addFriend(String accountNameOrEmail) {
        final ClientMsgProtobuf<CMsgClientAddFriend> addFriend = new ClientMsgProtobuf<CMsgClientAddFriend>(CMsgClientAddFriend.class, EMsg.ClientAddFriend);
        addFriend.getBody().accountnameOrEmailToAdd = accountNameOrEmail;
        getClient().send(addFriend);
    }

    /**
     * Sends a friend request to a user.
     *
     * @param steamId The SteamID of the friend to add.
     */
    public void addFriend(SteamID steamId) {
        final ClientMsgProtobuf<CMsgClientAddFriend> addFriend = new ClientMsgProtobuf<CMsgClientAddFriend>(CMsgClientAddFriend.class, EMsg.ClientAddFriend);
        addFriend.getBody().steamidToAdd = steamId.convertToLong();
        getClient().send(addFriend);
    }

    /**
     * Removes a friend from your friends list.
     *
     * @param steamId The SteamID of the friend to remove.
     */
    public void removeFriend(SteamID steamId) {
        final ClientMsgProtobuf<CMsgClientRemoveFriend> removeFriend = new ClientMsgProtobuf<CMsgClientRemoveFriend>(CMsgClientRemoveFriend.class, EMsg.ClientRemoveFriend);
        removeFriend.getBody().friendid = steamId.convertToLong();
        getClient().send(removeFriend);
    }

    // the default details to request in most situations
    final int defaultInfoRequest = EClientPersonaStateFlag.PlayerName.v() | EClientPersonaStateFlag.Presence.v() | EClientPersonaStateFlag.SourceID.v() | EClientPersonaStateFlag.GameExtraInfo.v() | EClientPersonaStateFlag.LastSeen.v();

    /**
     * Requests persona state for a list of specified SteamID. Results are
     * returned in {@link PersonaStateCallback}.
     *
     * @param steamIdList   A list of SteamIDs to request the info of.
     * @param requestedInfo The requested info flags.
     */
    public void requestFriendInfo(Collection<SteamID> steamIdList, int requestedInfo) {
        final ClientMsgProtobuf<CMsgClientRequestFriendData> request = new ClientMsgProtobuf<CMsgClientRequestFriendData>(CMsgClientRequestFriendData.class, EMsg.ClientRequestFriendData);
        long[] friends = new long[steamIdList.size()];
        int i = 0;
        for (final SteamID steamId : steamIdList) {
            friends[i++] = steamId.convertToLong();
        }
        request.getBody().friends = friends;
        request.getBody().personaStateRequested = requestedInfo;
        getClient().send(request);
    }

    public void requestFriendInfo(Collection<SteamID> steamIdList, EClientPersonaStateFlag requestedInfo) {
        requestFriendInfo(steamIdList, requestedInfo.v());
    }

    public void requestFriendInfo(Collection<SteamID> steamIdList) {
        requestFriendInfo(steamIdList, defaultInfoRequest);
    }

    /**
     * Requests persona state for a specified SteamID. Results are returned in
     * {@link PersonaStateCallback}.
     *
     * @param steamId       A SteamID to request the info of.
     * @param requestedInfo The requested info flags.
     */
    public void requestFriendInfo(SteamID steamId, int requestedInfo) {
        final List<SteamID> temp = new ArrayList<SteamID>();
        temp.add(steamId);
        requestFriendInfo(temp, requestedInfo);
    }

    public void requestFriendInfo(SteamID steamId, EClientPersonaStateFlag requestedInfo) {
        requestFriendInfo(steamId, requestedInfo.v());
    }

    public void requestFriendInfo(SteamID steamId) {
        requestFriendInfo(steamId, defaultInfoRequest);
    }

    /**
     * Ignores or unignores a friend on Steam. Results are returned in a
     * {@link IgnoreFriendCallback}.
     *
     * @param steamId   The SteamID of the friend to ignore or unignore.
     * @param setIgnore if set to true, the friend will be ignored; otherwise, they
     *                  will be unignored.
     * @return The Job ID of the request. This can be used to find the
     * appropriate {@link JobCallback}.
     */
    public JobID ignoreFriend(SteamID steamId, boolean setIgnore) {
        final ClientMsg<MsgClientSetIgnoreFriend> ignore = new ClientMsg<MsgClientSetIgnoreFriend>(MsgClientSetIgnoreFriend.class);
        ignore.setSourceJobID(getClient().getNextJobID());
        ignore.getBody().setMySteamId(getClient().getSteamId());
        ignore.getBody().ignore = (byte) (setIgnore ? 1 : 0);
        ignore.getBody().setSteamIdFriend(steamId);
        getClient().send(ignore);
        return ignore.getSourceJobID();
    }

    public JobID ignoreFriend(SteamID steamId) {
        return ignoreFriend(steamId, true);
    }

    /**
     * Requests profile info for the given SteamID.
     * Results are returned in a {@link ProfileInfoCallback}
     *
     * @param steamId The SteamID to request the profile info of.
     * @return the JobID of the request
     */
    public JobID requestProfileInfo(SteamID steamId) {
        final ClientMsgProtobuf<CMsgClientFriendProfileInfo> request = new ClientMsgProtobuf<CMsgClientFriendProfileInfo>(CMsgClientFriendProfileInfo.class, EMsg.ClientFriendProfileInfo);
        request.setSourceJobID(getClient().getNextJobID());
        request.getBody().steamidFriend = steamId.convertToLong();
        getClient().send(request);
        return request.getSourceJobID();
    }

    /**
     * Requests Steam level for a list of specified SteamID. Results are
     * returned in {@link SteamLevelCallback}.
     *
     * @param steamIdList A list of SteamIDs to request the info of
     */
    public void requestSteamLevel(Collection<SteamID> steamIdList) {
        final ClientMsgProtobuf<CMsgClientFSGetFriendsSteamLevels> request = new ClientMsgProtobuf<CMsgClientFSGetFriendsSteamLevels>(CMsgClientFSGetFriendsSteamLevels.class, EMsg.ClientFSGetFriendsSteamLevels);
        int[] ids = new int[steamIdList.size()];
        int i = 0;
        for (final SteamID steamId : steamIdList) {
            ids[i++] = (int) steamId.getAccountID();
        }
        request.getBody().accountids = ids;
        getClient().send(request);
    }

    /**
     * Requests Steam level for a single SteamID.
     *
     * @param steamId the SteamID to query
     */
    public void requestSteamLevel(SteamID steamId) {
        final List<SteamID> temp = new ArrayList<SteamID>();
        temp.add(steamId);
        requestSteamLevel(temp);
    }

    /**
     * Requests message history for a specific friend
     *
     * @param steamId the SteamID of the friend to get history for
     */
    public void requestFriendMessageHistory(SteamID steamId) {
        final ClientMsgProtobuf<CMsgClientFSGetFriendMessageHistory> request = new ClientMsgProtobuf<CMsgClientFSGetFriendMessageHistory>(CMsgClientFSGetFriendMessageHistory.class, EMsg.ClientFSGetFriendMessageHistory);
        request.getBody().steamid = steamId.convertToLong();
        getClient().send(request);
    }

    /**
     * Requests message history for offline messages
     */
    public void requestOfflineMessageHistory() {
        final ClientMsgProtobuf<CMsgClientFSGetFriendMessageHistoryForOfflineMessages> request = new ClientMsgProtobuf<CMsgClientFSGetFriendMessageHistoryForOfflineMessages>(CMsgClientFSGetFriendMessageHistoryForOfflineMessages.class, EMsg.ClientFSGetFriendMessageHistoryForOfflineMessages);
        getClient().send(request);
    }

    /**
     * Handles a client message. This should not be called directly.
     */
    @Override
    public void handleMsg(IPacketMsg packetMsg) {
        switch (packetMsg.getMsgType()) {
            case ClientPersonaState:
                handlePersonaState(packetMsg);
                break;

            case ClientFriendsList:
                handleFriendsList(packetMsg);
                break;

            case ClientPlayerNicknameList:
                handlePlayerNicknameList(packetMsg);
                break;

            case ClientFriendMsgIncoming:
                handleFriendMsg(packetMsg);
                break;

            case ClientFriendMsgEchoToSender:
                handleFriendMsgEcho(packetMsg);
                break;

            case ClientAccountInfo:
                handleAccountInfo(packetMsg);
                break;

            case ClientAddFriendResponse:
                handleFriendResponse(packetMsg);
                break;

            case ClientSetIgnoreFriendResponse:
                handleIgnoreFriendResponse(packetMsg);
                break;

            case ClientFriendProfileInfoResponse:
                handleProfileInfoResponse(packetMsg);
                break;

            case ClientFSGetFriendsSteamLevelsResponse:
                handleSteamLevelResponse(packetMsg);
                break;

            case ClientFSGetFriendMessageHistoryResponse:
                handleFriendMessageHistoryResponse(packetMsg);
                break;
        }
    }

    void handleAccountInfo(IPacketMsg packetMsg) {
        final ClientMsgProtobuf<CMsgClientAccountInfo> accInfo = new ClientMsgProtobuf<CMsgClientAccountInfo>(CMsgClientAccountInfo.class, packetMsg);
        // cache off our local name
        cache.getLocalUser().name = accInfo.getBody().personaName;
    }

    void handleFriendMsg(IPacketMsg packetMsg) {
        final ClientMsgProtobuf<CMsgClientFriendMsgIncoming> friendMsg = new ClientMsgProtobuf<CMsgClientFriendMsgIncoming>(CMsgClientFriendMsgIncoming.class, packetMsg);
        final FriendMsgCallback callback = new FriendMsgCallback(friendMsg.getBody());
        getClient().postCallback(callback);
    }

    void handleFriendMsgEcho(IPacketMsg packetMsg) {
        final ClientMsgProtobuf<CMsgClientFriendMsgIncoming> friendMsgEcho = new ClientMsgProtobuf<CMsgClientFriendMsgIncoming>(CMsgClientFriendMsgIncoming.class, packetMsg);
        final FriendMsgEchoCallback callback = new FriendMsgEchoCallback(friendMsgEcho.getBody());
        getClient().postCallback(callback);
    }

    void handlePlayerNicknameList(IPacketMsg packetMsg) {
        final ClientMsgProtobuf<CMsgClientPlayerNicknameList> list = new ClientMsgProtobuf<CMsgClientPlayerNicknameList>(CMsgClientPlayerNicknameList.class, packetMsg);
        for (CMsgClientPlayerNicknameList.PlayerNickname friend : list.getBody().nicknames) {
            cache.getUser(new SteamID(friend.steamid)).nickname = friend.nickname;
        }
        final FriendsNicknameListCallback callback = new FriendsNicknameListCallback(list.getBody());
        getClient().postCallback(callback);
    }

    void handleFriendsList(IPacketMsg packetMsg) {
        final ClientMsgProtobuf<CMsgClientFriendsList> list = new ClientMsgProtobuf<CMsgClientFriendsList>(CMsgClientFriendsList.class, packetMsg);
        cache.getLocalUser().steamId = getClient().getSteamId();
        if (!list.getBody().bincremental) {
            // if we're not an incremental update, the message contains all friends, so we should clear our current list
            synchronized (listLock) {
                friendList.clear();
                clanList.clear();
            }
        }
        // we have to request information for all of our friends because steam only sends persona information for online friends
        final ClientMsgProtobuf<CMsgClientRequestFriendData> reqInfo = new ClientMsgProtobuf<CMsgClientRequestFriendData>(CMsgClientRequestFriendData.class, EMsg.ClientRequestFriendData);
        reqInfo.getBody().personaStateRequested = defaultInfoRequest;
        synchronized (listLock) {
            final List<SteamID> friendsToRemove = new ArrayList<SteamID>();
            final List<SteamID> clansToRemove = new ArrayList<SteamID>();
            long[] friendsArray = new long[list.getBody().friends.length];
            int i = 0;
            for (final CMsgClientFriendsList.Friend friendObj : list.getBody().friends) {
                final SteamID friendId = new SteamID(friendObj.ulfriendid);
                if (friendId.isIndividualAccount()) {
                    final User user = cache.getUser(friendId);
                    user.relationship = EFriendRelationship.f(friendObj.efriendrelationship);
                    if (friendList.contains(friendId)) {
                        // if this is a friend on our list, and they removed us, mark them for removal
                        if (user.relationship == EFriendRelationship.None) {
                            friendsToRemove.add(friendId);
                        }
                    } else {
                        // we don't know about this friend yet, lets add them
                        friendList.add(friendId);
                    }
                } else if (friendId.isClanAccount()) {
                    final Clan clan = cache.getClans().getAccount(friendId);
                    clan.relationship = EClanRelationship.f(friendObj.efriendrelationship);
                    if (clanList.contains(friendId)) {
                        // mark clans we were removed/kicked from
                        // note: not actually sure about the kicked relationship, but i'm using it for good measure
                        if (clan.relationship == EClanRelationship.None || clan.relationship == EClanRelationship.Kicked) {
                            clansToRemove.add(friendId);
                        }
                    } else {
                        // don't know about this clan, add it
                        clanList.add(friendId);
                    }
                }
                if (!list.getBody().bincremental) {
                    // request persona state for our friend & clan list when it's a non-incremental update
                    friendsArray[i++] = friendId.convertToLong();
                }
            }
            reqInfo.getBody().friends = friendsArray;
            // remove anything we marked for removal
            friendList.removeAll(friendsToRemove);
            clanList.removeAll(clansToRemove);
        }
        if (reqInfo.getBody().friends.length > 0) {
            getClient().send(reqInfo);
        }
        final FriendsListCallback callback = new FriendsListCallback(list.getBody());
        getClient().postCallback(callback);
    }

    void handlePersonaState(IPacketMsg packetMsg) {
        final ClientMsgProtobuf<CMsgClientPersonaState> perState = new ClientMsgProtobuf<CMsgClientPersonaState>(CMsgClientPersonaState.class, packetMsg);
        final int flags = perState.getBody().statusFlags;
        for (final CMsgClientPersonaState.Friend friend : perState.getBody().friends) {
            final SteamID friendId = new SteamID(friend.friendid);
            //SteamID sourceId = new SteamID(friend.getSteamidSource());
            if (friendId.isIndividualAccount()) {
                final User cacheFriend = cache.getUser(friendId);
                if ((flags & EClientPersonaStateFlag.PlayerName.v()) == EClientPersonaStateFlag.PlayerName.v()) {
                    cacheFriend.name = friend.playerName;
                }
                //if ((flags & EClientPersonaStateFlag.Presence.v()) == EClientPersonaStateFlag.Presence.v()) {
                if (friend.avatarHash != null) cacheFriend.avatarHash = friend.avatarHash;
                cacheFriend.personaState = EPersonaState.f(friend.personaState);
                //}
                if ((flags & EClientPersonaStateFlag.LastSeen.v()) == EClientPersonaStateFlag.LastSeen.v()) {
                    cacheFriend.lastLogoff = friend.lastLogoff;
                    cacheFriend.lastLogon = friend.lastLogon;
                }
                if ((flags & EClientPersonaStateFlag.GameExtraInfo.v()) == EClientPersonaStateFlag.GameExtraInfo.v()) {
                    cacheFriend.gameName = friend.gameName;
                    cacheFriend.gameId = new GameID(friend.gameid);
                    cacheFriend.gameAppId = friend.gamePlayedAppId;
                }
            } else if (friendId.isClanAccount()) {
                final Clan cacheClan = cache.getClans().getAccount(friendId);
                if ((flags & EClientPersonaStateFlag.PlayerName.v()) == EClientPersonaStateFlag.PlayerName.v()) {
                    cacheClan.name = friend.playerName;
                }
            }
            // TODO: cache other details/account types?
        }
        for (final CMsgClientPersonaState.Friend friend : perState.getBody().friends) {
            final PersonaStateCallback callback = new PersonaStateCallback(friend);
            getClient().postCallback(callback);
        }
    }

    void handleFriendResponse(IPacketMsg packetMsg) {
        final ClientMsgProtobuf<CMsgClientAddFriendResponse> friendResponse = new ClientMsgProtobuf<CMsgClientAddFriendResponse>(CMsgClientAddFriendResponse.class, packetMsg);
        final FriendAddedCallback callback = new FriendAddedCallback(friendResponse.getBody());
        getClient().postCallback(callback);
    }

    void handleIgnoreFriendResponse(IPacketMsg packetMsg) {
        final ClientMsg<MsgClientSetIgnoreFriendResponse> response = new ClientMsg<MsgClientSetIgnoreFriendResponse>(packetMsg, MsgClientSetIgnoreFriendResponse.class);
        final IgnoreFriendCallback innerCallback = new IgnoreFriendCallback(response.getBody());
        final JobCallback<?> callback = new JobCallback<IgnoreFriendCallback>(response.getTargetJobID(), innerCallback);
        getClient().postCallback(callback);
    }

    void handleProfileInfoResponse(IPacketMsg packetMsg) {
        final ClientMsgProtobuf<CMsgClientFriendProfileInfoResponse> response = new ClientMsgProtobuf<CMsgClientFriendProfileInfoResponse>(CMsgClientFriendProfileInfoResponse.class, packetMsg);
        final ProfileInfoCallback callback = new ProfileInfoCallback(response.getBody());
        getClient().postCallback(callback);
    }

    void handleSteamLevelResponse(IPacketMsg packetMsg) {
        final ClientMsgProtobuf<CMsgClientFSGetFriendsSteamLevelsResponse> response = new ClientMsgProtobuf<CMsgClientFSGetFriendsSteamLevelsResponse>(CMsgClientFSGetFriendsSteamLevelsResponse.class, packetMsg);
        final SteamLevelCallback callback = new SteamLevelCallback(response.getBody());
        getClient().postCallback(callback);
    }

    void handleFriendMessageHistoryResponse(IPacketMsg packetMsg) {
        final ClientMsgProtobuf<CMsgClientFSGetFriendMessageHistoryResponse> response = new ClientMsgProtobuf<CMsgClientFSGetFriendMessageHistoryResponse>(CMsgClientFSGetFriendMessageHistoryResponse.class, packetMsg);
        final FriendMsgHistoryCallback callback = new FriendMsgHistoryCallback(response.getBody());
        getClient().postCallback(callback);
    }

    public List<SteamID> getFriendList() {
        return this.friendList;
    }

    public List<SteamID> getClanList() {
        return this.clanList;
    }
}
