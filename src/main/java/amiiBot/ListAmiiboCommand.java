package amiiBot;

import java.util.ArrayList;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class ListAmiiboCommand extends AbstractCommand {
	String description = "Lists all amiibo in the database";
	String command = "listAmiibo";

	public EmbedBuilder getOutput(MasterList masterList, String userDiscordID, ArrayList<String> parameters) {

		String output = "";

		if (parameters.size() < 1) {
			EmbedBuilder embed = new EmbedBuilder()
					.setDescription("Error: No parameters defined. Command structure is !listAmiibo <Type> [optional]<Series>");
			return embed;
		}
		
		int typeIndex = masterList.getTypeList().indexOf(parameters.get(0));
		if (typeIndex == -1) {
			EmbedBuilder embed = new EmbedBuilder()
					.setDescription("Error: Parameter \"" + parameters.get(0) + "\" was not recognized!");
			return embed;
		}
		String typeName = masterList.getTypeList().get(typeIndex);
		
		//outputs the type of amiibo listed
		output = output + "***" + masterList.getTypeList().get(typeIndex) + ":***\n";

		for (int seriesIndex = 0; seriesIndex < masterList.getNumOfSeries(typeName); seriesIndex++) {
			String seriesName = masterList.getSeriesList(typeName).get(seriesIndex);
			//outputs the current series being listed
			output = output + "\n**" + masterList.getSeriesAt(seriesIndex, typeName) + ":** ";
			//outputs the number in the current series being listed
			output = output + "*(" + masterList.getNumOfAmiibo(seriesName) + ")*\n";

			for (int amiiboIndex = 0; amiiboIndex < masterList.getNumOfAmiibo(seriesName); amiiboIndex++) {
				//outputs the name of the amiibo
				output = output + masterList.getAmiiboList(seriesName).get(amiiboIndex).getName();

				if (amiiboIndex < masterList.getMasterList().get(typeIndex).get(seriesIndex).size() - 2) {

					output = output + ", ";

				} else if (amiiboIndex == masterList.getMasterList().get(typeIndex).get(seriesIndex).size() - 2) {
					if (masterList.getMasterList().get(typeIndex).get(seriesIndex).size() != 2) {

						output = output + ",";

					}

					output = output + " and ";
				}
			}

			output = output + "\n";
		}

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
