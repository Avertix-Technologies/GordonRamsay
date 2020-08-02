package org.kowlintech.commands.misc;

import net.dv8tion.jda.api.EmbedBuilder;
import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.enums.Category;
import org.kowlintech.utils.constants.Global;

import java.util.ArrayList;

@Command(name = "help", category = Category.MISCELLANEOUS, description = "Shows a list of commands or info about a command.", args = "[command]")
public class HelpCommand implements CommandExecutor {

    private String funListString;
    private String miscListString;
    private String modListString;
    private String ownerListString;
    private String cmdusage;

    @Override
    public void execute(CommandEvent event) {
        ArrayList<String> funCommandList = new ArrayList<>();
        ArrayList<String> miscCommandList = new ArrayList<>();
        ArrayList<String> modCommandList = new ArrayList<>();
        ArrayList<String> ownerCommandList = new ArrayList<>();
        for(Command command : GordonRamsay.getCommandManager().getCommands()) {
            if(command.category() == Category.FUN) {
                funCommandList.add(command.name());
            }
            if(command.category() == Category.MISCELLANEOUS) {
                miscCommandList.add(command.name());
            }
            if(command.category() == Category.MODERATION) {
                modCommandList.add(command.name());
            }
            if(command.category() == Category.OWNER) {
                ownerCommandList.add(command.name());
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
        if(GordonRamsay.devIds.contains(event.getMember().getId())) {
            embed.addField("Owner", ownerListString, false);
        }
        embed.setColor(Global.COLOR);
        embed.setFooter("Made by Kowlin#4417 & Starman#5874 | Use g.help <command> to see command info.");
        if(!event.getArgs().isEmpty()) {
            ArrayList<String> commandArray = new ArrayList<String>();
            ArrayList<Command> commandArrayList = new ArrayList<Command>();
            for(Command cmd : GordonRamsay.getCommandManager().getCommands()) {
                commandArray.add(cmd.name());
                commandArrayList.add(cmd);
            }
            if(commandArray.contains(event.getArgs())) {
                Command cmd = commandArrayList.get(commandArray.indexOf(event.getArgs()));
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle(cmd.name().substring(0, 1).toUpperCase() + cmd.name().substring(1) + " Command");
                if(cmd.args() == null) {
                    cmdusage = cmd.description() + "\n\n**Usage:** g." + cmd.name();
                } else {
                    cmdusage = cmd.description() + "\n\n**Usage:** g." + cmd.name() + " " + cmd.args();
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
