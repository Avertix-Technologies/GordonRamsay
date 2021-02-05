package org.kowlintech.commands.economy;

import net.dv8tion.jda.api.EmbedBuilder;
import org.joda.time.Duration;
import org.joda.time.format.PeriodFormat;
import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.EconomyManager;
import org.kowlintech.utils.PhraseManager;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.EconomyPhrase;
import org.kowlintech.utils.command.objects.enums.Category;
import org.kowlintech.utils.command.objects.enums.PhraseType;

import java.awt.*;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

@Command(name = "ask", category = Category.ECONOMY, description = "Ask a public figure for some money.")
public class AskCommand implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) throws SQLException {
        if(GordonRamsay.askCooldownHash.get(event.getMember().getIdLong()) != null) {
            String[] split = GordonRamsay.askCooldownHash.get(event.getMember().getIdLong()).split("-");
            long remaining_long = Long.valueOf(split[1]) - (System.currentTimeMillis() - Long.valueOf(split[0]));

            if(String.valueOf(remaining_long).startsWith("-")) {
                GordonRamsay.askCooldownHash.remove(event.getMember().getIdLong());
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

        EconomyPhrase phrase = phraseManager.getRandomPhrase(PhraseType.ASK);

        Random random = new Random();
        boolean give = random.nextBoolean();

        int low = 1000;
        int high = 4000;
        int result = random.nextInt(high-low) + low;

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Ask");
        eb.setTimestamp(Instant.now());
        if(give) {
            economyManager.getUser(event.getMember().getUser(), event.getGuild()).addToBalance(result);
            eb.setColor(Color.GREEN);
            eb.setDescription(phrase.getText() + ": Here you go you, lazyass. " + result + "<:lambchops:686757563987263542>");
            event.reply(eb.build());
        } else {
            eb.setColor(Color.RED);
            eb.setDescription(phrase.getText() + ": Get a job, lazyass.");
            event.reply(eb.build());
        }

        GordonRamsay.askCooldownHash.put(event.getMember().getIdLong(), System.currentTimeMillis() + "-480000");

        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                GordonRamsay.askCooldownHash.remove(event.getMember().getIdLong());
            }
        };

        timer.schedule(task, 480000L);
    }
}
