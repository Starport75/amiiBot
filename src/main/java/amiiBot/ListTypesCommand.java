package amiiBot;

import java.util.ArrayList;

public class ListTypesCommand extends AbstractCommand {
    String description = "Lists the different types of amiibo";
    String command = "listTypes";
    String parameterString = "";
    int accessLevel = 0;

    public BetterEmbed getOutput(String userDiscordID, int accessLevel, UserAmiiboList amiiboList, ArrayList<String> parameters, EasterEgg egg) {

    	
    	String output = "**amiibo Types:**";
    	
    	for (int listIndex = 0; listIndex < amiiboList.getTypeList().size(); listIndex++) {
    		output = output + "\n*" + amiiboList.getTypeList().get(listIndex) + "*";
    	}
    	
    	BetterEmbed embed = new BetterEmbed()
                .setDescription(output);
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
