package org.kowlintech.utils;

import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.ObjectCommand;

public class AliasManager {

    public boolean addAlias(ObjectCommand command, String alias) {
        if(!isAliasRegistered(alias)) {
            register(command, alias);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeAlias(String alias) {
        if(isAliasRegistered(alias)) {
            GordonRamsay.aliases.remove(alias);
            return true;
        } else {
            return false;
        }
    }

    public boolean isAliasRegistered(String alias) {
        if(GordonRamsay.aliases.get(alias) != null) {
            return true;
        } else {
            return false;
        }
    }

    private void register(ObjectCommand command, String alias) {
        GordonRamsay.aliases.put(alias, command);
    }
}
