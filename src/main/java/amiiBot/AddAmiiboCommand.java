package amiiBot;

import java.util.ArrayList;

import org.json.JSONObject;

public class AddAmiiboCommand extends AbstractCommand {
	String description = "Adds an amiibo to your amiiboHunt collection";
	String command = "addAmiibo";
	String parameterString = "<amiibo name> <series name> <NiB/OoB>";
	int accessLevel = 0;

	public BetterEmbed getOutput(String userDiscordID, int accessLevel, UserAmiiboList amiiboList,
			ArrayList<String> parameters, EasterEgg egg) {

		AmiiboHuntAccess websiteData = new AmiiboHuntAccess();
		String seriesName;
		String amiiboName;
		String isBoxed = null;

		if (parameters.size() < 3) {
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

		if (parameters.get(2).equals("nib")) {
			isBoxed = "true";
		} else if (!parameters.get(2).equals("oob")) {
			return new BetterEmbed()
					.setError("Error: Parameter \"" + parameters.get(2) + "\" was not recognized as <NiB/OoB>!");
		}

		if (!websiteData.sendPostRequest("https://www.amiibohunt.com/api/discord/v1/addAmiiboToCollection",
				userDiscordID, "" + currAmiibo.getAmiiboID(), isBoxed)) {
			return new BetterEmbed().setError(websiteData.getLastError());
		}
		JSONObject data = new JSONObject(websiteData.getLastRequestString());

		System.out.println("Data sent: " + "https://www.amiibohunt.com/api/discord/v1/addAmiiboToCollection" + ", "
				+ userDiscordID + ", " + currAmiibo.getAmiiboID() + ", " + isBoxed);
		System.out.println("Server Response: " + data.toString());

		String output = ("Successfully added 1 " + data.get("amiibo_name") + " to your collection! You now have "
				+ data.get("qty_owned") + " " + data.get("amiibo_name") + " amiibo!");

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