package org.kowlintech.commands.owner;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Message;
import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.constants.Global;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class EvalCommand extends Command {

    public EvalCommand(Category category) {
        this.name = "eval";
        this.guildOnly = true;
        this.help = "Eval command.";
        this.arguments = "<code>";
        this.category = category;
    }

    // \/ \/ \/ \/ \/ \/ \/ \/ \/
    /**
     * THIS COMMAND IS NOT READY
     * DO NOT EDIT THIS AND DO NOT EXECUTE IT IN DISCORD.
     */
    // /\ /\ /\ /\ /\ /\ /\ /\ /\

    // Don't judge me about this I know it's a clusterfuck right now.

    @Override
    protected void execute(CommandEvent event) {
        if(event.getAuthor().getId() != event.getClient().getOwnerId()) {
            event.reply("no");
            return;
        }

        SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy HH:mm ss");

        String[] args = event.getMessage().getContentRaw().split(" ");

        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

        if(event.getArgs().isEmpty()) {
            event.reply("You gotta give me something to execute, you fucking idiot!");
            return;
        }

        /* Imports */
        try {
            engine.eval("var imports = new JavaImporter(java.io, java.lang, java.util);");
        } catch (ScriptException ex) {
            ex.printStackTrace();
        }

        /* Put string representations */
        engine.put("jda", event.getJDA());
        engine.put("api", event.getJDA());
        engine.put("e", event);
        engine.put("dnd", OnlineStatus.DO_NOT_DISTURB);
        engine.put("idle", OnlineStatus.IDLE);
        engine.put("online", OnlineStatus.ONLINE);
        engine.put("SDF", SDF);
        engine.put("Date", new Date());

        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

        ScheduledFuture<?> future = service.schedule(() -> {

            /* Initialize Objects */
            long startExec = System.currentTimeMillis();
            Object out;
            EmbedBuilder message = new EmbedBuilder()
                    .setTitle("Eval")
                    .setTimestamp(Instant.now())
                    .setColor(Global.COLOR);

            try {
                /* Build input script */
                String script = "";
                for (int i = 1; i < args.length; i++) {
                    args[i] = args[i].replace("```java", "").replace("```", "");
                    script += i == args.length-1 ? args[i]:args[i]+" ";
                }
                message.addField(" Input", "```java\n\n" + script + "```", false);

                /* Output */
                out = engine.eval(script);
                message.addField(" Output", "```java\n\n" + out.toString() + "```", false);

                /* Exception */
            } catch (Exception ex) {
                message.addField(" Error", "```java\n\n" + ex.getMessage() + "```", false);
            }

            event.reply(message.build());

            service.shutdownNow();

        }, 0, TimeUnit.MILLISECONDS);
    }
}