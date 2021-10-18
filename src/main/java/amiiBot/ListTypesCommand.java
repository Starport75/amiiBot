package amiiBot;

import java.util.ArrayList;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class ListTypesCommand extends AbstractCommand{
	String description = "Default description. Contact the creator if you are reading this message";
	String command = "listTypes";

	public EmbedBuilder getOutput(String userDiscordID, ArrayList<String> parameters) {
		UserAmiiboList userCollection = new UserAmiiboList(userDiscordID);

		EmbedBuilder embed = new EmbedBuilder()
				.setDescription(userCollection.getTypeList().toString());
		return embed;
	}

	public String getCommand() {
		return command;
	}

	public String getDescription() {
		return description;
	}

}
