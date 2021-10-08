package amiiBot;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class ListAmiiboCommand extends AbstractCommand {
	String description = "Lists all amiibo in the database";
	String command = "listAmiibo";

	public EmbedBuilder getOutput(MasterList masterList) {
		String output = "";

		for (int i = 0; i < masterList.getMasterList().length; i++) {
			output = output + "**" + TypeEnum.intToType(i).toString() + ":**\n";
			for (int j = 0; j < masterList.getMasterList()[i].length; j++) {
				output = output + SeriesEnum.intToSeries(j).toString() + " Series:\n";
				for (int k = 0; k < masterList.getMasterList()[i][j].size(); k++) {
					output = output + masterList.getMasterList()[i][j].get(k).getName();
					if (k < masterList.getMasterList()[i][j].size() - 2) {
						output = output + ", ";
					} else if (k == masterList.getMasterList()[i][j].size() - 2) {
						if (masterList.getMasterList()[i][j].size() != 2) {
							output = output +  ",";
						}
						output = output + " and ";
					}
				}
				output = output + "\n";
			}
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
