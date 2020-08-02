package org.kowlintech.commands.misc;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDAInfo;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.enums.Category;
import org.kowlintech.utils.constants.Global;

@Command(name = "info", category = Category.MISCELLANEOUS, description = "Gets info on the bot")
public class InfoCommand implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) {
        // Basic embed setup
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("**Information**");
        eb.setColor(Global.COLOR);
        eb.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());

        // Fields
        eb.addField("Developers", "KowlinMC#2385\nStarman#5874", false);
        eb.addField("Library", "[JDA v" + JDAInfo.VERSION + "](https://github.com/DV8FromTheWorld/JDA)", true);
        eb.addField("Server Count", String.valueOf(event.getJDA().getGuilds().size()), true);
        eb.addField("Support Server", "[Invite](https://discord.gg/SW7bmXm)", true);

        // Send the embed
        event.reply(eb.build());
    }
}
