package org.kowlintech.commands.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import org.kowlintech.utils.Insult;
import org.kowlintech.utils.InsultManager;

import java.awt.*;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class InsultCommand extends Command {

    private InsultManager insultManager;

    public InsultCommand(Category category, InsultManager insultManager) {
        this.name = "insult";
        this.guildOnly = true;
        this.help = "Insults the specified user.";
        this.arguments = "<user>";
        this.category = category;
        this.insultManager = insultManager;
    }

    @Override
    protected void execute(CommandEvent event) {
        Member member;

        // Check for arguments; if none were supplied, insult the user who executed the command.
        if(event.getArgs().isEmpty()){
            event.reply("You've got to tell me who to insult, you fucking donkey!");
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
                eb.setDescription(listOfMembers(found, event.getArgs()));
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
            event.reply("You fucking idiot, you can't insult YOURSELF!");
            return;
        }

        try {
            ArrayList<Insult> insults = insultManager.getInsults();

            Random r = new Random();

            int insult=r.nextInt(insults.size());
            Insult insult1 = insults.get(insult);
            event.reply(insult1.getText().replace("{m}", "<@" + member.getId() + ">"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String listOfMembers(List<Member> list, String query)
    {
        String out = String.format("⚠ **Multiple members found matching \"%s\":**", query);
        for(int i = 0; i < 6 && i < list.size(); i++)
            out += "\n - " + list.get(i).getUser().getName() + " (ID:" + list.get(i).getId() + ")";
        if(list.size() > 6)
            out += "\n**And " + (list.size() - 6) + " more...**";
        return out;
    }
}
