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
		
		int typeInt = masterList.getTypeList().indexOf(parameters.get(0));
		if (typeInt == -1) {
			EmbedBuilder embed = new EmbedBuilder()
					.setDescription("Error: Parameter \"" + parameters.get(0) + "\" was not recognized!");
			return embed;
		}

		output = output + "***" + masterList.getTypeList().get(typeInt) + ":***\n";

		int min = 0;
		int max = masterList.getMasterList()[typeInt].length;
		if (parameters.size() >= 2) {
		int tempSeriesIndex = masterList.getSeriesList().indexOf(parameters.get(1));
			System.out.println(tempSeriesIndex);
			System.out.println(masterList.getSeriesList());
			if (tempSeriesIndex == -1) {
				EmbedBuilder embed = new EmbedBuilder()
						.setDescription("Error: Parameter \"" + parameters.get(1) + "\" was not recognized! #2");
				return embed;
			} else {
				min = tempSeriesIndex;
				max = min + 1;
			}
		}
		
		for (int j = min; j < max ; j++) {

			output = output + "\n**" + masterList.getSeriesList().get(1) + ":** ";
			output = output + "*(" + masterList.getMasterList()[typeInt][j].size() + ")*\n";

			for (int k = 0; k < masterList.getMasterList()[typeInt][j].size(); k++) {

				output = output + masterList.getMasterList()[typeInt][j].get(k).getName();

				if (k < masterList.getMasterList()[typeInt][j].size() - 2) {

					output = output + ", ";

				} else if (k == masterList.getMasterList()[typeInt][j].size() - 2) {
					if (masterList.getMasterList()[typeInt][j].size() != 2) {

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
