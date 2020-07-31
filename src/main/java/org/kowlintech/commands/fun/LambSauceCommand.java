package org.kowlintech.commands.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

import java.awt.*;
import java.sql.Time;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LambSauceCommand extends Command {

    public LambSauceCommand(Category category) {
        this.name = "lambsauce";
        this.guildOnly = true;
        this.help = "Asks the specified user about the whereabouts of the lamb sauce.";
        this.arguments = "<user>";
        this.category = category;
    }

    @Override
    protected void execute(CommandEvent event) {
        Member member;

        // Check for arguments; if none were supplied, insult the user who executed the command.
        if(event.getArgs().isEmpty()){
            event.reply("You've got to tell me who to ask, you fucking donkey!");
            return;
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

        // If the user is trying to execute the command on themselves, insult them then return
        if(member == event.getGuild().getMember(event.getAuthor())){
            event.reply("You fucking idiot, you can't ask YOURSELF about the lamb sauce!");
            return;
        }

        // Ask the question
        event.getChannel().sendMessage(member.getAsMention() + " can I ask you a question?").queue();
        event.getChannel().sendMessage(("WHERE'S THE LAMB SAUUUCE!?!?")).queueAfter(5, TimeUnit.SECONDS);
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
