package amiiBot;

import java.util.ArrayList;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class CompareAmiiboCommand extends AbstractCommand {
	String description = "Default description. Contact the creator if you are reading this message";
	String command = "compareAmiibo";
	String parameterString = "<Type/Series> <Other User> <All/Dupes>";
	int accessLevel = 0;

	public BetterEmbed getOutput(String userDiscordID, int accessLevel, UserAmiiboList amiiboList,
			ArrayList<String> parameters, EasterEgg egg, DiscordApi api, Server currServer, User currUser) {

		AmiiboHuntAccess websiteData = new AmiiboHuntAccess();

		if (!amiiboList.updateCollectionData(userDiscordID, websiteData)) {
			return new BetterEmbed().setError(websiteData.getLastError());
		}
		int minAmiibo;
		String output = "";

		UserAmiiboList secondaryAmiiboList = amiiboList;
		if (!secondaryAmiiboList.updateCollectionData("277092431814590464", websiteData)) {
			return new BetterEmbed().setError(websiteData.getLastError());
		}

		int seriesMin;
		int seriesMax;
		String typeName;

		if (parameters.size() < 2) {
			return new BetterEmbed().setError(
					"Error: Not all parameters defined. Command structure is !" + command + " " + parameterString);
		}

		if (parameters.get(2).equals("All")) {
			minAmiibo = 1;
			output = output + "*Listing amiibo that you own that " + "USER" + " does not:*";
		} else if (parameters.get(2).equals("Dupes")) {
			minAmiibo = 2;
			output = output + "*Listing amiibo that you own dupes of that " + "USER"
					+ " is missing from their collection:";
		} else {
			return new BetterEmbed()
					.setError("Error: Parameter \"" + parameters.get(1) + "\" not recognized as <All/Dupes>");
		}

		int typeIndex = amiiboList.getTypeList().indexOf(parameters.get(0));

		if (typeIndex == -1) {
			int seriesIndex;
			if (amiiboList.seriesToTypeIndex(parameters.get(0)) != -1) {
				String currType = amiiboList.getTypeList().get(amiiboList.seriesToTypeIndex(parameters.get(0)));
				seriesIndex = amiiboList.getSeriesList(currType).indexOf(parameters.get(0));
				typeIndex = amiiboList.seriesToTypeIndex(parameters.get(0));
				typeName = amiiboList.getTypeList().get(typeIndex);
				seriesMin = seriesIndex;
				seriesMax = seriesIndex + 1;
			} else {
				return new BetterEmbed().setError("Error: Parameter \"" + parameters.get(0) + "\" was not recognized!");
			}
		} else {
			typeName = amiiboList.getTypeList().get(typeIndex);
			seriesMin = 0;
			seriesMax = amiiboList.getNumOfSeries(typeName);
		}

		// outputs the type of amiibo listed
		output = output + "***" + amiiboList.getTypeList().get(typeIndex) + ":***\n";

		for (int seriesIndex = seriesMin; seriesIndex < seriesMax; seriesIndex++) {
			String seriesName = amiiboList.getSeriesList(typeName).get(seriesIndex);

			// outputs the current series being listed
			output = output + "\n**" + amiiboList.getSeriesAt(seriesIndex, typeName) + ":** ";

			// outputs the number in the current series being listed
			output = output + "*(";
			output = output + (amiiboList.getNumOfAmiibo(seriesName) - amiiboList.getNumCollectedInSeries(seriesName));
			output = output + "/" + amiiboList.getNumOfAmiibo(seriesName) + ")*\n";

			ArrayList<String> collectionList = new ArrayList<String>();

			for (int amiiboIndex = 0; amiiboIndex < amiiboList.getNumOfAmiibo(seriesName); amiiboIndex++) {

				// adds the amiibo to collectionList
				if (amiiboList.getAmiiboList(seriesName).get(amiiboIndex).getNumObtained() >= minAmiibo
						&& secondaryAmiiboList.getAmiiboList(seriesName).get(amiiboIndex).getNumObtained() == 0) {
					collectionList.add(amiiboList.getAmiiboList(seriesName).get(amiiboIndex).getName());
				}
			}

			// Lists all of the amiibo in collectionList nicely with commas and "and"s
			if (collectionList.size() > 1) {

				int i;

				for (i = 0; i < collectionList.size() - 1; i++) {
					output = output + collectionList.get(i);

					if (collectionList.size() > 2) {
						output = output + ", ";
					}
				}

				output = output + " and " + collectionList.get(i);

			} else if (collectionList.size() == 1) {

				output = output + collectionList.get(0);
			}

			output = output + "\n";

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
}