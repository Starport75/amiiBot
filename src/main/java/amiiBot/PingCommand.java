package amiiBot;

import java.util.ArrayList;

class PingCommand extends AbstractCommand {
    String command = "ping";
    String description = "A test command";

    public BetterEmbed getOutput(String userDiscordID, UserAmiiboList amiiboList, ArrayList<String> parameters) {
    	BetterEmbed embed = new BetterEmbed()
                .setDescription("Pong!");
        return embed;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public void updateLength(String output) {
        length = output.length();
    }

    public int getLength() {
        return length;
    }
}
