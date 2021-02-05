package org.kowlintech.commands.economy;

import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.EconomyManager;
import org.kowlintech.utils.EmbedHelper;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.EconomyUser;
import org.kowlintech.utils.command.objects.enums.Category;

import java.sql.SQLException;
import java.util.Random;

@Command(name = "gamble", category = Category.ECONOMY, description = "Allows you to gamble up to 50,000.", args = "<amount>")
public class GambleCommand implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) throws SQLException {
        if(event.getArgs().isEmpty()) {
            event.reply("You gotta give something to gamble, you fucking idiot!");
            return;
        }

        EconomyManager manager = GordonRamsay.getEconomyManager();
        EconomyUser user = manager.getUser(event.getMember().getUser(), event.getGuild());

        String[] args = event.getArgs().split(" ");

        if(Integer.valueOf(args[0].trim()) == null) {
            event.reply("`" + args[1] + "` isn't an amount, you fucking idiot!");
            return;
        }

        int amount = Integer.valueOf(args[0].trim());

        if(amount > 50000) {
            event.reply("You can't gamble more than 50,000 <:lambchops:686757563987263542>, you fucking idiot!");
            return;
        }

        if(user.getBalance() < amount) {
            event.reply("You can't gamble money that you don't have. You clearly shouldn't have a credit card, you fucking idiot!");
            return;
        }

        Random random = new Random();

        boolean winlose = random.nextBoolean();

        if(winlose) {
            user.addToBalance(amount);
            event.reply(EmbedHelper.buildSuccessEmbed("Congrats, you fucking donkey!", "You won **" + amount + "** <:lambchops:686757563987263542>"));
            return;
        } else {
            user.removeFromBalance(amount);
            event.reply(EmbedHelper.buildErrorEmbed("Sucks to suck", "You lost **" + amount + "** <:lambchops:686757563987263542>"));
            return;
        }
    }
}
