package org.kowlintech.utils.command.objects.enums;

import net.dv8tion.jda.api.Permission;

public enum PermissionType {

    KICK("Kick", Permission.KICK_MEMBERS),
    BAN("Ban", Permission.BAN_MEMBERS),
    DELETE_MESSAGES("Delete Messages", Permission.MESSAGE_MANAGE),
    NONE("None", null);

    private String name;
    private Permission permission;

    PermissionType(String name, Permission permission) {
        this.name = name;
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public Permission getPermission() {
        return permission;
    }
}
