package org.kowlintech.utils.snipe;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.jodah.expiringmap.ExpiringMap;
import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.snipe.objects.SnipeGuild;
import org.kowlintech.utils.snipe.objects.SnipeMessage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SnipeManager {

    private Map<Long, Message> MESSAGES;

    public SnipeManager() {
        MESSAGES = ExpiringMap.builder()
                .maxSize(200)
                .expiration(1, TimeUnit.HOURS)
                .build();
    }

    public SnipeGuild getGuild(long guild) {
        SnipeGuild guild_prepared = null;
        try {
            PreparedStatement st = GordonRamsay.getDatabaseConnection().prepareStatement("SELECT * FROM snipe_settings WHERE guildid=?");
            st.setLong(1, guild);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                guild_prepared = new SnipeGuild(GordonRamsay.jda.getGuildById(guild), rs.getBoolean("enabled"));;
            }

            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if(guild_prepared == null) {
            try {
                PreparedStatement st = GordonRamsay.getDatabaseConnection().prepareStatement("INSERT INTO snipe_settings (guildid, enabled) VALUES(?, ?)");
                st.setLong(1, guild);
                st.setBoolean(2, false);

                st.execute();
                st.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            return new SnipeGuild(GordonRamsay.jda.getGuildById(guild), false);
        } else {
            return guild_prepared;
        }
    }

    public boolean doesMessageExist(long guild, long message) {
        return (MESSAGES.get(message) != null);
    }

    public SnipeMessage getMessage(long guild, long message) {
        if(MESSAGES.get(message) != null) {
            Message message_object = MESSAGES.get(message);
            MESSAGES.remove(message);
            return new SnipeMessage(message_object.getContentRaw().trim(), message_object.getAuthor().getIdLong(), message_object.getGuild().getIdLong(), message_object.getChannel().getIdLong(), message_object.getIdLong(), message_object.getTimeCreated());
        } else {
            return null;
        }
    }

    public void insertMessage(Message message) {
        MESSAGES.put(message.getIdLong(), message);
    }
}
