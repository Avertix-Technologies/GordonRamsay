package org.kowlintech.commands.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RPSCommand extends Command {

    public RPSCommand(Category category) {
        this.name = "rps";
        this.help = "Play rock paper scissors with the bot";
        this.arguments = "<Choice>";
        this.guildOnly = true;
        this.category = category;
    }

    @Override
    protected void execute(CommandEvent event) {
        if(event.getArgs().isEmpty()){
            event.reply("You need to tell me what your decision is, you fucking idiot!");
            return;
        }

        List<String> decisions = Arrays.asList(
                "Rock",
                "Paper",
                "Scissors"
        );
        Random rand = new Random();
        String decision = decisions.get(rand.nextInt(decisions.size()));
        if(event.getArgs().equalsIgnoreCase("rock")) {
            if(decision == "Paper") {
                event.reply("You chose **Rock** and I chose **Paper**. I win!");
                return;
            }
            if(decision == "Rock") {
                event.reply("You chose **Rock** and I chose **Rock**. It's a tie ¯\\_(ツ)_/¯");
                return;
            }
            if(decision == "Scissors") {
                event.reply("You chose **Rock** and I chose **Scissors**. You win \uD83D\uDE26");
                return;
            }
        }
        if(event.getArgs().equalsIgnoreCase("paper")) {
            if(decision == "Paper") {
                event.reply("You chose **Paper** and I chose **Paper**. It's a tie ¯\\_(ツ)_/¯");
                return;
            }
            if(decision == "Rock") {
                event.reply("You chose **Paper** and I chose **Rock**. You win \uD83D\uDE26");
                return;
            }
            if(decision == "Scissors") {
                event.reply("You chose **Paper** and I chose **Scissors**. I win!");
                return;
            }
        }
        if(event.getArgs().equalsIgnoreCase("scissors")) {
            if(decision == "Paper") {
                event.reply("You chose **Scissors** and I chose **Paper**. You win \uD83D\uDE26");
                return;
            }
            if(decision == "Rock") {
                event.reply("You chose **Scissors** and I chose **Rock**. I win!");
                return;
            }
            if(decision == "Scissors") {
                event.reply("You chose **Scissors** and I chose **Scissors**. It's a tie ¯\\_(ツ)_/¯");
                return;
            }
        }
    }
}
