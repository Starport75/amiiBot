package amiiBot;

import org.json.JSONObject;

import java.util.ArrayList;

public class GenerateCollectionImageCommand extends AbstractCommand {
    String description = "Default description. Contact the creator if you are reading this message";
    String command = "generateImage";
    int accessLevel = 0;

    public BetterEmbed getOutput(String userDiscordID, int accessLevel, UserAmiiboList amiiboList, ArrayList<String> parameters, EasterEgg egg) {

        AmiiboHuntAccess websiteData = new AmiiboHuntAccess();

        JSONObject data = new JSONObject(websiteData.sendPostRequest("https://www.amiibohunt.com/api/discord/v1/getCollectionImageById", userDiscordID));
        String output = data.get("val").toString();

        BetterEmbed embed = new BetterEmbed()
                .setImage(output);
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
