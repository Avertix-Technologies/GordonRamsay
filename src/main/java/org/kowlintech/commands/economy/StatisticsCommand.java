package org.kowlintech.commands.economy;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.EmbedHelper;
import org.kowlintech.utils.PhraseManager;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.enums.Category;
import org.kowlintech.utils.constants.Global;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

@Command(name = "statistics", category = Category.ECONOMY, description = "Shows the statistics of the bot economy.", aliases = {"stats"})
public class StatisticsCommand implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) {
        event.reply(EmbedHelper.buildWaitingEmbed("<:idle:683864575417778222> **Getting Global Economy Statistics...**"), message -> {
            try {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Global Economy Statistics");

                ResultSet rs = GordonRamsay.getDatabaseConnection().prepareStatement("SELECT * FROM economy").executeQuery();

                long total_bal = 0;
                long total_users = 0;

                while (rs.next()) {
                    total_bal = (total_bal + rs.getLong("coins"));
                    total_users++;
                }

                eb.addField("Registered Users", String.valueOf(total_users), true);
                eb.addField("Total Economy Balance", String.valueOf(total_bal), true);

                rs.close();

                ResultSet rs1 = GordonRamsay.getDatabaseConnection().prepareStatement("SELECT * FROM economy ORDER BY coins DESC LIMIT 1").executeQuery();

                while (rs1.next()) {
                    User user = event.getJDA().getUserById(rs1.getLong("userid"));
                    eb.addField("Leading User", user.getName() + "#" + user.getDiscriminator(), true);
                    eb.addField("Leading User Amount", String.valueOf(rs1.getLong("coins")), true);
                }

                rs1.close();

                PhraseManager manager = GordonRamsay.getPhraseManager();

                eb.addField("Total Phrase Count", String.valueOf(manager.getPhraseHashMap().size()), true);
                eb.setTimestamp(Instant.now());
                eb.setColor(Global.COLOR);

                message.editMessage(eb.build()).queue();
            } catch (SQLException ex) {
                ex.printStackTrace();
                message.editMessage(EmbedHelper.buildErrorEmbed("Error", "There was an error while gathering info about the economy. Please try again later.")).queue();
            }
        });
    }
}
