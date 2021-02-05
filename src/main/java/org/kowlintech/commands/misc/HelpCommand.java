package org.kowlintech.commands.misc;

import net.dv8tion.jda.api.EmbedBuilder;
import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.ObjectCommand;
import org.kowlintech.utils.command.objects.enums.Category;
import org.kowlintech.utils.constants.Global;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

@Command(name = "help", category = Category.MISCELLANEOUS, description = "Shows a list of commands or info about a command.", args = "[command]")
public class HelpCommand implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(event.getJDA().getSelfUser().getName() + " Bot Commands");
        eb.setColor(Global.COLOR);
        eb.setTimestamp(Instant.now());
        eb.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
        eb.setFooter("Developed by Kowlin#0001 & Starman#8456");

        for(Category cate : Category.values()) {
            HashMap<String, ObjectCommand> cmdList = GordonRamsay.getCommandHashMap();
            if(cmdList.size() > 0) {
                ArrayList<String> sb = new ArrayList<>();

                for(ObjectCommand command : cmdList.values()) {
                    if(command.getInterface().category() == cate) {
                        sb.add(command.getInterface().name());
                    }
                }
                if(sb.size() > 0) {
                    eb.addField(cate.getName(), String.join(" **|** ", sb), false);
                }
            }
        }

        event.reply(eb.build());
    }
}
