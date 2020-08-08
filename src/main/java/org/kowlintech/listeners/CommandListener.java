package org.kowlintech.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.kowlintech.Config;
import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.enums.Category;
import org.kowlintech.utils.command.objects.enums.EnumCommand;
import org.kowlintech.utils.command.objects.enums.PermissionType;
import org.kowlintech.utils.constants.Global;

import java.lang.annotation.Annotation;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config config = new Config();

        if(!event.getMessage().getContentRaw().startsWith(config.getPrefix()) || event.getAuthor().isBot()) {
            return;
        }
        String[] messageSplit = event.getMessage().getContentRaw().split(config.getPrefix());
        for(EnumCommand command : EnumCommand.values()) {
            ArrayList<String> aliases = new ArrayList<>();
            for(String alias : command.getAliases()) {
                aliases.add(alias);
            }
            if(messageSplit[1].startsWith(command.toString().toLowerCase()) || messageSplit[1].equals(command.toString().toLowerCase()) || aliases.contains(messageSplit[1])) {
                Annotation annotation = command.getExecutor().getClass().getDeclaredAnnotation(org.kowlintech.utils.command.objects.Command.class);
                org.kowlintech.utils.command.objects.Command cmd = (org.kowlintech.utils.command.objects.Command) annotation;

                if(cmd.category().equals(Category.OWNER) && !GordonRamsay.devIds.contains(event.getAuthor().getId())) {
                    event.getChannel().sendMessage("You can't use this command, you fucking idiot!").queue();
                    return;
                }

                if(cmd.permission() != PermissionType.NONE && !event.getMember().hasPermission(cmd.permission().getPermission())) {
                    event.getChannel().sendMessage("You must have the `" + cmd.permission().getPermission().getName() + "` permission to use that command.").queue();
                    return;
                }

                if(cmd.args().isEmpty()) {
                    try {
                        command.getExecutor().execute(new CommandEvent(event.getGuild(), "", event, event.getJDA(), event.getMember(), event.getChannel()));
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                    return;
                }

                String[] classArgs = cmd.args().split(" ");

                if(cmd.args().startsWith("[")) {
                    try {
                        StringBuilder message = new StringBuilder();
                        for (int i = 1; i < event.getMessage().getContentRaw().split(" ").length; i++) {
                            message.append(event.getMessage().getContentRaw().split(" ")[i]).append(" ");
                        }

                        command.getExecutor().execute(new CommandEvent(event.getGuild(), message.toString().trim(), event, event.getJDA(), event.getMember(), event.getChannel()));
                        return;
                    } catch (IndexOutOfBoundsException | SQLException ex) {
                        try {
                            command.getExecutor().execute(new CommandEvent(event.getGuild(), "", event, event.getJDA(), event.getMember(), event.getChannel()));
                        } catch (SQLException exception) {
                            exception.printStackTrace();
                        }
                        return;
                    }
                }

                StringBuilder message = new StringBuilder();
                for (int i = 1; i < event.getMessage().getContentRaw().split(" ").length; i++) {
                    message.append(event.getMessage().getContentRaw().split(" ")[i]).append(" ");
                }

                if(message.toString().split(" ").length < classArgs.length) {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle(cmd.name().substring(0, 1).toUpperCase() + cmd.name().substring(1) + " Command");
                    String cmdusage;
                    if(cmd.args() == null) {
                        cmdusage = cmd.description() + "\n\n**Usage:** g." + cmd.name();
                    } else {
                        cmdusage = cmd.description() + "\n\n**Usage:** g." + cmd.name() + " " + cmd.args();
                    }
                    eb.setDescription(cmdusage);
                    eb.setColor(Global.COLOR);
                    event.getChannel().sendMessage(eb.build()).queue();
                    return;
                }

                try {
                    command.getExecutor().execute(new CommandEvent(event.getGuild(), message.toString().trim(), event, event.getJDA(), event.getMember(), event.getChannel()));
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}
