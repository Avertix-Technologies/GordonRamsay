package org.kowlintech.commands.misc;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.kowlintech.utils.constants.Global;

public class FeedbackCommand extends Command {

    public FeedbackCommand(Category category) {
        this.name = "feedback";
        this.help = "Sends feedback to the bot developers";
        this.arguments = "<feedback>";
        this.guildOnly = true;
        this.category = category;
    }

    @Override
    protected void execute(CommandEvent event) {
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
        eb.addField("User", event.getAuthor().getAsTag(), true);
        eb.addField("User ID", event.getAuthor().getId(), true);
        eb.addField("Guild Name", event.getGuild().getName(), true);
        eb.addField("Guild ID", event.getGuild().getId(), true);
        eb.addField("Feedback", event.getArgs(), false);

        // Send the embed
        feedback.sendMessage(eb.build()).queue();
        event.reply("Your feedback has been submitted. If you abuse this command, you will lose your ability to send feedback.");
    }
}
