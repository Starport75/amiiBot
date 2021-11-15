package amiiBot;

import org.json.JSONObject;

import java.util.ArrayList;

public class GenerateCollectionImageCommand extends AbstractCommand {
    String description = "Generates an image of all the figures you have collected";
    String command = "generateImage";
    String parameterString = "";
    int accessLevel = 0;

    public BetterEmbed getOutput(String userDiscordID, int accessLevel, UserAmiiboList amiiboList, ArrayList<String> parameters, EasterEgg egg) {

        AmiiboHuntAccess websiteData = new AmiiboHuntAccess();

        if (!websiteData.sendPostRequest("https://www.amiibohunt.com/api/discord/v1/getCollectionImageById", userDiscordID, null, null)) {
        	return new BetterEmbed().setError(websiteData.getLastError());
        }
        JSONObject data = new JSONObject(websiteData.getLastRequestString());
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
    
    public String getParameters() {
    	return parameterString;
    }
}
