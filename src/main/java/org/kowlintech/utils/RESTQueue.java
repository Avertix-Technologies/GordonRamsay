package org.kowlintech.utils;

import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.*;

public class RESTQueue {

    public static void addToQueue(MessageAction action) {
        action.queue();
    }

    public static void addToQueue(GuildAction action) {
        action.queue();
    }

    public static void addToQueue(ChannelAction action) {
        action.queue();
    }

    public static void addToQueue(MemberAction action) {
        action.queue();
    }

    public static void addToQueue(RoleAction action) {
        action.queue();
    }

    public static void addToQueue(RestAction action) {
        action.queue();
    }
}
