package org.kowlintech.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.kowlintech.Config;
import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.EmbedHelper;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.enums.Category;
import org.kowlintech.utils.command.objects.enums.EnumCommand;
import org.kowlintech.utils.command.objects.enums.PermissionType;
import org.kowlintech.utils.constants.Global;

import java.awt.*;
import java.lang.annotation.Annotation;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config config = new Config();

        if(!event.getMessage().getContentRaw().startsWith(config.getPrefix()) || event.getAuthor().isBot()) {
            return;
        }
        for(EnumCommand command : EnumCommand.values()) {
            try {
                ArrayList<String> aliases = new ArrayList<>();
                for (String alias : command.getAliases()) {
                    aliases.add(alias);
                }
                if (event.getMessage().getContentRaw().startsWith((config.getPrefix() + command.name()).trim().toLowerCase()) || event.getMessage().getContentRaw().startsWith("<@" + event.getJDA().getSelfUser().getId() + ">" + command.name().trim().toLowerCase()) || event.getMessage().getContentRaw().startsWith("<!@" + event.getJDA().getSelfUser().getId() + ">" + command.name().trim().toLowerCase())) {
                    Annotation annotation = command.getExecutor().getClass().getDeclaredAnnotation(org.kowlintech.utils.command.objects.Command.class);
                    org.kowlintech.utils.command.objects.Command cmd = (org.kowlintech.utils.command.objects.Command) annotation;

                    if (cmd.category().equals(Category.OWNER)) {
                        if(!GordonRamsay.devIds.contains(event.getAuthor().getId())) {
                            event.getChannel().sendMessage("You can't use this command, you fucking idiot!").queue();
                        }
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
                }
            } catch (Exception ex) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Command Error");
                eb.setDescription("```" + ex.toString() + "```");
                eb.addField("Command Name", command.name().toLowerCase(), true);
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
