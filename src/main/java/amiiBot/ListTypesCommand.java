package amiiBot;

import java.util.ArrayList;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class ListTypesCommand extends AbstractCommand {
    String description = "Lists the different types of amiibo";
    String command = "listTypes";
    String parameterString = "";
    int accessLevel = 0;

    public BetterEmbed getOutput(String userDiscordID, int accessLevel, UserAmiiboList amiiboList,
			ArrayList<String> parameters, EasterEgg egg, DiscordApi api, Server currServer, User currUser) {

    	
    	String output = "**amiibo Types:**";
    	
    	for (int listIndex = 0; listIndex < amiiboList.getTypeList().size(); listIndex++) {
    		output = output + "\n*" + capitalize(amiiboList.getTypeList().get(listIndex)) + "*";
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
    
    public String capitalize(String word) {
		String cOutput = ("" + word.charAt(0)).toUpperCase();

		for (int i = 1; i < word.length(); i++) {
			if (word.charAt(i - 1) == ' ' || word.charAt(i - 1) == '(' || word.charAt(i -1) == '-') {
				cOutput = cOutput + ("" + word.charAt(i)).toUpperCase();
			} else {
				cOutput = cOutput + word.charAt(i);
			}
		}
		return cOutput;
	}
}
