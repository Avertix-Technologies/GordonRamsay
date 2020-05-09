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
import org.kowlintech.commands.misc.HelpCommand;
import org.kowlintech.commands.fun.LambSauceCommand;
import org.kowlintech.commands.misc.InviteCommand;
import org.kowlintech.commands.misc.SupportCommand;
import org.kowlintech.commands.moderation.BanCommand;
import org.kowlintech.commands.moderation.KickCommand;
import org.kowlintech.commands.moderation.PurgeCommand;
import org.kowlintech.commands.owner.DeployCommand;
import org.kowlintech.utils.Categories;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GordonRamsay {

    private static JDA jda;
    private static Connection connection;
    public static Socket socketclient;
    private static ServerSocket server;

    public static void main(String[] args) throws IOException, LoginException, IllegalArgumentException, RateLimitedException, SQLException, ClassNotFoundException {
        Config config = new Config();

        EventWaiter waiter = new EventWaiter();

        CommandClientBuilder client = new CommandClientBuilder();

        client.setOwnerId("525050292400685077");
        client.setCoOwnerIds("363850072309497876");
        client.setEmojis("✅", "⚠", "❌");
        client.useHelpBuilder(false);
        client.setPrefix(config.getPrefix());
        client.setActivity(Activity.watching("for " + config.getPrefix() + "help"));

        client.addCommands(
                new LambSauceCommand(Categories.FUN),

                new HelpCommand(Categories.MISCELLANEOUS),
                new SupportCommand(Categories.MISCELLANEOUS),
                new InviteCommand(Categories.MISCELLANEOUS),

                new BanCommand(Categories.MODERATION),
                new KickCommand(Categories.MODERATION),
                new PurgeCommand(Categories.MODERATION),

                new DeployCommand(Categories.OWNER)
        );

        jda = new JDABuilder(AccountType.BOT)
                .setToken(config.getToken())

                .setStatus(OnlineStatus.IDLE)
                .setActivity(Activity.playing("Please Wait..."))

                .addEventListeners(
                        waiter,
                        client.build()
                )
                .build();

        openDatabaseConnection();
    }

    private static void openDatabaseConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://192.168.0.96/ramsay?user=postgres&password=kowlin";
        Connection conn = DriverManager.getConnection(url);
        connection = conn;
        System.out.println("[DATABASE] Connected to PostgreSQL Database!");
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
}
