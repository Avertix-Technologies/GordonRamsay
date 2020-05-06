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

    public static void main(String[] args) throws IOException, LoginException, IllegalArgumentException, RateLimitedException {
        Config config = new Config();

        // The JDA Utilities EventWaiter.
        EventWaiter waiter = new EventWaiter();

        CommandClientBuilder client = new CommandClientBuilder();

        // Setting up the CommandClient.
        client.setOwnerId("525050292400685077");
        client.setCoOwnerIds("363850072309497876");
        client.setEmojis("✅", "⚠", "❌");
        client.useHelpBuilder(false);
        client.setPrefix(config.getPrefix());

        JDA jda = new JDABuilder(AccountType.BOT)
                // set the token
                .setToken(config.getToken())

                // set the game for when the bot is loading
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setActivity(Activity.playing("Starting Up... Please Wait"))

                // add the listeners
                .addEventListeners(
                        waiter,
                        client.build()
                )
                .build();

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        String[] activities = new String[]{"with " + config.getPrefix() + "help", "", "People Type"};
        Random r = new Random();
        Runnable task = () -> {
            int randomactivity=r.nextInt(activities.length);
            if(activities[randomactivity] == "for " + config.getPrefix() + "cook") {
                jda.getPresence().setActivity(Activity.watching(activities[randomactivity]));
            } else if (activities[randomactivity] == "Spotify") {
                jda.getPresence().setActivity(Activity.listening(activities[randomactivity]));
            } else {
                jda.getPresence().setActivity(Activity.playing(activities[randomactivity]));
            }
        };

        executor.scheduleWithFixedDelay(task, 0, 25, TimeUnit.SECONDS);
    }

    private void openDatabaseConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://127.0.0.1/ramsay?user=postgres&password=kowlin";
        Connection conn = DriverManager.getConnection(url);
        connection = conn;
        System.out.println("[DATABASE] Connected to PostgreSQL Database!");
    }

    public static Connection getDatabaseConnection() {
        return connection;
    }
}
