package amiiBot;

import java.util.ArrayList;

class PingCommand extends AbstractCommand {
    String command = "ping";
    String description = "A test command";
    String parameterString = "";
    int accessLevel = 1;

    public BetterEmbed getOutput(String userDiscordID, int accessLevel, UserAmiiboList amiiboList, ArrayList<String> parameters, EasterEgg egg) {
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
    
    public int getAccessLevel() {
    	return accessLevel;
    }
    
    public String getParameters() {
    	return parameterString;
    }
}
