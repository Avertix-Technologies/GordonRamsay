package org.kowlintech.utils;

import org.kowlintech.GordonRamsay;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FeedbackManager {

    private ArrayList<Long> BLACKLISTED_USERS;
    private ArrayList<Long> BLACKLISTED_GUILDS;

    public FeedbackManager() {
        initialize();
    }

    public void toggleBlacklistUser(Long user_id) {
        try {
            if(isUserBlacklisted(user_id)) {
                PreparedStatement st = GordonRamsay.getDatabaseConnection().prepareStatement("DELETE FROM fb_user_blacklist WHERE userid=?");
                st.setLong(1, user_id);
                st.execute();
            } else {
                PreparedStatement st = GordonRamsay.getDatabaseConnection().prepareStatement("INSERT INTO fb_user_blacklist (userid) VALUES(?)");
                st.setLong(1, user_id);
                st.execute();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void toggleBlacklistGuild(Long guild_id) {
        try {
            if(isGuildBlacklisted(guild_id)) {
                PreparedStatement st = GordonRamsay.getDatabaseConnection().prepareStatement("DELETE FROM fb_guild_blacklist WHERE guildid=?");
                st.setLong(1, guild_id);
                st.execute();
            } else {
                PreparedStatement st = GordonRamsay.getDatabaseConnection().prepareStatement("INSERT INTO fb_guild_blacklist (guildid) VALUES(?)");
                st.setLong(1, guild_id);
                st.execute();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void initialize() {
        BLACKLISTED_USERS = new ArrayList<>();
        BLACKLISTED_GUILDS = new ArrayList<>();
        try {
            PreparedStatement st = GordonRamsay.getDatabaseConnection().prepareStatement("SELECT * FROM fb_user_blacklist");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                BLACKLISTED_USERS.add(rs.getLong("userid"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            PreparedStatement st = GordonRamsay.getDatabaseConnection().prepareStatement("SELECT * FROM fb_guild_blacklist");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                BLACKLISTED_GUILDS.add(rs.getLong("guildid"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean isUserBlacklisted(Long user_id) {
        return BLACKLISTED_USERS.contains(user_id);
    }

    public boolean isGuildBlacklisted(Long guild_id) {
        return BLACKLISTED_GUILDS.contains(guild_id);
    }
}
