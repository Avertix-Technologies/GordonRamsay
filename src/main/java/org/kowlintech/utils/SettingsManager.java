package org.kowlintech.utils;

import org.kowlintech.GordonRamsay;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingsManager {

    public static void setGuildTemperatureUnit(long guildId, String unit) throws SQLException {
        PreparedStatement st = GordonRamsay.getDatabaseConnection().prepareStatement("SELECT * FROM settings WHERE guildid=?");
        st.setLong(1, guildId);
        ResultSet rs = st.executeQuery();
        String result = null;
        while (rs.next()) {
            result = rs.getString("temperatureunit");
        }
        if(result != null) {
            PreparedStatement st1 = GordonRamsay.getDatabaseConnection().prepareStatement("UPDATE settings SET temperatureunit=? WHERE guildid=?");
            st1.setString(1, unit);
            st1.setLong(2, guildId);
            st1.execute();
        } else {
            PreparedStatement st1 = GordonRamsay.getDatabaseConnection().prepareStatement("INSERT INTO settings (guildid, temperatureunit) VALUES(?, ?)");
            st1.setLong(1, guildId);
            st1.setString(2, unit);
            st1.execute();
        }
    }

    public static String getGuildTemperatureUnit(long guildId) throws SQLException {
        PreparedStatement st = GordonRamsay.getDatabaseConnection().prepareStatement("SELECT * FROM settings WHERE guildid=?");
        st.setLong(1, guildId);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            return rs.getString("temperatureunit").toLowerCase().trim();
        }
        return null;
    }
}
