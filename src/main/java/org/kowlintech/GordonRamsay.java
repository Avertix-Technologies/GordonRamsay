package org.kowlintech;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.exceptions.RateLimitedException;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import org.kowlintech.commands.fun.*;
import org.kowlintech.commands.misc.*;
import org.kowlintech.commands.moderation.BanCommand;
import org.kowlintech.commands.moderation.KickCommand;
import org.kowlintech.commands.moderation.PurgeCommand;
import org.kowlintech.commands.owner.DeployCommand;
import org.kowlintech.commands.owner.EvalCommand;
import org.kowlintech.commands.owner.ManageInsultsCommand;
import org.kowlintech.listeners.JoinLeaveListener;
import org.kowlintech.utils.Categories;
import org.kowlintech.utils.Insult;
import org.kowlintech.utils.InsultManager;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class GordonRamsay {

    private static JDA jda;
    private static Connection connection;
    public static Socket socketclient;
    private static ServerSocket server;

    public static void main(String[] args) throws IOException, LoginException, IllegalArgumentException, SQLException, ClassNotFoundException {
        Config config = new Config();

        EventWaiter waiter = new EventWaiter();

        CommandClientBuilder client = new CommandClientBuilder();

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
                new FeedbackCommand(Categories.MISCELLANEOUS),

                new BanCommand(Categories.MODERATION),
                new KickCommand(Categories.MODERATION),
                new PurgeCommand(Categories.MODERATION),

                new DeployCommand(Categories.OWNER),
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
    }

    private static void openDatabaseConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://127.0.0.1/gramsay?user=postgres&password=kowlin";
        Connection conn = DriverManager.getConnection(url);
        connection = conn;
        System.out.println("[DATABASE] Connected to PostgreSQL Database!");
    }

    private static void checkTables() throws SQLException {
        Statement st = connection.createStatement();
        st.execute("CREATE TABLE IF NOT EXISTS insults (id SERIAL PRIMARY KEY, insult TEXT NOT NULL UNIQUE);");
    }

    public static Connection getDatabaseConnection() {
        return connection;
    }

    public static void openSocketClient() {
        try {
            //Creating Clientside Socket
            socketclient = new Socket("127.0.0.1", 1027);
        } catch (UnknownHostException e) {
            System.out.println("[Error] An error occurred while connecting to the deployment daemon.");
        } catch (IOException e) {
            System.out.println("[Error] Failed to create Writers.");
        }
    }

    private static InsultManager getInsultManager() {
        return new InsultManager();
    }
}
