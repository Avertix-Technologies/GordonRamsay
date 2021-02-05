package org.kowlintech.utils.snipe.objects;

import java.time.OffsetDateTime;
import java.util.Date;

public class SnipeMessage {

    private String CONTENTS;
    private long USER_ID;
    private long GUILD_ID;
    private long CHANNEL_ID;
    private long MESSAGE_ID;
    private OffsetDateTime TIME;

    public SnipeMessage(String contents, long userid, long guildid, long channelid, long messageid, OffsetDateTime time) {
        CONTENTS = contents;
        USER_ID = userid;
        GUILD_ID = guildid;
        CHANNEL_ID = channelid;
        MESSAGE_ID = messageid;
        TIME = time;
    }

    public String getContents() {
        return CONTENTS;
    }

    public long getUserId() {
        return USER_ID;
    }

    public long getGuildId() {
        return GUILD_ID;
    }

    public long getChannelId() {
        return CHANNEL_ID;
    }

    public long getMessageId() {
        return MESSAGE_ID;
    }

    public OffsetDateTime getDateTime() {
        return TIME;
    }
}
