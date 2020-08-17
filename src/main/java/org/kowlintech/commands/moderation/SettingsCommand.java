package org.kowlintech.commands.moderation;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.EmbedHelper;
import org.kowlintech.utils.SettingsManager;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.enums.Category;
import org.kowlintech.utils.constants.Global;

import java.sql.SQLException;

@Command(name = "settings", description = "Manage settings for the guild.\n\nEx: g.settings tempunit <metric/imperial>", category = Category.MODERATION, args = "<option> <option2>")
public class SettingsCommand implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) throws SQLException {
        if(event.getMember().hasPermission(Permission.ADMINISTRATOR) || GordonRamsay.devIds.contains(event.getMember().getId())) {
            String[] args = event.getArgs().split(" ");
            if(args[0].trim().equalsIgnoreCase("tempunit")) {
                if(args.length >= 2) {
                    if(String.valueOf(args[1]) != null) {
                        if(args[1].toLowerCase().equals("imperial") || args[1].toLowerCase().equals("metric")) {
                            event.reply(EmbedHelper.buildSuccessEmbed("Guild temperature unit has been set to `" + args[1].toLowerCase() + "` from `" + SettingsManager.getGuildTemperatureUnit(event.getGuild().getIdLong()) + "`"));
                            SettingsManager.setGuildTemperatureUnit(event.getGuild().getIdLong(), args[1].toLowerCase().trim());
                            return;
                        } else {
                            event.reply(":x: Please specify a valid temperature unit! (Imperial/Metric)");
                            return;
                        }
                    } else {
                        event.reply(":x: Temperature Unit must be a string!");
                        return;
                    }
                } else {
                    event.reply(":x: Please specify a temperature unit! (Imperial/Metric)");
                    return;
                }
            } else {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Options for Settings Command");
                eb.setColor(Global.COLOR);
                eb.setDescription("Uses for the g.settings command");
                eb.addField("g.settings tempunit <Imperial/Metric>", "Sets the temperature unit for the g.weather command.", true);
                event.reply(eb.build());
                return;
            }
        } else {
            event.reply(":x: Sorry! This command can only be used be members with the `Administrator` permission.");
            return;
        }
    }
}
