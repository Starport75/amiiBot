package amiiBot;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.util.ArrayList;

class PingCommand extends AbstractCommand {
    String command = "ping";
    String description = "A test command";

    public EmbedBuilder getOutput(String userDiscordID, ArrayList<String> parameters) {
        EmbedBuilder embed = new EmbedBuilder()
                .setDescription("Pong!");
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
