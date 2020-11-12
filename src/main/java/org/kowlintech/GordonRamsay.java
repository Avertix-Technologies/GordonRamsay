package org.kowlintech;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.apache.commons.collections4.map.LRUMap;
import org.discordbots.api.client.DiscordBotListAPI;
import org.kowlintech.listeners.CommandListener;
import org.kowlintech.listeners.JoinLeaveListener;
import org.kowlintech.utils.ChangelogManager;
import org.kowlintech.utils.InsultManager;
import org.kowlintech.utils.command.CommandManager;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.ObjectCommand;
import org.postgresql.util.PSQLException;
import org.reflections.Reflections;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.lang.annotation.Annotation;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

public class GordonRamsay extends ListenerAdapter {

    private static JDA jda;
    private static Connection connection;
    private static LRUMap<Integer, String> insults;
    private static CommandManager commandManager;
    public static ArrayList<String> devIds;
    private static ChangelogManager changelogManager;
    private static List<ObjectCommand> commands;
    public static DiscordBotListAPI dblAPI;

    public static void main(String[] args) throws LoginException, IllegalArgumentException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Config config = new Config();

        EventWaiter waiter = new EventWaiter();

        insults = new LRUMap<>();
        commandManager = new CommandManager();
        devIds = new ArrayList<>();
        devIds.add("525050292400685077");
        devIds.add("363850072309497876");
        commands = new ArrayList<>();

        JDABuilder builder = JDABuilder.createDefault(config.getToken());
        builder.setEnabledIntents(Arrays.asList(GatewayIntent.values()));
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.enableCache(CacheFlag.MEMBER_OVERRIDES);
        builder.disableIntents(GatewayIntent.GUILD_PRESENCES);
        builder.addEventListeners(
                waiter,
                new JoinLeaveListener(),
                new CommandListener()
        );
        jda = builder.build();
        jda.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
        jda.getPresence().setActivity(Activity.watching("for " + config.getPrefix() + "help"));

        try {
            openDatabaseConnection();
        } catch (PSQLException ex) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Database Connection Error");
            eb.setDescription("```" + ex.toString() + "```");
            eb.setColor(Color.RED);
            eb.setTimestamp(LocalDateTime.now(ZoneId.systemDefault()));
            TextChannel ch = jda.getTextChannelById("744579696695705832");
            ch.sendMessage("<@525050292400685077> **Action Required!**").queue();
            ch.sendMessage(eb.build()).queue();
        }
        try {
            checkTables();
            prepareInsults();
        } catch (Exception ex) {}
        changelogManager = new ChangelogManager(jda);
        registerCommands();

        dblAPI = new DiscordBotListAPI.Builder()
                .token("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjUyODk4NDU1ODYyOTAyNzg0MSIsImJvdCI6dHJ1ZSwiaWF0IjoxNjAxNTkzNzI4fQ.6Xt6iiBM-0m7UpOBxicPCCYPPSrH0J_Ok3771W43nm0")
                .botId(jda.getSelfUser().getId())
                .build();
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
        st.execute("CREATE TABLE IF NOT EXISTS changelog (timeadded BIGINT NOT NULL, title TEXT NOT NULL, description TEXT NOT NULL);");
        st.execute("CREATE TABLE IF NOT EXISTS settings (guildid BIGINT NOT NULL, temperatureunit TEXT NOT NULL)");
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

    private static void registerCommands() throws IllegalAccessException, InstantiationException {
        Reflections reflections = new Reflections("org.kowlintech.commands");

        for(Class classc : reflections.getSubTypesOf(CommandExecutor.class)) {
            CommandExecutor executor = (CommandExecutor) classc.newInstance();
            Annotation annotation = executor.getClass().getDeclaredAnnotation(org.kowlintech.utils.command.objects.Command.class);
            org.kowlintech.utils.command.objects.Command cmd = (org.kowlintech.utils.command.objects.Command) annotation;
            commands.add(new ObjectCommand(executor, cmd));
        }
        System.out.println("Registered Commands! (Count: " + commands.size() + ")");
    }

    public static CommandManager getCommandManager() {
        return commandManager;
    }

    public static Connection getDatabaseConnection() {
        return connection;
    }

    public static ChangelogManager getChangelogManager() {
        return changelogManager;
    }

    public static List<ObjectCommand> getCommands() {
        return commands;
    }
}
