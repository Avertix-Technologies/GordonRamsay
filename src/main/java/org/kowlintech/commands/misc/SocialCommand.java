package org.kowlintech.commands.misc;

import net.dv8tion.jda.api.EmbedBuilder;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.enums.Category;
import org.kowlintech.utils.constants.Global;

@Command(name = "social", category = Category.MISCELLANEOUS, description = "Sends links for Gordon Ramsay's social media accounts")
public class SocialCommand implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("**Social Media**");
        eb.setColor(Global.COLOR);

        eb.setDescription("<:youtube:685619934553833528> [YouTube](https://youtube.com/user/gordonramsay)\n<:twitter:685618168038817816> [Twitter](https://twitter.com/GordonRamsay)\n" +
                "<:facebook:685619177242886236> [Facebook](https://facebook.com/gordonramsay)\n<:instagram:685619575668342856> [Instagram](https://instagram.com/gordongram)");

        event.reply(eb.build());
    }
}
