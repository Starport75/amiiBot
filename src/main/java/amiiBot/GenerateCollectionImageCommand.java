package amiiBot;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.json.JSONObject;

import java.util.ArrayList;

public class GenerateCollectionImageCommand extends AbstractCommand {
    String description = "Default description. Contact the creator if you are reading this message";
    String command = "generateImage";

    public EmbedBuilder getOutput(String userDiscordID, ArrayList<String> parameters) {

        AmiiboHuntAccess websiteData = new AmiiboHuntAccess();

        JSONObject data = new JSONObject(websiteData.sendPostRequest("https://www.amiibohunt.com/api/discord/v1/getCollectionImageById", userDiscordID));
        String output = data.get("val").toString();

        EmbedBuilder embed = new EmbedBuilder()
                .setImage(output);
        updateLength(output);
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
