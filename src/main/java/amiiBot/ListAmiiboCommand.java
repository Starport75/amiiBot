package amiiBot;

import java.util.ArrayList;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class ListAmiiboCommand extends AbstractCommand {
	String description = "Lists all the amiibo within the specified series or type";
	String command = "listAmiibo";
    String parameterString = "<type/series> (optional)<obtained/not obtained>";
    int accessLevel = 0;

	public BetterEmbed getOutput(String userDiscordID, int accessLevel, UserAmiiboList amiiboList,
			ArrayList<String> parameters, EasterEgg egg, DiscordApi api, Server currServer, User currUser) {

		AmiiboHuntAccess websiteData = new AmiiboHuntAccess();

		boolean hasAccount = amiiboList.updateCollectionData(userDiscordID, websiteData);		
		
		if (!amiiboList.updateCollectionData(userDiscordID, websiteData) && parameters.size() > 1) {
			return new BetterEmbed().setError(websiteData.getLastError());
		}
		boolean listAll = (parameters.size() < 2);
		boolean listObtained = true;

		String output = "";

		int seriesMin;
		int seriesMax;
		String typeName;

		if (parameters.size() < 1) {
			return new BetterEmbed().setError(
					"Error: Not all parameters defined. Command structure is !" + command + " " + parameterString);
		}

		if (!listAll) {
			if (!hasAccount) {
				return new BetterEmbed().setError(websiteData.getLastError());
			}
			if (parameters.get(1).equals("obtained")) {
				listObtained = true;
			} else if (parameters.get(1).equals("not obtained")) {
				listObtained = false;
			} else {
				return new BetterEmbed().setError("Error: Parameter \"" + parameters.get(1) + "\" not recognized as <Obtained/Not Obtained>");
			}
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
		output = output + "***" + capitalize(amiiboList.getTypeList().get(typeIndex)) + ":***\n";

		for (int seriesIndex = seriesMin; seriesIndex < seriesMax; seriesIndex++) {
			String seriesName = amiiboList.getSeriesList(typeName).get(seriesIndex);

			// outputs the current series being listed
			output = output + "\n**" + capitalize(amiiboList.getSeriesAt(seriesIndex, typeName)) + ":** ";

			// outputs the number in the current series being listed
			output = output + "*(";
			if (listAll) {
				output = output + amiiboList.getNumOfAmiibo(seriesName);
				output = output + ")*\n";
			} else if (listObtained) {
				output = output + amiiboList.getNumCollectedInSeries(seriesName);
				output = output + "/" + amiiboList.getNumOfAmiibo(seriesName) + ")*\n";

			} else {
				output = output
						+ (amiiboList.getNumOfAmiibo(seriesName) - amiiboList.getNumCollectedInSeries(seriesName));
				output = output + "/" + amiiboList.getNumOfAmiibo(seriesName) + ")*\n";
			}

			ArrayList<String> collectionList = new ArrayList<String>();

			for (int amiiboIndex = 0; amiiboIndex < amiiboList.getNumOfAmiibo(seriesName); amiiboIndex++) {
				
				// adds the amiibo to collectionList
				if (listAll || ((amiiboList.getAmiiboList(seriesName).get(amiiboIndex)
						.getNumObtained() == 0) != listObtained)) {
					collectionList.add(amiiboList.getAmiiboList(seriesName).get(amiiboIndex).getNameUppercase());
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
