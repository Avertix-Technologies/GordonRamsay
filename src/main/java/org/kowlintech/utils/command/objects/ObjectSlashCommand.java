package org.kowlintech.utils.command.objects;

public class ObjectSlashCommand {

    private SlashCommandExecutor commandExecutor;
    private SlashCommand commandInterface;

    public ObjectSlashCommand(SlashCommandExecutor executor, SlashCommand command) {
        commandExecutor = executor;
        commandInterface = command;
    }

    public SlashCommandExecutor getExecutor() {
        return commandExecutor;
    }

    public SlashCommand getInterface() {
        return commandInterface;
    }
}
