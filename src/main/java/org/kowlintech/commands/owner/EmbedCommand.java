package org.kowlintech.commands.owner;

import net.dv8tion.jda.api.EmbedBuilder;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.enums.Category;
import org.kowlintech.utils.constants.Global;

@Command(name = "embed", category = Category.OWNER, description = "Sends an embed with the command contents.", args = "<contents>")
public class EmbedCommand implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Global.COLOR);
        eb.setDescription(event.getArgs());
        event.reply(eb.build());
    }
}
