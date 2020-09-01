package org.kowlintech.utils.command.objects;

public class ObjectCommand {

    private CommandExecutor commandExecutor;
    private Command commandInterface;

    public ObjectCommand(CommandExecutor executor, Command command) {
        commandExecutor = executor;
        commandInterface = command;
    }

    public CommandExecutor getExecutor() {
        return commandExecutor;
    }

    public Command getInterface() {
        return commandInterface;
    }
}
