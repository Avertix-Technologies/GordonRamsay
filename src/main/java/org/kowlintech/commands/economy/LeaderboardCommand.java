package org.kowlintech.commands.economy;

import net.dv8tion.jda.api.EmbedBuilder;
import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.enums.Category;
import org.kowlintech.utils.constants.Global;

import java.sql.ResultSet;
import java.sql.SQLException;

@Command(name = "leaderboard", category = Category.ECONOMY, description = "Shows the top 5 users in a guild or globally.", args = "[-g]", aliases = {"lb"})
public class LeaderboardCommand implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) throws SQLException {
        if(!event.getArgs().isEmpty() && event.getArgs().startsWith("-g") || event.getArgs().startsWith("-G")) {
            ResultSet rs = GordonRamsay.getDatabaseConnection().prepareStatement("SELECT * FROM economy ORDER BY coins DESC LIMIT 5").executeQuery();

            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Global Leaderboard");
            eb.setColor(Global.COLOR);

            while (rs.next()) {
                try {
                    eb.addField("#" + (eb.getFields().size() + 1) + " - " + event.getJDA().getUserById(rs.getLong("userid")).getName() + " (" + event.getGuild().getName() + ")",
                            "Porkchops: " + rs.getLong("coins") + " <:lambchops:686757563987263542>",
                            false
                    );
                } catch (Exception ex) {
                    eb.addField("#" + (eb.getFields().size() + 1) + " - Unknown User (" + event.getGuild().getName() + ")",
                            "Porkchops: " + rs.getLong("coins") + " <:lambchops:686757563987263542>",
                            false
                    );
                }
            }

            if(eb.getFields().size() == 0) {
                eb.setDescription("No one is using the economy.");
            }

            rs.close();

            event.reply(eb.build());

            return;
        }
        ResultSet rs = GordonRamsay.getDatabaseConnection().prepareStatement("SELECT * FROM economy WHERE guildid=" + event.getGuild().getIdLong() + " ORDER BY coins DESC LIMIT 5").executeQuery();

        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle(event.getGuild().getName() + "'s Leaderboard");
        eb.setColor(Global.COLOR);

        while (rs.next()) {
            try {
                eb.addField("#" + (eb.getFields().size() + 1) + " - " + event.getJDA().getUserById(rs.getLong("userid")).getName() + " (" + event.getGuild().getName() + ")",
                        "Porkchops: " + rs.getLong("coins") + " <:lambchops:686757563987263542>",
                        false
                );
            } catch (Exception ex) {
                eb.addField("#" + (eb.getFields().size() + 1) + " - Unknown User (" + event.getGuild().getName() + ")",
                        "Porkchops: " + rs.getLong("coins") + " <:lambchops:686757563987263542>",
                        false
                );
            }
        }

        if(eb.getFields().size() == 0) {
            eb.setDescription("No one is using the economy in this server.");
        }

        rs.close();

        event.reply(eb.build());
    }
}
