package amiiBot;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class ListAmiiboCommand extends AbstractCommand {
	String description = "Lists all amiibo in the database";
	String command = "listAmiibo";

	public EmbedBuilder getOutput(MasterList masterList, String restOfCommand) {

		// System.out.println(restOfCommand);
		// System.out.println(restOfCommand.substring(restOfCommand.indexOf('<') + 1,
		// restOfCommand.indexOf('>')));

		String output = "";

		int typeInt = 0;

		output = output + "***" + TypeEnum.intToType(typeInt).toString() + ":***\n";
		for (int j = 0; j < masterList.getMasterList()[typeInt].length; j++) {
			output = output + "\n**" + SeriesEnum.intToSeries(j, TypeEnum.intToType(typeInt)).toString() + ":** ";
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
