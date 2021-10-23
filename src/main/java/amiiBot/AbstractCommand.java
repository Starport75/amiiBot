package amiiBot;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.util.ArrayList;

abstract class AbstractCommand {
    String description = "Default description. Contact the creator if you are reading this message";
    String command;
    int length = -1;

    public EmbedBuilder getOutput(String userDiscordID, ArrayList<String> parameters) {
        EmbedBuilder embed = new EmbedBuilder()
                .setDescription("This command has not been set up. Please contact the creator");
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
