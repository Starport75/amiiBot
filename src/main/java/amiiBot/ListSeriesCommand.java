package amiiBot;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.util.ArrayList;

public class ListSeriesCommand extends AbstractCommand {
    String description = "Default description. Contact the creator if you are reading this message";
    String command = "listSeries";

    public EmbedBuilder getOutput(String userDiscordID, UserAmiiboList amiiboList, ArrayList<String> parameters) {

        if (parameters.size() < 1) {
            EmbedBuilder embed = new EmbedBuilder().setDescription(
                    "Error: No parameters defined. Command structure is !listSeries <Type>");
            return embed;
        }

        if (amiiboList.getTypeIndex(parameters.get(0)) == -1) {
            EmbedBuilder embed = new EmbedBuilder()
                    .setDescription("Error: Parameter \"" + parameters.get(0) + "\" was not recognized!");
            return embed;
        }

        EmbedBuilder embed = new EmbedBuilder()
                .setDescription(amiiboList.getSeriesList(amiiboList.getTypeAt(amiiboList.getTypeIndex(parameters.get(0)))).toString());
        updateLength(amiiboList.getSeriesList(amiiboList.getTypeAt(amiiboList.getTypeIndex(parameters.get(0)))).toString());
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
