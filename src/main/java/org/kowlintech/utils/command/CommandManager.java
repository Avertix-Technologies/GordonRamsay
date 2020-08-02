package org.kowlintech.utils.command;

import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.enums.EnumCommand;

import java.lang.annotation.Annotation;
import java.util.ArrayList;

public class CommandManager {

    public ArrayList<Command> getCommands() {
        ArrayList<Command> list = new ArrayList<>();
        for(EnumCommand command : EnumCommand.values()) {
            Annotation annotation = command.getExecutor().getClass().getDeclaredAnnotation(org.kowlintech.utils.command.objects.Command.class);
            org.kowlintech.utils.command.objects.Command cmd = (org.kowlintech.utils.command.objects.Command) annotation;

            list.add(cmd);
        }

        return list;
    }

    public Command getCommand(String command) {
        Annotation annotation = EnumCommand.valueOf(command.toUpperCase()).getExecutor().getClass().getDeclaredAnnotation(org.kowlintech.utils.command.objects.Command.class);
        org.kowlintech.utils.command.objects.Command cmd = (org.kowlintech.utils.command.objects.Command) annotation;
        return cmd;
    }
}
