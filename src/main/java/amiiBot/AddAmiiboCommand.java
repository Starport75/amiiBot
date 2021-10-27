package amiiBot;

import java.util.ArrayList;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.json.JSONObject;

public class AddAmiiboCommand extends AbstractCommand{
	 String description = "Default description. Contact the creator if you are reading this message";
	    String command = "addAmiibo";
	    int length = -1;

	    public EmbedBuilder getOutput(String userDiscordID, UserAmiiboList amiiboList, ArrayList<String> parameters) {
	    	
	    	AmiiboHuntAccess websiteData = new AmiiboHuntAccess();
	    	String seriesName;
	    	String amiiboName;
	    	String isBoxed;

	    	
	    	if (parameters.size() < 1) {
				EmbedBuilder embed = new EmbedBuilder().setDescription(
						"Error: Not all parameters defined. Command structure is !addAmiibo <amiibo Name> <Series Name> <NiB/OoB>");
				return embed;
			}
	    	
	    	if (!amiiboList.doesSeriesExist(parameters.get(1))) {
				EmbedBuilder embed = new EmbedBuilder().setDescription(
						"Error: Parameter \"" + parameters.get(1) + "\" was not recognized as <series>!");
				return embed;
			}

			if (!amiiboList.isInSeries(parameters.get(0), parameters.get(1))) {
				EmbedBuilder embed = new EmbedBuilder().setDescription(
						"Error: Parameter \"" + parameters.get(0) + "\" was not recognized as <amiibo Name>!");
				return embed;
			}

			amiiboName = parameters.get(0);
			seriesName = parameters.get(1);
	    	
	    	Amiibo currAmiibo = amiiboList.getAmiibo(amiiboName, seriesName);
	    	
	    	if (parameters.get(2).equals("NiB")) {
	    		isBoxed = "true";
	    	} else if (parameters.get(2).equals("OoB")){
	    		isBoxed = "false";
	    	} else {
	    		EmbedBuilder embed = new EmbedBuilder().setDescription(
						"Error: Parameter \"" + parameters.get(2) + "\" was not recognized as <NiB/OoB>!");
				return embed;
	    	}

	    	JSONObject data = new JSONObject(websiteData
					.sendPostRequest("https://www.amiibohunt.com/api/discord/v1/addAmiiboToCollection", userDiscordID, "" + currAmiibo.getAmiiboID(), isBoxed));
	    	
	        EmbedBuilder embed = new EmbedBuilder()
	                .setDescription(data.toString());
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
