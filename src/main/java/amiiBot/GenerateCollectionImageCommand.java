package amiiBot;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.json.JSONObject;

import java.util.ArrayList;

public class GenerateCollectionImageCommand extends AbstractCommand {
	String description = "Generates an image of all the figures you have collected";
	String command = "generateImage";
	String parameterString = "<Figures/Cards>, (Cards)<Card Series>, (Cards)<Wishlist/Unobtained/Duplicates>";
	int accessLevel = 0;

	public BetterEmbed getOutput(String userDiscordID, int accessLevel, UserAmiiboList amiiboList,
			ArrayList<String> parameters, EasterEgg egg, DiscordApi api, Server currServer, User currUser) {

		AmiiboHuntAccess websiteData = new AmiiboHuntAccess();

		if (parameters.size() < 1) {
			return new BetterEmbed().setError(
					"Error: Not all parameters defined. Command structure is !" + command + " " + parameterString);
		}

		if (parameters.get(0).equals("figures")) {
			if (!websiteData.sendPostRequest("https://www.amiibohunt.com/api/discord/v1/getCollectionImageById",
					userDiscordID, null, null)) {
				return new BetterEmbed().setError(websiteData.getLastError());
			}
		} else if (parameters.get(0).equals("cards")) {

			if (parameters.size() < 2) {
				return new BetterEmbed().setError(
						"Error: Not all parameters defined. Command structure is !" + command + " " + parameterString);
			}
			
			String[] name = new String[] { "series", ""};
			String[] value = new String[] { "", "true"};

			String[] seriesNames = { "animal crossing (series 1)", "animal crossing (series 2)",
					"animal crossing (series 3)", "animal crossing (series 4)", "animal crossing (series 5)" };

			int seriesIndex = -1;

			for (int i = 0; i < seriesNames.length; i++) {
				if (seriesNames[i].equals(parameters.get(1))) {
					seriesIndex = i;
				}
			}

			if (seriesIndex == -1) {
				return new BetterEmbed().setError("Error: Parameter \"" + parameters.get(1)
						+ "\" was not recognized as <Series Name>. Please note that currently, this command only works with Animal Crossing series 1 through 5");
			}
			
			value[0] = "" + (seriesIndex + 1);
						
			if (parameters.size() > 2) {
				if (parameters.get(2).equals("wishlist")) {
					name[1] = "wishlistOnly";
				} else if (parameters.get(2).equals("unobtained")) {
					name[1] = "unownedOnly";
				} else if (parameters.get(2).equals("duplicates")) {
					name[1] = "duplicatesOnly";
				} else {
					return new BetterEmbed()
							.setError("Error: Parameter \"" + parameters.get(2) + "\" was not recognized as <Wishlist/Unobtained/Duplicates>.");
				}
			}
			
			if (!websiteData.sendPostRequest("https://www.amiibohunt.com/api/discord/v1/getCollectionImageCardSeries",
					userDiscordID, name, value)) {
				return new BetterEmbed().setError(websiteData.getLastError());
			}

		} else {
			return new BetterEmbed()
					.setError("Error: Parameter \"" + parameters.get(0) + "\" was not recognized as <Figures/Cards>.");
		}

		JSONObject data = new JSONObject(websiteData.getLastRequestString());
		String output = data.get("val").toString();

		BetterEmbed embed = new BetterEmbed().setImage(output);
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
