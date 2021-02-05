package org.kowlintech.utils;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.command.objects.EconomyUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EconomyManager {

    private JDA JDA_INSTANCE;
    private Connection DATABASE;
    public List<EconomyUser> USERS;

    public EconomyManager(JDA jda, Connection database) {
        JDA_INSTANCE = jda;
        DATABASE = database;
        USERS = new ArrayList<>();
    }

    public EconomyUser registerUser(User user, Guild guild) {
        executeStatement("INSERT INTO economy (coins, userid, guildid) VALUES(0, " + user.getIdLong() + ", " + guild.getIdLong() + ");");
        EconomyUser registered = new EconomyUser(user.getIdLong(), 0L, guild.getIdLong());

        USERS.add(registered);
        return registered;
    }

    private void executeStatement(String statement) {
        try {
            PreparedStatement st = DATABASE.prepareStatement(statement);

            st.execute();

            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private ResultSet executeQuery(String query) {
        try {
            PreparedStatement st = DATABASE.prepareStatement(query);
            return st.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public EconomyUser getUser(User user, Guild guild) {
        for(EconomyUser ecouser : USERS) {
            if(ecouser.getUserId() == user.getIdLong() && ecouser.getGuildId() == guild.getIdLong()) {
                return ecouser;
            }
        }

        try {
            PreparedStatement st = GordonRamsay.getDatabaseConnection().prepareStatement("SELECT * FROM economy WHERE userid=" + user.getId() + " AND guildid=" + guild.getId() + ";");
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                return new EconomyUser(user.getIdLong(), rs.getLong("coins"), guild.getIdLong());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        EconomyUser registered = registerUser(user, guild);
        return registered;
    }
}
