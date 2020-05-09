package org.kowlintech.commands.misc;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class SupportCommand extends Command {

    public SupportCommand(Category category) {
        this.name = "support";
        this.guildOnly = true;
        this.help = "Gets an invite to the support server";
        this.category = category;
    }

    @Override
    protected void execute(CommandEvent event) {
        event.reply("https://discord.gg/SW7bmXm");
    }
}
