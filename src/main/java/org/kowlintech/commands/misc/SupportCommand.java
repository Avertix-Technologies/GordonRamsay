package org.kowlintech.commands.misc;

import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.enums.Category;

@Command(name = "support", category = Category.MISCELLANEOUS, description = "Gets an invite to the support server")
public class SupportCommand implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) {
        event.reply("https://discord.gg/SW7bmXm");
    }
}
