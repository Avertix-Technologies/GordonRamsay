package org.kowlintech.commands.fun;

import com.google.gson.internal.$Gson$Preconditions;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import org.kowlintech.utils.constants.Global;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class GayCommand extends Command {

    public GayCommand(Category category) {
        this.name = "gay";
        this.help = "Answers the ultimate question; how gay is someone?";
        this.category = category;
        this.guildOnly = true;
        this.arguments = "<user>";
    }

    @Override
    protected void execute(CommandEvent event) {
        Member member;

        // Check for arguments; if none were supplied, insult the user who executed the command.
        if(event.getArgs().isEmpty()){
            event.reply("You've got to tell me who to check, you fucking donkey!");
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


        // Generate a random number between 0 and 100
        Random r = new Random();
        int number = r.nextInt(100 - 0 + 1);

        // Embed setup
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Global.COLOR);
        eb.setTitle("\uD83C\uDFF3️\u200D\uD83C\uDF08 **Gay Rating** \uD83C\uDFF3️\u200D\uD83C\uDF08");
        eb.setDescription(member.getAsMention() + " is **" + number + "%** gay");

        // Send the embed
        event.reply(eb.build());
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
