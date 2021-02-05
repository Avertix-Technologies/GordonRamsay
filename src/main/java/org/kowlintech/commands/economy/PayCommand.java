package org.kowlintech.commands.economy;

import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.EconomyManager;
import org.kowlintech.utils.EmbedHelper;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.EconomyUser;
import org.kowlintech.utils.command.objects.enums.Category;

import java.awt.*;
import java.util.List;

@Command(name = "pay", category = Category.ECONOMY, description = "Pays a user a specified amount of your balance.", args = "{user} {amount}")
public class PayCommand implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) {
        String[] args = event.getArgs().split(" ");

        if(args.length == 0) {
            event.reply("You gotta give someone to pay, you fucking idiot!");
            return;
        } else if(args.length < 2 && args.length >= 1) {
            event.reply("You gotta give an amount to pay, you fucking idiot!");
            return;
        }

        EconomyManager manager = GordonRamsay.getEconomyManager();

        EconomyUser userToPay = manager.getUser(event.getMember().getUser(), event.getGuild());

        Member member;

        // Check for arguments; if none were supplied, insult the user who executed the command.
        if(event.getArgs().isEmpty()) {
            member = event.getMember();
        }
        // If arguments were supplied, parse them into a Member
        else{
            List<Member> found = FinderUtil.findMembers(args[0].trim(), event.getGuild());
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
        
        Integer amount= 0;

        try {
            amount = Integer.parseInt(args[1].trim());

            if(amount == null) {
                event.reply("`" + args[1] + "` isn't an amount, you fucking idiot!");
                return;
            }

            if(amount > userToPay.getBalance()) {
                event.reply("You can't spend money that you don't have. You clearly shouldn't have a credit card, you fucking idiot!");
                return;
            }
        
            if(amount < 1) {
                event.reply("You have to give at least 1 token, you fucking idiot!");
                return;
            }
        } catch(NumberFormatException ex) {
            event.reply(String.format("`%s` isn't a number, you fucking idiot!", args[1].trim()));
            return;
        }
            
        EconomyUser userToBePaid = manager.getUser(member.getUser(), event.getGuild());

        userToPay.removeFromBalance(amount);
        userToBePaid.addToBalance(amount);

        event.reply(EmbedHelper.buildSuccessEmbed("User Paid", member.getAsMention() + " has been paid **" + amount + "** <:lambchops:686757563987263542>"));
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
