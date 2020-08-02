package org.kowlintech.commands.moderation;

import net.dv8tion.jda.api.entities.Message;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.enums.Category;
import org.kowlintech.utils.command.objects.enums.PermissionType;

import java.util.List;

@Command(name = "purge", category = Category.MODERATION, description = "Deletes the specified amount of messages.", args = "<amount>", permission = PermissionType.DELETE_MESSAGES)
public class PurgeCommand implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) {
        if(event.getArgs().isEmpty())
        {
            event.reply("You gotta give me an amount, you fucking idiot!");
            return;
        }
        else {
            try {
                Integer toPurge = Integer.parseInt(event.getArgs());
                List<Message> messages = event.getChannel().getHistory().retrievePast(toPurge + 1).complete();
                messages.add(event.getRawEvent().getMessage());
                event.getChannel().purgeMessages(messages);
            } catch (Exception ex) {
                event.reply("Ok Discord is being a bitch and not letting me delete those messages. You might just want to manually delete those messages if you can.");
                return;
            }
        }
    }
}
