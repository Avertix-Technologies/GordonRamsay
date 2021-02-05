package org.kowlintech.listeners;

import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.snipe.SnipeManager;
import org.kowlintech.utils.snipe.objects.SnipeMessage;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SnipeListener extends ListenerAdapter {

    @Override
    public void onMessageDelete(MessageDeleteEvent event) {
        SnipeManager manager = GordonRamsay.getSnipeManager();

        if(manager.getGuild(event.getGuild().getIdLong()).isSnipeEnabled()) {
            SnipeMessage message = manager.getMessage(event.getGuild().getIdLong(), event.getMessageIdLong());
            if(message != null) {
                try {
                    PreparedStatement st = GordonRamsay.getDatabaseConnection().prepareStatement("INSERT INTO snipe(contents, usr, guild, channel, message, bot, time) VALUES(?, ?, ?, ?, ?, ?)");
                    st.setString(1, message.getContents());
                    st.setLong(2, message.getUserId());
                    st.setLong(3, message.getGuildId());
                    st.setLong(4, message.getChannelId());
                    st.setLong(5, message.getMessageId());
                    st.setLong(6, message.getDateTime().toEpochSecond());

                    st.execute();
                    st.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
