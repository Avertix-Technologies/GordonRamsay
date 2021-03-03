package org.kowlintech.commands.misc;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.FeedbackManager;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.enums.Category;
import org.kowlintech.utils.constants.Global;

import java.awt.*;
import java.time.Instant;

@Command(name = "feedback", category = Category.MISCELLANEOUS, description = "Sends feedback to the bot developers", args = "<feedback>")
public class FeedbackCommand implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) {
        FeedbackManager manager = GordonRamsay.getFeedbackManager();

        if(manager.isUserBlacklisted(event.getMember().getIdLong())) {
            event.reply(":x: You have been blacklisted from using this command!");
            return;
        } else if(manager.isGuildBlacklisted(event.getGuild().getIdLong())) {
            event.reply(":x: This guild has been blacklisted from using this command!");
            return;
        }
        // If there's no input, insult the user and return, as always.
        if(event.getArgs().isEmpty()) {
            event.reply("You fucking donkey! You need to provide your feedback!");
            return;
        }

        // Get the feedback channel
        TextChannel feedback = event.getJDA().getTextChannelById(Global.FEEDBACK);

        // Basic embed setup
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("**Feedback**");
        eb.setColor(Global.COLOR);

        // Embed fields
        eb.addField("User", event.getMember().getUser().getName() + "#" + event.getMember().getUser().getDiscriminator(), true);
        eb.addField("User ID", event.getMember().getId(), true);
        eb.addField("Guild Name", event.getGuild().getName(), true);
        eb.addField("Guild ID", event.getGuild().getId(), true);
        eb.addField("Feedback", event.getArgs(), false);

        // Send the embed
        feedback.sendMessage(eb.build()).queue();
        EmbedBuilder eb1 = new EmbedBuilder();
        eb1.setTitle("Feedback Submitted");
        eb1.setDescription("Your feedback has been submitted.\n\n**If you abuse this command in any way, your ability to give feedback will be removed.**");
        eb1.setColor(Color.GREEN);
        eb1.setTimestamp(Instant.now());
        event.reply(eb1.build());
    }
}
