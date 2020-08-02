package org.kowlintech.utils.command.objects.enums;

import jdk.internal.jline.internal.Nullable;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandExecutor;

public enum EnumCommand {

    HELP();

    private String[] aliases;
    private CommandExecutor executor;

    EnumCommand(CommandExecutor executor, String[] aliases) {
        this.executor = executor;
        this.aliases = aliases;
    }

    public CommandExecutor getExecutor() {
        return executor;
    }

    public String[] getAliases() {
        return aliases;
    }
}
