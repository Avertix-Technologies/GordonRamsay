package org.kowlintech.commands.misc;

import net.dv8tion.jda.api.EmbedBuilder;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.enums.Category;
import org.kowlintech.utils.constants.Global;

@Command(name = "vote", category = Category.MISCELLANEOUS, description = "Upvote the bot on the bot lists")
public class VoteCommand implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("**Vote**");
        eb.setColor(Global.COLOR);

        eb.setDescription("[Top.gg](https://top.gg/bot/528984558629027841/vote)\n[Discord Boats](https://discord.boats/bot/528984558629027841/vote)\n" +
                "[Glenn Bot List](https://glennbotlist.xyz/bot/528984558629027841)");

        event.reply(eb.build());
    }
}
