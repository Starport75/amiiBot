package amiiBot;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.util.ArrayList;

public class ListTypesCommand extends AbstractCommand {
    String description = "Default description. Contact the creator if you are reading this message";
    String command = "listTypes";

    public EmbedBuilder getOutput(String userDiscordID, UserAmiiboList amiiboList, ArrayList<String> parameters) {

        EmbedBuilder embed = new EmbedBuilder()
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
