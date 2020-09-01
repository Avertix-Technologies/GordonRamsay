package org.kowlintech.commands.fun;

import net.dv8tion.jda.api.EmbedBuilder;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.enums.Category;
import org.kowlintech.utils.constants.Global;
import org.kowlintech.utils.reddit.MemePost;
import org.kowlintech.utils.reddit.Reddit;

import java.util.Random;

@Command(name = "foodmeme", category = Category.FUN, description = "Shows you a food meme from r/foodmemes or r/gordonramsaymemes")
public class FoodMemeCommand implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) {
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
            String title;
            if(post.title().split("").length >= 256) {
                title = post.title().substring(0, 253) + "...";
            } else {
                title = post.title();
            }
            String cutTitle = post.title().substring(0, 253);
            eb.setTitle(title);
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