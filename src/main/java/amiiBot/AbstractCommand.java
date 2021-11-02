package amiiBot;

import java.util.ArrayList;

abstract class AbstractCommand {
    String description = "Default description. Contact the creator if you are reading this message";
    String command;
    int accessLevel = 0;

    public BetterEmbed getOutput(String userDiscordID, int accessLevel, UserAmiiboList amiiboList, ArrayList<String> parameters, EasterEgg egg) {
                return new BetterEmbed().setError("This command has not been set up. Please contact the creator");
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
}
