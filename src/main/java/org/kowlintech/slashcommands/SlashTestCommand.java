package org.kowlintech.slashcommands;

import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.SlashCommand;
import org.kowlintech.utils.command.objects.SlashCommandExecutor;
import org.kowlintech.utils.command.objects.enums.Category;

@SlashCommand(name = "slashtest", category = Category.MISCELLANEOUS, description = "Tests the discord slash commands.")
public class SlashTestCommand implements SlashCommandExecutor {

    @Override
    public void execute(CommandEvent event) {
        event.reply("Test");
    }
}
