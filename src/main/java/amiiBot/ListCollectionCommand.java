package amiiBot;

import java.util.ArrayList;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class ListCollectionCommand extends AbstractCommand {
	String description = "Lists all amiibo in the user's collection";
	String command = "listCollection";

	public EmbedBuilder getOutput(String userDiscordID, ArrayList<String> parameters) {

		String output = "";
		UserAmiiboList userCollection = new UserAmiiboList(userDiscordID);

		if (parameters.size() < 1) {
			EmbedBuilder embed = new EmbedBuilder().setDescription(
					"Error: No parameters defined. Command structure is !listCollection <Type/Series>");
			return embed;
		}

		int typeIndex = userCollection.getTypeList().indexOf(parameters.get(0));
		if (typeIndex == -1) {
			EmbedBuilder embed = new EmbedBuilder()
					.setDescription("Error: Parameter \"" + parameters.get(0) + "\" was not recognized!");
			return embed;
		}
		
		boolean uncollected = (parameters.size() > 1 &&  parameters.get(1).equals("Missing"));

		String typeName = userCollection.getTypeList().get(typeIndex);

		// outputs the type of amiibo listed
		output = output + "***" + userCollection.getTypeList().get(typeIndex) + ":***\n";

		for (int seriesIndex = 0; seriesIndex < userCollection.getNumOfSeries(typeName); seriesIndex++) {
			String seriesName = userCollection.getSeriesList(typeName).get(seriesIndex);
			// outputs the current series being listed
			output = output + "\n**" + userCollection.getSeriesAt(seriesIndex, typeName) + ":** ";
			// outputs the number in the current series being listed
			output = output + "*(";
			if (uncollected) {
				output = output + (userCollection.getNumOfAmiibo(seriesName) - userCollection.getNumCollectedInSeries(seriesName));
			} else {
				output = output + userCollection.getNumCollectedInSeries(seriesName);
			}
			output = output + "/" + userCollection.getNumOfAmiibo(seriesName) + ")*\n";

			ArrayList<String> collectionList = new ArrayList<String>();
			
			for (int amiiboIndex = 0; amiiboIndex < userCollection.getNumOfAmiibo(seriesName); amiiboIndex++) {
				if (userCollection.getAmiiboList(seriesName).get(amiiboIndex).getNumObtained() > 0) {

					// outputs the name of the amiibo
					collectionList.add(userCollection.getAmiiboList(seriesName).get(amiiboIndex).getName());
					
					int i;
					for (i = 0; i < collectionList.size() - 2; i++) {
						output = output + collectionList.get(i) + ", ";
					}
					//output = output + collectionList.get(i + 1) + ", and " + collectionList.get(i + 1);
				}
			}

			output = output + "\n";
		}
		System.out.println(output);
		EmbedBuilder embed = new EmbedBuilder().setDescription(output);
		return embed;
	}

	public String getCommand() {
		return command;
	}

	public String getDescription() {
		return description;
	}
}
