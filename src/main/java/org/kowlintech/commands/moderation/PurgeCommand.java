package org.kowlintech.commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.kowlintech.utils.constants.Global;

import java.awt.*;
import java.util.List;

public class PurgeCommand extends Command {

    public PurgeCommand(Category category) {
        this.name = "purge";
        this.guildOnly = true;
        this.help = "Deletes the specified amount of messages.";
        this.arguments = "<amount>";
        this.category = category;
        this.botPermissions = new Permission[]{Permission.MESSAGE_MANAGE};

        this.userPermissions = new Permission[]{Permission.MESSAGE_MANAGE};
    }

    @Override
    protected void execute(CommandEvent event) {
        if(event.getArgs().isEmpty())
        {
            event.reply("You gotta give me an amount, you fucking idiot!");
            return;
        }
        else {
            try {
                Integer toPurge = Integer.parseInt(event.getArgs());
                List<Message> messages = event.getChannel().getHistory().retrievePast(toPurge + 1).complete();
                messages.add(event.getMessage());
                event.getChannel().purgeMessages(messages);
            } catch (Exception ex) {
                event.reply("Ok Discord is being a bitch and not letting me delete those messages. You might just want to manually delete those messages if you can.");
                return;
            }
        }
    }
}
