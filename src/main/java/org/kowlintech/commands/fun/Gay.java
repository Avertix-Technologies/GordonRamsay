package org.kowlintech.commands.fun;

import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.enums.Category;
import org.kowlintech.utils.constants.Global;

import java.awt.*;
import java.util.List;
import java.util.Random;

@Command(name = "gayt", category = Category.FUN, description = "Answers the ultimate question; how gay is someone?", args = "<user>")
public class Gay implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) {
        Member member;

        // Check for arguments; if none were supplied, insult the user who executed the command.
        if(event.getArgs().isEmpty()) {
            member = event.getMember();
        }
        // If arguments were supplied, parse them into a Member
        else{
            List<Member> found = FinderUtil.findMembers(event.getArgs(), event.getGuild());
            if(found.isEmpty())
            {
                event.reply("I don't know who the fuck that is but they aren't in this server.");
                return;
            }
            else if(found.size()>1)
            {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Multiple Members Matching Query");
                eb.setDescription(listOfMembers(found));
                eb.setColor(Color.RED);
                event.reply(eb.build());
                return;
            }
            else
            {
                member = found.get(0);
            }
        }


        // Generate a random number between 0 and 100
        Random r = new Random();
        int number = r.nextInt(100 - 0 + 1);

        // Embed setup
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Global.COLOR);
        eb.setTitle("**Gay Rating**");
        eb.setDescription(member.getAsMention() + " is **" + number + "%** gay");

        // Send the embed
        event.reply(eb.build());
    }

    private static String listOfMembers(List<Member> list)
    {
        String out = "";
        for(int i = 0; i < 6 && i < list.size(); i++)
            out += "\n - " + list.get(i).getUser().getName() + " (ID:" + list.get(i).getId() + ")";
        if(list.size() > 6)
            out += "\n**And " + (list.size() - 6) + " more...**";
        return out;
    }
}
