package org.kowlintech.commands.misc;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class InviteCommand extends Command {

    public InviteCommand(Category category) {
        this.name = "invite";
        this.help = "Gets the bot's invite link";
        this.guildOnly = true;
        this.category = category;
    }

    @Override
    protected void execute(CommandEvent event) {
        event.reply("<https://discordapp.com/oauth2/authorize?client_id=528984558629027841&scope=bot&permissions=271969398>");
    }
}
