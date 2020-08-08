package org.kowlintech.commands.fun;

import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.Insult;
import org.kowlintech.utils.InsultManager;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.enums.Category;

import java.awt.*;
import java.sql.SQLException;
import java.sql.Time;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Command(name = "insult", category = Category.FUN, description = "Insults the specified user.", args = "<user>")
public class InsultCommand implements CommandExecutor {

    private InsultManager insultManager = new InsultManager();

    @Override
    public void execute(CommandEvent event) {
        Member member;

        // Check for arguments; if none were supplied, insult the user who executed the command.
        if(event.getArgs().isEmpty()){
            event.reply("You've got to tell me who to insult, you fucking donkey!");
            return;
        }
        // If arguments were supplied, parse them into a Member
        else{
            List<Member> found = FinderUtil.findMembers(event.getArgs().trim(), event.getGuild());
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

        // If the user is trying to execute the command on themselves, insult them then return
        if(member.getUser().getId().trim() == event.getMember().getUser().getId().trim()){
            event.reply("You fucking idiot, you can't insult YOURSELF!");
            return;
        } else if(member.getUser().getId().trim() == event.getJDA().getSelfUser().getId().trim()){
            event.reply("You fucking idiot, i'm not going to insult MYSELF!");
            return;
        }

        ArrayList<String> insults = new ArrayList<>(GordonRamsay.getInsultsArray());

        Random r = new Random();

        int num = r.nextInt(insults.size());

        String insult = insults.get(num);
        event.reply(insult.replace("{m}", "<@" + member.getId() + ">"));
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
