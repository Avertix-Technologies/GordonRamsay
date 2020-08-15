package org.kowlintech.utils.command.objects;

import org.kowlintech.GordonRamsay;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChangelogMessage implements IChangelogMessage {

    private long creationDate;
    private String title;
    private String description;

    public ChangelogMessage(long creationDate, String title, String description) {
        this.creationDate = creationDate;
        this.title = title;
        this.description = description;
    }

    /**
     * @return When the Changelog message was created.
     */
    @Override
    public long getCreationDate() {
        return creationDate;
    }

    /**
     * @return The title of the Changelog message.
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * @return The description of the Changelog message.
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * @param newTitle       New title of the Changelog message.
     * @param newDescription New description for the Changelog message.
     */
    @Override
    public void edit(String newTitle, String newDescription) throws SQLException {
        PreparedStatement st = GordonRamsay.getDatabaseConnection().prepareStatement("UPDATE changelog SET title=? AND description=? WHERE timeadded=?");
        st.setString(1, newTitle);
        st.setString(2, newDescription);
        st.setLong(3, getCreationDate());
        st.execute();
    }

    @Override
    public void delete() throws SQLException {
        PreparedStatement st = GordonRamsay.getDatabaseConnection().prepareStatement("DELETE FROM changelog WHERE timeadded=?");
        st.setLong(1, getCreationDate());
        st.execute();
    }
}
