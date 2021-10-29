package amiiBot;

import java.util.ArrayList;

public class ListSeriesCommand extends AbstractCommand {
    String description = "Default description. Contact the creator if you are reading this message";
    String command = "listSeries";

    public BetterEmbed getOutput(String userDiscordID, UserAmiiboList amiiboList, ArrayList<String> parameters, EasterEgg egg) {

        if (parameters.size() < 1) {
        	return new BetterEmbed().setError("Error: No parameters defined. Command structure is !listSeries <Type>");
        }

        if (amiiboList.getTypeIndex(parameters.get(0)) == -1) {
        	return new BetterEmbed().setError("Error: Parameter \"" + parameters.get(0) + "\" was not recognized!");
        }

        BetterEmbed embed = new BetterEmbed()
                .setDescription(amiiboList.getSeriesList(amiiboList.getTypeAt(amiiboList.getTypeIndex(parameters.get(0)))).toString());
        return embed;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }
}
