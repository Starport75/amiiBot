package amiiBot;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class ListAmiiboCommand extends AbstractCommand{
	String description = "Lists all amiibo in the database";
	String command = "listAmiibo";

	public EmbedBuilder getOutput() {
String output = "";
		
		for (int i = 0; i < commandList.size(); i++) {
			output = output + "\n**" + commandToken + commandList.get(i).getCommand() + "**\n\t*" + commandList.get(i).getDescription() + "*";
		}
		EmbedBuilder embed = new EmbedBuilder()
				.setDescription(output);
				return embed;
	}

	public String getCommand() {
		return command;
	}

	public String getDescription() {
		return description;
	}
}
