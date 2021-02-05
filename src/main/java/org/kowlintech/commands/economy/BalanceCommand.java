package org.kowlintech.commands.economy;

import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.EconomyUser;
import org.kowlintech.utils.command.objects.enums.Category;
import org.kowlintech.utils.constants.Global;

import java.awt.*;
import java.util.List;

@Command(name = "balance", category = Category.ECONOMY, description = "Gets a user's balance", args = "[@user]", aliases = {"bal"})
public class BalanceCommand implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) {
        Member member;

        if(event.getArgs().isEmpty()) {
            member = event.getMember();
        } else {
            String[] args = event.getArgs().split(" ");
            List<Member> found = FinderUtil.findMembers(args[0], event.getGuild());
            if(found.isEmpty()) {
                event.reply("I don't know who the fuck that is but they aren't in this server.");
                return;
            } else if(found.size() > 1) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Multiple Members Matching Query");
                eb.setDescription(listOfMembers(found));
                eb.setColor(Color.RED);
                event.reply(eb.build());
                return;
            } else {
                member = found.get(0);
            }
        }

        EconomyUser ecouser = GordonRamsay.getEconomyManager().getUser(member.getUser(), event.getGuild());

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle((member.getId() == event.getMember().getId() ? "Your Balance" : member.getUser().getName() + "#" + member.getUser().getDiscriminator() + "'s Balance"));
        eb.setDescription(ecouser.getBalance() + " <:lambchops:686757563987263542>");
        eb.setColor(Global.COLOR);
        event.reply(eb.build());
    }

    private static String listOfMembers(List<Member> list)
    {
        String out = "";
        for(int i = 0; i < 6 && i < list.size(); i++)
            out += "\n - " + list.get(i).getUser().getName() + " (ID: " + list.get(i).getId() + ")";
        if(list.size() > 6)
            out += "\n**And " + (list.size() - 6) + " more...**";
        return out;
    }
}
