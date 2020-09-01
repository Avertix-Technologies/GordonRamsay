package org.kowlintech.utils.command;

import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.ObjectCommand;

import java.util.ArrayList;

public class CommandManager {

    public ArrayList<Command> getCommands() {
        ArrayList<Command> list = new ArrayList<>();
        for(ObjectCommand command : GordonRamsay.getCommands()) {
            list.add(command.getInterface());
        }

        return list;
    }

    public Command getCommand(String command) {
        for(ObjectCommand command1 : GordonRamsay.getCommands()) {
            if(command1.getInterface().name() == command.trim().toLowerCase()) {
                return command1.getInterface();
            }
        }

        return null;
    }
}
