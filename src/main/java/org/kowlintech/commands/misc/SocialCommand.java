package org.kowlintech.commands.misc;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import org.kowlintech.utils.constants.Global;

public class SocialCommand extends Command {

    public SocialCommand(Category category) {
        this.name = "social";
        this.help = "Sends links to Gordon Ramsay's social media accounts";
        this.guildOnly = true;
        this.category = category;
    }

    @Override
    protected void execute(CommandEvent event) {
        // Basic embed setup
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("**Social Media**");
        eb.setColor(Global.COLOR);

        // Description
        eb.setDescription("<:youtube:685619934553833528> [YouTube](https://youtube.com/user/gordonramsay)\n<:twitter:685618168038817816> [Twitter](https://twitter.com/GordonRamsay)\n" +
                "<:facebook:685619177242886236> [Facebook](https://facebook.com/gordonramsay)\n<:instagram:685619575668342856> [Instagram](https://instagram.com/gordongram)");

        // Send the embed
        event.reply(eb.build());
    }
}
