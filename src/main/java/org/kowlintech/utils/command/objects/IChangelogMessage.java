package org.kowlintech.utils.command.objects;

import java.sql.SQLException;

public interface IChangelogMessage {

    /**
     * @return When the Changelog message was created.
     */
    long getCreationDate();

    /**
     * @return The title of the Changelog message.
     */
    String getTitle();

    /**
     * @return The description of the Changelog message.
     */
    String getDescription();

    /**
     * @param newTitle New title of the Changelog message.
     * @param newDescription New description for the Changelog message.
     */
    void edit(String newTitle, String newDescription) throws SQLException;

    void delete() throws SQLException;
}
