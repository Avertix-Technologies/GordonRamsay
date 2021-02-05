package org.kowlintech.commands.economy;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import org.joda.time.Duration;
import org.joda.time.format.PeriodFormat;
import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.EconomyManager;
import org.kowlintech.utils.PhraseManager;
import org.kowlintech.utils.command.objects.*;
import org.kowlintech.utils.command.objects.enums.Category;
import org.kowlintech.utils.command.objects.enums.PhraseType;
import org.kowlintech.utils.constants.Global;

import java.awt.*;
import java.time.Instant;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

@Command(name = "shovel", category = Category.ECONOMY, description = "Shovel for lambchops.", aliases = {"shv"})
public class ShovelCommand implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) {
        if(GordonRamsay.shovelCooldownHash.get(event.getMember().getIdLong()) != null) {
            String[] split = GordonRamsay.shovelCooldownHash.get(event.getMember().getIdLong()).split("-");
            long remaining_long = Long.valueOf(split[1]) - (System.currentTimeMillis() - Long.valueOf(split[0]));

            if(String.valueOf(remaining_long).startsWith("-")) {
                GordonRamsay.shovelCooldownHash.remove(event.getMember().getIdLong());
            } else {
                event.reply(event.getMember().getAsMention() + ", little too quick there buddy! Please wait another `" + PeriodFormat.getDefault().print(new Duration(remaining_long).toPeriod()) + "`.");
                return;
            }
        }
        EconomyManager economyManager = GordonRamsay.getEconomyManager();
        PhraseManager phraseManager = GordonRamsay.getPhraseManager();

        if(phraseManager.getPhraseHashMap().isEmpty()) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Error");
            eb.setDescription("It appears that there have been no phrases added for this command as of yet.\n\nPlease try again later or join our discord (g.support) for updates.");
            eb.setColor(Color.RED);
            eb.setTimestamp(Instant.now());
            event.reply(eb.build());
            return;
        }

        EconomyPhrase phrase = phraseManager.getRandomPhrase(PhraseType.SHOVEL);

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Shovel");
        if(phrase.getFate()) {
            eb.setColor(Color.GREEN);
        } else {
            eb.setColor(Color.RED);
        }

        Random random = new Random();
        int low = 10;
        int high = 400;
        int result = random.nextInt(high-low) + low;

        eb.setDescription(phrase.getText().replace("{m}", (phrase.getFate() ? "" : "-") + result + "<:lambchops:686757563987263542>"));
        eb.setTimestamp(Instant.now());

        try {
            event.reply(eb.build());
        } catch (InsufficientPermissionException ex) {
            event.getRawEvent().getMessage().addReaction("❌");
            return;
        }

        EconomyUser user = economyManager.getUser(event.getMember().getUser(), event.getGuild());

        if(phrase.getFate()) {
            user.addToBalance(result);
        } else {
            user.removeFromBalance(result);
        }

        GordonRamsay.shovelCooldownHash.put(event.getMember().getIdLong(), System.currentTimeMillis() + "-3000");

        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                GordonRamsay.shovelCooldownHash.remove(event.getMember().getIdLong());
            }
        };

        timer.schedule(task, 3000L);
    }
}
