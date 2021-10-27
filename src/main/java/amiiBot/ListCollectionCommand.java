package amiiBot;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.util.ArrayList;

public class ListCollectionCommand extends AbstractCommand {
	String description = "Lists amiibo for the user";
	String command = "listAmiibo";

	public EmbedBuilder getOutput(String userDiscordID, UserAmiiboList amiiboList, ArrayList<String> parameters) {

		amiiboList.updateCollectionData(userDiscordID);

		boolean listAll = (parameters.size() < 2);
		System.out.println("Parameters size = " + parameters.size());
		boolean listObtained = true;

		String output = "";

		int seriesMin;
		int seriesMax;
		String typeName;

		if (parameters.size() < 1) {
			EmbedBuilder embed = new EmbedBuilder()
					.setDescription("Error: No parameters defined. Command structure is !listCollection <Type/Series>");
			return embed;
		}

		if (!listAll) {
			if (parameters.get(1).equals("Obtained")) {
				listObtained = true;
			} else if (parameters.get(1).equals("Not Obtained")) {
				listObtained = false;
			} else {
				EmbedBuilder embed = new EmbedBuilder().setDescription(
						"Error: Parameter \"" + parameters.get(1) + "\" not recognized as <Obtained/Not Obtained>");
				return embed;
			}
		}

		int typeIndex = amiiboList.getTypeList().indexOf(parameters.get(0));

		if (typeIndex == -1) {
			int seriesIndex;
			if (amiiboList.seriesToTypeIndex(parameters.get(0)) != -1) {
				String currType = amiiboList.getTypeList().get(amiiboList.seriesToTypeIndex(parameters.get(0)));
				System.out.println("currType = " + currType);
				seriesIndex = amiiboList.getSeriesList(currType).indexOf(parameters.get(0));
				typeIndex = amiiboList.seriesToTypeIndex(parameters.get(0));
				typeName = amiiboList.getTypeList().get(typeIndex);
				System.out.println("typeIndex = " + typeIndex);
				System.out.println("seriesIndex = " + seriesIndex);
				seriesMin = seriesIndex;
				seriesMax = seriesIndex + 1;
			} else {

				EmbedBuilder embed = new EmbedBuilder()
						.setDescription("Error: Parameter \"" + parameters.get(0) + "\" was not recognized!");
				return embed;
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
		EmbedBuilder embed = new EmbedBuilder().setDescription(output);
		updateLength(output);
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
