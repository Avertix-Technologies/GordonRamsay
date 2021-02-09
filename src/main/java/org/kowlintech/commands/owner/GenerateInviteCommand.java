package org.kowlintech.commands.fun;

import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.enums.Category;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Command(name = "generateinvite", category = Category.OWNER, description = "Generates an invite for a guild.", args = "<guildid> <channelid>")
public class LambSauceCommand implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) {
        event.getJDA().getGuildById(args[0].trim()).getTextChannelById(args[1].trim()).createInvite().queue(invite -> {
            event.reply(String.format("discord.gg/%s", invite.getCode()));
        });
    }
}
