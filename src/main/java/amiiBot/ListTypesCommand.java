package amiiBot;

import java.util.ArrayList;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class ListTypesCommand extends AbstractCommand{
	String description = "Default description. Contact the creator if you are reading this message";
	String command = "listTypes";

	public EmbedBuilder getOutput(MasterList masterList, String userDiscordID, ArrayList<String> parameters) {
		EmbedBuilder embed = new EmbedBuilder()
				.setDescription(masterList.getTypeList().toString());
		return embed;
	}

	public String getCommand() {
		return command;
	}

	public String getDescription() {
		return description;
	}

}
