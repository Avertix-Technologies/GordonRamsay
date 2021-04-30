package org.kowlintech.utils.command.objects;

import java.sql.SQLException;

public interface SlashCommandExecutor {

    void execute(SlashCommandEvent event) throws SQLException;
}