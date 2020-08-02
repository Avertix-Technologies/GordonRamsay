package org.kowlintech.utils.command.objects;

import java.sql.SQLException;

public interface CommandExecutor {

    void execute(CommandEvent event) throws SQLException;
}