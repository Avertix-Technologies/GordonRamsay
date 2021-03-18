package org.kowlintech.utils;

import org.kowlintech.GordonRamsay;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BlacklistManager {

    public List<Long> blacklist;

    public BlacklistManager() {
        initialize();
    }

    public List<Long> getBlacklist() {
        return blacklist;
    }

    public void addUser(Long userid) {
        try {
            PreparedStatement st = GordonRamsay.getDatabaseConnection().prepareStatement("INSERT INTO user_blacklist (userid) VALUES(?)");
            st.setLong(1, userid);
            st.execute();

            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        blacklist.add(userid);
    }

    public void removeUser(Long userid) {
        try {
            PreparedStatement st = GordonRamsay.getDatabaseConnection().prepareStatement("DELETE FROM user_blacklist WHERE userid=?");
            st.setLong(1, userid);
            st.execute();

            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        blacklist.remove(userid);
    }

    private void initialize() {
        blacklist = new ArrayList<>();
        try {
            PreparedStatement st = GordonRamsay.getDatabaseConnection().prepareStatement("SELECT * FROM user_blacklist");
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                blacklist.add(rs.getLong("userid"));
            }

            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
