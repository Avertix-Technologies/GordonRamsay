package org.kowlintech.utils.command.objects;

import java.sql.SQLException;

public interface SubCommandExecutor {

    void execute(CommandEvent event) throws SQLException;
}