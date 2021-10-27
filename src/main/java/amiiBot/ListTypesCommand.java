package amiiBot;

import java.util.ArrayList;

public class ListTypesCommand extends AbstractCommand {
    String description = "Default description. Contact the creator if you are reading this message";
    String command = "listTypes";

    public BetterEmbed getOutput(String userDiscordID, UserAmiiboList amiiboList, ArrayList<String> parameters) {

    	BetterEmbed embed = new BetterEmbed()
                .setDescription(amiiboList.getTypeList().toString());
        updateLength(amiiboList.getTypeList().toString());
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
