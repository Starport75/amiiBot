package amiiBot;

import java.util.ArrayList;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class ListSeriesCommand extends AbstractCommand {
	String description = "Lists all the amiibo series within the specified type";
	String command = "listSeries";
	String parameterString = "<type>";
	int accessLevel = 0;

	public BetterEmbed getOutput(String userDiscordID, int accessLevel, UserAmiiboList amiiboList,
			ArrayList<String> parameters, EasterEgg egg, DiscordApi api, Server currServer, User currUser) {

		if (parameters.size() < 1) {
			return new BetterEmbed().setError(
					"Error: Not all parameters defined. Command structure is !" + command + " " + parameterString);
		}

		if (amiiboList.getTypeIndex(parameters.get(0)) == -1) {
			return new BetterEmbed().setError("Error: Parameter \"" + parameters.get(0) + "\" was not recognized!");
		}

		String output = "**amiibo " + capitalize(parameters.get(0)) + " Series:**";

		ArrayList<String> currSeriesList = amiiboList
				.getSeriesList(amiiboList.getTypeAt(amiiboList.getTypeIndex(parameters.get(0))));

		for (int listIndex = 0; listIndex < currSeriesList.size(); listIndex++) {
			output = output + "\n*" + capitalize(currSeriesList.get(listIndex)) + "*";
		}

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

	public String capitalize(String word) {
		String cOutput = ("" + word.charAt(0)).toUpperCase();

		for (int i = 1; i < word.length(); i++) {
			if (word.charAt(i - 1) == ' ' || word.charAt(i - 1) == '(' || word.charAt(i -1) == '-') {
				cOutput = cOutput + ("" + word.charAt(i)).toUpperCase();
			} else {
				cOutput = cOutput + word.charAt(i);
			}
		}
		return cOutput;
	}
}
