package amiiBot;

import java.util.ArrayList;

public class ListTypesCommand extends AbstractCommand {
    String description = "Default description. Contact the creator if you are reading this message";
    String command = "listTypes";
    int accessLevel = 0;

    public BetterEmbed getOutput(String userDiscordID, int accessLevel, UserAmiiboList amiiboList, ArrayList<String> parameters, EasterEgg egg) {

    	BetterEmbed embed = new BetterEmbed()
                .setDescription(amiiboList.getTypeList().toString());
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
}
