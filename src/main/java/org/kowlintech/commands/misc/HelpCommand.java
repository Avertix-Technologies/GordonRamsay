package org.kowlintech.commands.misc;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import org.kowlintech.utils.Categories;
import org.kowlintech.utils.constants.Global;

import java.util.ArrayList;

public class HelpCommand extends Command {

    private String funListString;
    private String miscListString;
    private String modListString;
    private String ownerListString;
    private String cmdusage;


    public HelpCommand(Category category) {
        this.name = "help";
        this.guildOnly = true;
        this.help = "Shows a list of commands or info about a command.";
        this.category = category;
    }

    @Override
    protected void execute(CommandEvent event) {
        ArrayList<String> funCommandList = new ArrayList<String>();
        ArrayList<String> miscCommandList = new ArrayList<String>();
        ArrayList<String> modCommandList = new ArrayList<String>();
        ArrayList<String> ownerCommandList = new ArrayList<String>();
        for(Command command : event.getClient().getCommands()) {
            if(command.getCategory() == Categories.FUN) {
                funCommandList.add(command.getName());
            }
            if(command.getCategory() == Categories.MISCELLANEOUS) {
                miscCommandList.add(command.getName());
            }
            if(command.getCategory() == Categories.MODERATION) {
                modCommandList.add(command.getName());
            }
            if(command.getCategory() == Categories.OWNER) {
                ownerCommandList.add(command.getName());
            }
        }
        funListString = String.join(" **|** ", funCommandList);
        if(funCommandList.isEmpty()) { funListString = "None"; }
        miscListString = String.join(" **|** ", miscCommandList);
        if(miscCommandList.isEmpty()) { miscListString = "None"; }
        modListString = String.join(" **|** ", modCommandList);
        if(modCommandList.isEmpty()) { modListString = "None"; }
        ownerListString = String.join(" **|** ", ownerCommandList);
        if(ownerCommandList.isEmpty()) { ownerListString = "None"; }
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Gordon Ramsay Bot Commands");
        embed.addField("Fun", funListString, false);
        embed.addField("Miscellaneous", miscListString, false);
        embed.addField("Moderation", modListString, false);
        if(event.getMember() == event.getGuild().getMemberById(event.getClient().getOwnerId())) {
            embed.addField("Owner", ownerListString, false);
        }
        embed.setColor(Global.COLOR);
        embed.setFooter("Made by Kowlin#4417 & Starman#5874 | Use g!help <command> to see command info.");
        if(!event.getArgs().isEmpty()) {
            ArrayList<String> commandArray = new ArrayList<String>();
            ArrayList<Command> commandArrayList = new ArrayList<Command>();
            for(Command cmd : event.getClient().getCommands()) {
                commandArray.add(cmd.getName());
                commandArrayList.add(cmd);
            }
            if(commandArray.contains(event.getArgs())) {
                Command cmd = commandArrayList.get(commandArray.indexOf(event.getArgs()));
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle(cmd.getName().substring(0, 1).toUpperCase() + cmd.getName().substring(1) + " Command");
                if(cmd.getArguments() == null) {
                    cmdusage = cmd.getHelp() + "\n\n**Usage:** g!" + cmd.getName();
                } else {
                    cmdusage = cmd.getHelp() + "\n\n**Usage:** g!" + cmd.getName() + " " + cmd.getArguments();
                }
                eb.setDescription(cmdusage);
                eb.setColor(Global.COLOR);
                event.reply(eb.build());
                return;
            } else {
                event.reply(embed.build());
                return;
            }
        }
        event.reply(embed.build());
    }
}
