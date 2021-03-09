package org.kowlintech.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.ObjectCommand;
import org.kowlintech.utils.command.objects.enums.Category;
import org.kowlintech.utils.command.objects.enums.PermissionType;
import org.kowlintech.utils.constants.Global;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Properties;

public class CommandListener extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Properties prop = new Properties();
        try {
            FileInputStream file = new FileInputStream("gordon.properties");
            prop.load(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        if(!event.getMessage().getContentRaw().startsWith(prop.getProperty("prefix")) || event.getAuthor().isBot()) {
            if(event.getMessage().getContentRaw().contains("<@!528984558629027841>") || event.getMessage().getContentRaw().contains("<@528984558629027841>")) {
                event.getChannel().sendMessage("Prefix: `g.`").queue();
                return;
            }
            return;
        }
        for(ObjectCommand command : GordonRamsay.getCommands()) {
            try {
                if (event.getMessage().getContentRaw().startsWith((prop.getProperty("prefix") + command.getInterface().name() + " ").trim().toLowerCase()) || event.getMessage().getContentRaw().equalsIgnoreCase(prop.getProperty("prefix") + command.getInterface().name())) {
                    Command cmd = command.getInterface();
                    if (cmd.category().equals(Category.OWNER) && !GordonRamsay.devIds.contains(event.getAuthor().getId())) {
                        event.getChannel().sendMessage("You can't use this command, you fucking idiot!").queue();
                        return;
                    }

                    if (cmd.permission() != PermissionType.NONE && !event.getMember().hasPermission(cmd.permission().getPermission())) {
                        event.getChannel().sendMessage("You must have the `" + cmd.permission().getPermission().getName() + "` permission to use that command.").queue();
                        return;
                    }

                    if (cmd.args().isEmpty()) {
                        try {
                            command.getExecutor().execute(new CommandEvent(event.getGuild(), "", event, event.getJDA(), event.getMember(), event.getChannel()));
                        } catch (SQLException exception) {
                            exception.printStackTrace();
                        }
                        return;
                    }

                    String[] classArgs = cmd.args().split(" ");

                    if (cmd.args().startsWith("[")) {
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

                    if (message.toString().split(" ").length < classArgs.length) {
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setTitle(cmd.name().substring(0, 1).toUpperCase() + cmd.name().substring(1) + " Command");
                        String cmdusage;
                        if (cmd.args() == null) {
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
                    return;
                }

                for(String alias : GordonRamsay.aliases.keySet()) {
                    if(event.getMessage().getContentRaw().startsWith((prop.getProperty("prefix") + alias))) {
                        StringBuilder message = new StringBuilder();
                        for (int i = 1; i < event.getMessage().getContentRaw().split(" ").length; i++) {
                            message.append(event.getMessage().getContentRaw().split(" ")[i]).append(" ");
                        }

                        GordonRamsay.aliases.get(alias).getExecutor().execute(new CommandEvent(event.getGuild(), message.toString().trim(), event, event.getJDA(), event.getMember(), event.getChannel()));
                        return;
                    }
                }
            } catch (Exception ex) {
                if(ex instanceof InsufficientPermissionException) {
                    event.getAuthor().openPrivateChannel().queue(channel -> {
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setTitle("Command Error");
                        eb.setDescription("It looks like I'm not able to send the response for `" + command.getInterface().name().toLowerCase() + "` to that channel. If you're an administrator, please change the permissions in that channel. If not, please contact an administrator with this issue.");
                        eb.setColor(Color.RED);
                        eb.setTimestamp(LocalDateTime.now(ZoneId.systemDefault()));
                        try {
                            channel.sendMessage(eb.build()).queue();
                        } catch (Exception ex1) {
                            return;
                        }
                    });
                    return;
                }
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Command Error");
                eb.setDescription("```" + ex.toString() + "```");
                eb.addField("Command Name", command.getInterface().name().toLowerCase(), true);
                eb.addField("Executor", event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator() + " (" + event.getAuthor().getId() + ")", true);
                eb.addField("Guild", event.getGuild().getName() + " (" + event.getGuild().getId() + ")", true);
                eb.addField("Channel", event.getChannel().getName() + " (" + event.getChannel().getId() + ")", true);
                eb.setColor(Color.RED);
                eb.setTimestamp(LocalDateTime.now(ZoneId.systemDefault()));
                event.getJDA().getTextChannelById("744579696695705832").sendMessage(eb.build()).queue();
            }
        }
    }
}
