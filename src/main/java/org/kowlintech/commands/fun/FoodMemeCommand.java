package org.kowlintech.commands.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import org.kowlintech.utils.constants.Global;
import org.kowlintech.utils.reddit.MemePost;
import org.kowlintech.utils.reddit.Reddit;

import java.util.Random;

public class FoodMemeCommand extends Command {

    public FoodMemeCommand(Category c) {
        this.name = "foodmeme";
        this.guildOnly = true;
        this.category = c;
        this.help = "Shows you a food meme from r/foodmemes or r/gordonramsaymemes";
    }

    @Override
    protected void execute(CommandEvent event) {
        String[] subs = new String[]{"foodmemes", "gordonramsaymemes"};
        Random r = new Random();
        String sub = subs[r.nextInt(subs.length)];

        event.getChannel().sendTyping().queue();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Global.COLOR);
        eb.setFooter("From r/" + sub + " | Powered by the Reddit API", "https://www.redditstatic.com/desktop2x/img/favicon/android-icon-192x192.png");
        MemePost post;
        post = Reddit.getRandomMedia(sub);
        if(!post.isNsfw()) {
            eb.setTitle(post.title());
            eb.setImage(post.mediaUrl());
        } else if(post.isNsfw()) {
            MemePost post1;
            post1 = Reddit.getRandomMedia(sub);
            eb.setTitle(post1.title());
            eb.setImage(post1.mediaUrl());
        }
        event.reply(eb.build());
    }
}