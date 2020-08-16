package org.kowlintech.utils.command.objects.enums;

import org.kowlintech.commands.fun.*;
import org.kowlintech.commands.misc.*;
import org.kowlintech.commands.moderation.BanCommand;
import org.kowlintech.commands.moderation.KickCommand;
import org.kowlintech.commands.moderation.PurgeCommand;
import org.kowlintech.commands.owner.EvalCommand;
import org.kowlintech.commands.owner.ManageChangelogCommand;
import org.kowlintech.commands.owner.ManageInsultsCommand;
import org.kowlintech.utils.command.objects.CommandExecutor;

public enum EnumCommand {

    FOODMEME(new FoodMemeCommand(), new String[]{"fm"}),
    GAY(new Gay(), new String[]{}),
    INSULT(new InsultCommand(), new String[]{}),
    LAMBSAUCE(new LambSauceCommand(), new String[]{}),
    RPS(new RPSCommand(), new String[]{}),
    CHANGELOG(new Changelog(), new String[]{}),
    FEEDBACK(new FeedbackCommand(), new String[]{"fb"}),
    HELP(new HelpCommand(), new String[]{}),
    INFO(new InfoCommand(), new String[]{}),
    INVITE(new InviteCommand(), new String[]{}),
    PING(new PingCommand(), new String[]{}),
    SOCIAL(new SocialCommand(), new String[]{}),
    SUPPORT(new SupportCommand(), new String[]{}),
    UPTIME(new UptimeCommand(), new String[]{}),
    VOTE(new VoteCommand(), new String[]{}),
    BAN(new BanCommand(), new String[]{}),
    KICK(new KickCommand(), new String[]{}),
    PURGE(new PurgeCommand(), new String[]{}),
    EVAL(new EvalCommand(), new String[]{}),
    MANAGECHANGELOG(new ManageChangelogCommand(), new String[]{"mchangelog", "mcl"}),
    MANAGEINSULTS(new ManageInsultsCommand(), new String[]{});

    private String[] aliases;
    private CommandExecutor executor;

    EnumCommand(CommandExecutor executor, String[] aliases) {
        this.executor = executor;
        this.aliases = aliases;
    }

    public CommandExecutor getExecutor() {
        return executor;
    }

    public String[] getAliases() {
        return aliases;
    }
}
