package org.kowlintech;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.collections4.map.LRUMap;
import org.kowlintech.listeners.CommandListener;
import org.kowlintech.listeners.JoinLeaveListener;
import org.kowlintech.utils.InsultManager;
import org.kowlintech.utils.command.CommandManager;

import javax.security.auth.login.LoginException;
import java.sql.*;
import java.util.ArrayList;

public class GordonRamsay extends ListenerAdapter {

    private static JDA jda;
    private static Connection connection;
    private static LRUMap<Integer, String> insults;
    private static CommandManager commandManager;
    public static ArrayList<String> devIds;

    public static void main(String[] args) throws LoginException, IllegalArgumentException, SQLException, ClassNotFoundException {
        Config config = new Config();

        EventWaiter waiter = new EventWaiter();

        insults = new LRUMap<>();
        commandManager = new CommandManager();
        devIds = new ArrayList<>();
        devIds.add("525050292400685077");
        devIds.add("363850072309497876");

        jda = new JDABuilder(AccountType.BOT)
                .setToken(config.getToken())

                .setActivity(Activity.watching("for " + config.getPrefix() + "help"))

                .addEventListeners(
                        waiter,
                        new JoinLeaveListener(),
                        new CommandListener()
                )
                .build();

        openDatabaseConnection();
        checkTables();
        prepareInsults();
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

    public static CommandManager getCommandManager() {
        return commandManager;
    }

    public static Connection getDatabaseConnection() {
        return connection;
    }
}
