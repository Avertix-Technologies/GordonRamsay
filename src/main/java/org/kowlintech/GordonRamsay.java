package org.kowlintech;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.apache.commons.collections4.map.LRUMap;
import org.kowlintech.commands.fun.*;
import org.kowlintech.commands.misc.*;
import org.kowlintech.commands.moderation.BanCommand;
import org.kowlintech.commands.moderation.KickCommand;
import org.kowlintech.commands.moderation.PurgeCommand;
import org.kowlintech.commands.owner.EvalCommand;
import org.kowlintech.commands.owner.ManageInsultsCommand;
import org.kowlintech.listeners.JoinLeaveListener;
import org.kowlintech.utils.Categories;
import org.kowlintech.utils.InsultManager;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class GordonRamsay {

    private static JDA jda;
    private static Connection connection;
    private static LRUMap<Integer, String> insults;

    public static void main(String[] args) throws LoginException, IllegalArgumentException, SQLException, ClassNotFoundException {
        Config config = new Config();

        EventWaiter waiter = new EventWaiter();

        CommandClientBuilder client = new CommandClientBuilder();
        insults = new LRUMap<>();

        client.setOwnerId("525050292400685077");
        client.setCoOwnerIds("363850072309497876");
        client.setEmojis("✅", "⚠", "❌");
        client.useHelpBuilder(false);
        client.setPrefix(config.getPrefix());
        client.setAlternativePrefix("<@!528984558629027841> ");
        client.setActivity(Activity.watching("for " + config.getPrefix() + "help"));

        client.addCommands(
                new LambSauceCommand(Categories.FUN),
                new FoodMemeCommand(Categories.FUN),
                new RPSCommand(Categories.FUN),
                new GayCommand(Categories.FUN),
                new InsultCommand(Categories.FUN, getInsultManager()),

                new HelpCommand(Categories.MISCELLANEOUS),
                new SupportCommand(Categories.MISCELLANEOUS),
                new InviteCommand(Categories.MISCELLANEOUS),
                new InfoCommand(Categories.MISCELLANEOUS),
                new SocialCommand(Categories.MISCELLANEOUS),
                new VoteCommand(Categories.MISCELLANEOUS),
                new PingCommand(Categories.MISCELLANEOUS),
                new UptimeCommand(Categories.MISCELLANEOUS),
                new FeedbackCommand(Categories.MISCELLANEOUS),

                new BanCommand(Categories.MODERATION),
                new KickCommand(Categories.MODERATION),
                new PurgeCommand(Categories.MODERATION),

                new EvalCommand(Categories.OWNER),
                new ManageInsultsCommand(Categories.OWNER, getInsultManager())
        );

        jda = new JDABuilder(AccountType.BOT)
                .setToken(config.getToken())

                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setActivity(Activity.playing("Starting Up..."))

                .addEventListeners(
                        waiter,
                        new JoinLeaveListener(),
                        client.build()
                )
                .build();

        openDatabaseConnection();
        checkTables();
        prepareInsults();
    }

    private static void openDatabaseConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://192.168.0.96/gramsay?user=postgres&password=kowlin";
        Connection conn = DriverManager.getConnection(url);
        connection = conn;
        System.out.println("[DATABASE] Connected to PostgreSQL Database!");
    }

    private static void checkTables() throws SQLException {
        Statement st = connection.createStatement();
        st.execute("CREATE TABLE IF NOT EXISTS insults (id SERIAL PRIMARY KEY, insult TEXT NOT NULL UNIQUE);");
    }

    private static void prepareInsults() throws SQLException {
        PreparedStatement st = getDatabaseConnection().prepareStatement("SELECT * FROM insults");
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            insults.put(rs.getInt("id"), rs.getString("insult"));
        }
    }

    public static LRUMap<Integer, String> getInsults() {
        return insults;
    }

    public static ArrayList<String> getInsultsArray() {
        ArrayList<String> array = new ArrayList<>();
        for(String str : getInsults().values()) {
            array.add(str);
        }

        return array;
    }

    public static Connection getDatabaseConnection() {
        return connection;
    }

    private static InsultManager getInsultManager() {
        return new InsultManager();
    }
}
