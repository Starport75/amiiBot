package amiiBot;

import java.util.ArrayList;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.json.JSONObject;

public class RemoveAmiiboCommand extends AbstractCommand {
	String description = "Removes **all** instances of an amiibo in your amiiboHunt collection";
	String command = "removeAmiibo";
	String parameterString = "<amiibo name> <series name>";
	int accessLevel = 0;

	public BetterEmbed getOutput(String userDiscordID, int accessLevel, UserAmiiboList amiiboList,
			ArrayList<String> parameters, EasterEgg egg, DiscordApi api, Server currServer, User currUser) {

		AmiiboHuntAccess websiteData = new AmiiboHuntAccess();
		String seriesName;
		String amiiboName;

		if (parameters.size() < 2) {
			return new BetterEmbed().setError(
					"Error: Not all parameters defined. Command structure is !" + command + " " + parameterString);
		}

		if (!amiiboList.doesSeriesExist(parameters.get(1))) {
			return new BetterEmbed()
					.setError("Error: Parameter \"" + parameters.get(1) + "\" was not recognized as <series>!");
		}

		if (!amiiboList.isInSeries(parameters.get(0), parameters.get(1))) {
			return new BetterEmbed()
					.setError("Error: Parameter \"" + parameters.get(0) + "\" was not recognized as <amiibo Name>!");
		}

		amiiboName = parameters.get(0);
		seriesName = parameters.get(1);

		Amiibo currAmiibo = amiiboList.getAmiibo(amiiboName, seriesName);

		if (!websiteData.sendPostRequest("https://www.amiibohunt.com/api/discord/v1/removeAmiiboFromCollection",
				userDiscordID, new String[] {"amiibo_id"}, new String[]{"" + currAmiibo.getAmiiboID()})) {
			return new BetterEmbed().setError(websiteData.getLastError());
		}
		JSONObject data = new JSONObject(websiteData.getLastRequestString());

		System.out.println("Data sent: " + "https://www.amiibohunt.com/api/discord/v1/removeAmiiboFromCollection" + ", "
				+ userDiscordID + ", " + currAmiibo.getAmiiboID() + ", ");
		System.out.println("Server Response: " + data.toString());

		String output = ("Successfully removed " + data.get("amiibo_name") + " from your collection!");

		BetterEmbed embed = new BetterEmbed().setDescription(output);
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