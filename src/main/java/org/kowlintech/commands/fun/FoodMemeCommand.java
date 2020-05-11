package org.kowlintech.commands.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import org.kowlintech.utils.constants.Global;
import org.kowlintech.utils.reddit.MemePost;
import org.kowlintech.utils.reddit.Reddit;

public class FoodMemeCommand extends Command {

    public FoodMemeCommand(Category c) {
        this.name = "foodmeme";
        this.guildOnly = true;
        this.category = c;
        this.help = "Shows you a food meme from r/foodmemes";
    }

    @Override
    protected void execute(CommandEvent event) {
        event.getChannel().sendTyping().queue();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Global.COLOR);
        eb.setFooter("From r/foodmemes | Powered by the Reddit API", "https://www.redditstatic.com/desktop2x/img/favicon/android-icon-192x192.png");
        MemePost post;
        post = Reddit.getRandomMedia("foodmemes");
        if(!post.isNsfw()) {
            eb.setTitle(post.title());
            eb.setImage(post.mediaUrl());
        } else if(post.isNsfw()) {
            MemePost post1;
            post1 = Reddit.getRandomMedia("foodmemes");
            eb.setTitle(post1.title());
            eb.setImage(post1.mediaUrl());
        }
        event.reply(eb.build());
    }
}