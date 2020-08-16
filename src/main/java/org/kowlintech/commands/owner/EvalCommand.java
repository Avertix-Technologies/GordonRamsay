package org.kowlintech.commands.owner;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.enums.Category;
import org.kowlintech.utils.constants.Global;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Command(name = "eval", category = Category.OWNER, description = "Eval command.", args = "<code>")
public class EvalCommand implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) {
        SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy HH:mm ss");

        String[] args = event.getRawEvent().getMessage().getContentRaw().split(" ");

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
        engine.put("database", GordonRamsay.getDatabaseConnection());
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