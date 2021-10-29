package amiiBot;

import java.util.ArrayList;

import org.json.JSONObject;

public class AddAmiiboCommand extends AbstractCommand {
	String description = "Default description. Contact the creator if you are reading this message";
	String command = "addAmiibo";

	public BetterEmbed getOutput(String userDiscordID, UserAmiiboList amiiboList, ArrayList<String> parameters, EasterEgg egg) {

		AmiiboHuntAccess websiteData = new AmiiboHuntAccess();
		String seriesName;
		String amiiboName;
		String isBoxed;

		if (parameters.size() < 1) {
			return new BetterEmbed().setError(
					"Error: Not all parameters defined. Command structure is !addAmiibo <amiibo Name> <Series Name> <NiB/OoB>");
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

		if (parameters.get(2).equals("NiB")) {
			isBoxed = "true";
		} else if (parameters.get(2).equals("OoB")) {
			isBoxed = "false";
		} else {
			return new BetterEmbed().setError("Error: Parameter \"" + parameters.get(2) + "\" was not recognized as <NiB/OoB>!");
		}

		JSONObject data = new JSONObject(
				websiteData.sendPostRequest("https://www.amiibohunt.com/api/discord/v1/addAmiiboToCollection",
						userDiscordID, "" + currAmiibo.getAmiiboID(), isBoxed));

		BetterEmbed embed = new BetterEmbed().setDescription(data.toString());
		return embed;
	}

	public String getCommand() {
		return command;
	}

	public String getDescription() {
		return description;
	}
}