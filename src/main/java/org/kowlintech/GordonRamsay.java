package org.kowlintech;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.exceptions.RateLimitedException;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import org.kowlintech.commands.misc.HelpCommand;
import org.kowlintech.utils.Categories;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GordonRamsay {

    private static JDA jda;
    private static Connection connection;

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
                new HelpCommand(Categories.MISCELLANEOUS)
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
}
