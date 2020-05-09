package org.kowlintech.commands.misc;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import org.kowlintech.utils.constants.Global;

public class InfoCommand extends Command {

    public InfoCommand(Category category) {
        this.name = "info";
        this.help = "Gets info on the bot";
        this.guildOnly = true;
        this.category = category;
    }

    @Override
    protected void execute(CommandEvent event) {
        // Basic embed setup
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("**Information**");
        eb.setColor(Global.COLOR);
        eb.setThumbnail(event.getSelfUser().getAvatarUrl());

        // Fields
        eb.addField("Developers", "KowlinMC#2385\nStarman#5874", false);
        eb.addField("Library", "[JDA](https://github.com/DV8FromTheWorld/JDA)", true);
        eb.addField("Server Count", String.valueOf(event.getClient().getTotalGuilds()), true);
        eb.addField("Support Server", "[Invite](https://discord.gg/SW7bmXm)", true);

        // Send the embed
        event.reply(eb.build());
    }
}
