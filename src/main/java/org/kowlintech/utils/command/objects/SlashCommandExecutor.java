package org.kowlintech.utils.command.objects;

import java.sql.SQLException;

public interface SlashCommandExecutor {

    void execute(CommandEvent event) throws SQLException;
}