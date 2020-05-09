package org.kowlintech.commands.misc;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import org.kowlintech.utils.constants.Global;

public class VoteCommand extends Command {

    public VoteCommand(Category category) {
        this.name = "vote";
        this.help = "Upvote the bot on the bot lists";
        this.guildOnly = true;
        this.category = category;
    }

    @Override
    protected void execute(CommandEvent event) {
        // Basic embed setup
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("**Vote**");
        eb.setColor(Global.COLOR);

        // Description
        eb.setDescription("[Top.gg](https://top.gg/bot/528984558629027841/vote)\n[Discord Boats](https://discord.boats/bot/528984558629027841/vote)\n" +
                "[Glenn Bot List](https://glennbotlist.xyz/bot/528984558629027841)");

        // Send the embed
        event.reply(eb.build());
    }
}
