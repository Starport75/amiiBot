package amiiBot;

import java.util.ArrayList;

public class EasterEggCommand extends AbstractCommand{
	String description = "...?";
    String command = "ligma";
    String parameterString = "";
    int accessLevel = 0;
    EasterEgg egg = new EasterEgg();

    public BetterEmbed getOutput(String userDiscordID, int accessLevel, UserAmiiboList amiiboList, ArrayList<String> parameters, EasterEgg egg) {
                return new BetterEmbed().setDescription(egg.setEE(true, userDiscordID));
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
