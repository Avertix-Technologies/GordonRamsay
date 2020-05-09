package org.kowlintech.commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import org.kowlintech.utils.constants.Global;

import java.awt.*;
import java.util.List;

public class KickCommand extends Command {

    public KickCommand(Category category) {
        this.name = "kick";
        this.guildOnly = true;
        this.help = "Kicks the specified user.";
        this.arguments = "<user>";
        this.category = category;
        this.botPermissions = new Permission[]{Permission.KICK_MEMBERS};

        this.userPermissions = new Permission[]{Permission.KICK_MEMBERS};
    }

    @Override
    protected void execute(CommandEvent event) {
        Member member;
        if(event.getArgs().isEmpty())
        {
            event.reply("You gotta give me someone to kick, you fucking idiot!");
            return;
        }
        else
        {
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
        if(member == event.getGuild().getMember(event.getAuthor())) {
            event.reply("You fucking idiot " + event.getAuthor().getAsMention() + " you can't ban yourself!");
            return;
        }
        if(member.hasPermission(Permission.ADMINISTRATOR)) {
            event.reply("I won't ban an admin you fucking idiot.");
            return;
        }
        try {
            member.kick().queue();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setDescription("✅ " + member.getAsMention() + " (" + member.getId() + ")" + " has been kicked.");
            eb.setColor(Global.COLOR);
            event.reply(eb.build());
        } catch (Exception ex) {
            event.reply("Ok Discord is being a bitch and not letting me kick that user you might want to manually kick that user if you can.");
            return;
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