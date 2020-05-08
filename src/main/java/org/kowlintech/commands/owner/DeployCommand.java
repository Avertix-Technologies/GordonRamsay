package org.kowlintech.commands.owner;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.kowlintech.GordonRamsay;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class DeployCommand extends Command {

    public DeployCommand(Category category) {
        this.name = "deploy";
        this.guildOnly = true;
        this.help = "Deploys a new update to the bot.";
        this.category = category;
        this.ownerCommand = true;
    }

    // \/ \/ \/ \/ \/ \/ \/ \/ \/
    /**
     * THIS COMMAND IS NOT READY
     * DO NOT EDIT THIS AND DO NOT EXECUTE IT IN DISCORD.
     */
    // /\ /\ /\ /\ /\ /\ /\ /\ /\

    // Don't judge me about this I know it's a clusterfuck right now.

    @Override
    protected void execute(CommandEvent event) {
        if(GordonRamsay.socketclient == null) {
            GordonRamsay.openSocketClient();
        }
        event.getChannel().sendMessage("<:idle:683864575417778222> Contacting Deployment Daemon...").queue((message -> {
            try {
                Socket client = GordonRamsay.socketclient;
                ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                oos.writeObject("UpdateGordon");

                ObjectInputStream ois = new ObjectInputStream(client.getInputStream());

                while (ois.readObject() == null) {
                    message.editMessage(message.getContentRaw().replace("<:idle:683864575417778222>", "<:online:683864575095078944>") + "\n<:online:683864575095078944> Pulled from Github repository.").queue();

                    String string = (String) ois.readObject();

                    if(string == "None") {
                        message.editMessage(message.getContentRaw() + "\n<:dnd:683864575464046592> There were no changed files in this update.");
                        return;
                    } else if(string.contains("\n")) {
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setTitle("Commits");
                        eb.setDescription("**-** " + string);
                        eb.setColor(Color.GREEN);
                        message.getChannel().sendMessage(eb.build()).queue();
                        message.editMessage(message.getContentRaw() + "\n<:online:683864575095078944> Deployment Successful! Now restarting...");
                        return;
                    }
                }

            } catch (IOException | ClassNotFoundException e) {
                message.editMessage(message.getContentRaw() + "\n<:dnd:683864575464046592> An error occurred while receiving information from the daemon.").queue();
                e.printStackTrace();
            }
        }));
    }
}