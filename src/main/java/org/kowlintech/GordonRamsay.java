package org.kowlintech;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.apache.commons.collections4.map.LRUMap;
import org.discordbots.api.client.DiscordBotListAPI;
import org.kowlintech.listeners.CommandListener;
import org.kowlintech.listeners.JoinLeaveListener;
import org.kowlintech.listeners.ShardListener;
import org.kowlintech.utils.*;
import org.kowlintech.utils.command.CommandManager;
import org.kowlintech.utils.command.objects.*;
import org.kowlintech.utils.snipe.SnipeManager;
import org.postgresql.util.PSQLException;
import org.reflections.Reflections;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

public class GordonRamsay extends ListenerAdapter {

    public static JDA jda;
    private static Connection connection;
    private static LRUMap<Integer, String> insults;
    public static ArrayList<String> devIds;
    private static List<ObjectCommand> commands;
    private static List<ObjectSlashCommand> slashCommands;
    public static DiscordBotListAPI dblAPI;
    private static HashMap<String, ObjectCommand> commandHashMap;
    public static Properties config;

    public static HashMap<Long, String> shovelCooldownHash;
    public static HashMap<Long, String> crimeCooldownHash;
    public static HashMap<Long, String> askCooldownHash;

    private static EconomyManager economyManager;
    private static PhraseManager phraseManager;
    private static ChangelogManager changelogManager;
    private static CommandManager commandManager;
    private static AliasManager aliasManager;
    private static SnipeManager snipeManager;
    private static FeedbackManager feedbackManager;

    public static HashMap<String, ObjectCommand> aliases;

    private static String dbip;
    private static String dbuser;
    private static String dbpasswd;
    private static String dbname;

    private static ShardManager shards;
    public static HashMap<Integer, Long> shard_start_times;

    public static MessageEmbed notice;

    public static void main(String[] args) throws LoginException, IllegalArgumentException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        shard_start_times = new HashMap<>();

        Properties prop = new Properties();
        FileInputStream file = new FileInputStream("gordon.properties");
        prop.load(file);

        config = prop;

        EventWaiter waiter = new EventWaiter();

        insults = new LRUMap<>();
        commandManager = new CommandManager();
        devIds = new ArrayList<>();
        devIds.add("525050292400685077");
        devIds.add("363850072309497876");
        commands = new ArrayList<>();
        slashCommands = new ArrayList<>();
        commandHashMap = new HashMap<>();
        shovelCooldownHash = new HashMap<>();
        crimeCooldownHash = new HashMap<>();
        askCooldownHash = new HashMap<>();
        aliases = new HashMap<>();

        String prefix = prop.getProperty("prefix");
        String token = prop.getProperty("token");
        dbip = prop.getProperty("dbip");
        dbuser = prop.getProperty("dbuser");
        dbpasswd = prop.getProperty("dbpasswd");
        dbname = prop.getProperty("dbname");

        shards = DefaultShardManagerBuilder
                .createLight(token, Arrays.asList(GatewayIntent.values()))
                .setShardsTotal(2)
                .setShards(0, 1)
                .setActivity(Activity.watching("for " + prefix + "help"))
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .addEventListeners(
                        waiter,
                        new JoinLeaveListener(),
                        new CommandListener(),
                        new ShardListener()
                )
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .setChunkingFilter(ChunkingFilter.ALL)
                .enableCache(CacheFlag.MEMBER_OVERRIDES)
                .disableIntents(GatewayIntent.GUILD_PRESENCES)
                .build();

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

        aliasManager = new AliasManager();

        try {
            checkTables();
            prepareInsults();
        } catch (Exception ex) {}
        changelogManager = new ChangelogManager(jda);

        economyManager = new EconomyManager(jda, connection);
        phraseManager = new PhraseManager();
        snipeManager = new SnipeManager();
        feedbackManager = new FeedbackManager();

        registerCommands();

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Notice");
        eb.setColor(Color.YELLOW);
        eb.setDescription("This bot will be shutting down soon. Please [join the Discord Server](https://discord.gg/gTuvXYN/) for more information.");
        notice = eb.build();
    }

    private static void openDatabaseConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://" + dbip + "/" + dbname + "?user=" + dbuser + "&password=" + dbpasswd + "&sslmode=require";
        Connection conn = DriverManager.getConnection(url);
        connection = conn;
        System.out.println("[DATABASE] Connected to PostgreSQL Database!");
    }

    private static void checkTables() throws SQLException {
        Statement st = connection.createStatement();
        st.execute("CREATE TABLE IF NOT EXISTS insults (id SERIAL PRIMARY KEY, insult TEXT NOT NULL UNIQUE);");
        st.execute("CREATE TABLE IF NOT EXISTS changelog (timeadded BIGINT NOT NULL, title TEXT NOT NULL, description TEXT NOT NULL);");
        st.execute("CREATE TABLE IF NOT EXISTS settings (guildid BIGINT NOT NULL, temperatureunit TEXT NOT NULL)");
        st.execute("CREATE TABLE IF NOT EXISTS economy(coins BIGINT DEFAULT 0 NOT NULL, userid BIGINT NOT NULL, guildid BIGINT NOT NULL)");
        st.execute("CREATE TABLE IF NOT EXISTS shovel(text TEXT NOT NULL, fate BOOL NOT NULL, id SERIAL PRIMARY KEY)");
        st.execute("CREATE TABLE IF NOT EXISTS crime(text TEXT NOT NULL, fate BOOL NOT NULL, id SERIAL PRIMARY KEY)");
        st.execute("CREATE TABLE IF NOT EXISTS ask(text TEXT NOT NULL, fate BOOL NOT NULL, id SERIAL PRIMARY KEY)");
        st.execute("CREATE TABLE IF NOT EXISTS daily(userid BIGINT NOT NULL, guildid BIGINT NOT NULL, time BIGINT NOT NULL)");
        st.execute("CREATE TABLE IF NOT EXISTS snipe_settings(guildid BIGINT NOT NULL, enabled BOOL NOT NULL)");
        st.execute("CREATE TABLE IF NOT EXISTS snipe(contents TEXT NOT NULL, usr BIGINT NOT NULL, guild BIGINT NOT NULL, channel BIGINT NOT NULL, message BIGINT NOT NULL UNIQUE, bot BOOL NOT NULL, time BIGINT NOT NULL)");
        st.execute("CREATE TABLE IF NOT EXISTS fb_guild_blacklist(guildid BIGINT NOT NULL)");
        st.execute("CREATE TABLE IF NOT EXISTS fb_user_blacklist(userid BIGINT NOT NULL)");
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
            commandHashMap.put(cmd.name(), new ObjectCommand(executor, cmd));

            for(String str : cmd.aliases()) {
                if(!str.isEmpty()) {
                    if(!aliasManager.isAliasRegistered(str)) {
                        aliasManager.addAlias(new ObjectCommand(executor, cmd), str);
                    }
                }
            }
        }
        System.out.println("Registered Commands! (Count: " + commands.size() + ")");
    }

    public static ShardManager getShardManager() {
        return shards;
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

    public static HashMap<String, ObjectCommand> getCommandHashMap() { return commandHashMap; }

    public static EconomyManager getEconomyManager() { return economyManager; }

    public static PhraseManager getPhraseManager() { return phraseManager; }

    public static SnipeManager getSnipeManager() { return snipeManager; }

    public static List<ObjectSlashCommand> getSlashCommands() { return slashCommands; }

    public static FeedbackManager getFeedbackManager() {
        return feedbackManager;
    }
}
