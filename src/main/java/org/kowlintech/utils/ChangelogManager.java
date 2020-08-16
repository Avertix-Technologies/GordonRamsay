package org.kowlintech.utils;

import net.dv8tion.jda.api.JDA;
import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.command.objects.ChangelogMessage;
import org.kowlintech.utils.command.objects.IChangelogMessage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChangelogManager {

    private JDA jda;

    public ChangelogManager(JDA jda) {
        this.jda = jda;
    }

    public IChangelogMessage createMessage(String title, String description) throws SQLException {
        long time = System.currentTimeMillis();
        PreparedStatement st = GordonRamsay.getDatabaseConnection().prepareStatement("INSERT INTO changelog (timeadded, title, description) VALUES(?, ?, ?);");
        st.setLong(1, time);
        st.setString(2, title);
        st.setString(3, description);
        st.execute();

        System.out.println("Changelog Database Statement: \"" + String.format("INSERT INTO changelog (timeadded, title, description) VALUES(%s, %s, %s);", time, title, description) + "\"");

        return new ChangelogMessage(time, title, description);
    }

    public IChangelogMessage getMessage(long timeAdded) throws SQLException {
        PreparedStatement st = GordonRamsay.getDatabaseConnection().prepareStatement("SELECT * FROM changelog WHERE timeadded=?");
        st.setLong(1, timeAdded);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            return new ChangelogMessage(rs.getLong("timeadded"), rs.getString("title"), rs.getString("description"));
        }

        return null;
    }

    public IChangelogMessage editMessage(long timeAdded, String newTitle, String newDescription) throws SQLException {
        PreparedStatement st = GordonRamsay.getDatabaseConnection().prepareStatement("UPDATE changelog SET title=? AND description=? WHERE timeadded=?");
        st.setString(1, newTitle);
        st.setString(2, newDescription);
        st.setLong(3, timeAdded);
        st.execute();

        return new ChangelogMessage(timeAdded, newTitle, newDescription);
    }

    public void deleteMessage(Long timeAdded) throws SQLException {
        PreparedStatement st = GordonRamsay.getDatabaseConnection().prepareStatement("DELETE FROM changelog WHERE timeadded=?");
        st.setLong(1, timeAdded);
        st.execute();
    }

    public ArrayList<IChangelogMessage> getLatestChangelogMessages() throws SQLException {
        ArrayList<IChangelogMessage> array = new ArrayList<>();
        PreparedStatement st = GordonRamsay.getDatabaseConnection().prepareStatement("SELECT * FROM changelog ORDER BY timeadded DESC LIMIT 5");
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            array.add(new ChangelogMessage(rs.getLong("timeadded"), rs.getString("title"), rs.getString("description")));
        }
        return array;
    }
}
