package amiiBot;

import java.util.ArrayList;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class ListSeriesCommand extends AbstractCommand{
	String description = "Default description. Contact the creator if you are reading this message";
	String command = "listSeries";

	public EmbedBuilder getOutput(String userDiscordID, ArrayList<String> parameters) {
		UserAmiiboList userCollection = new UserAmiiboList(userDiscordID);

		if (parameters.size() < 1) {
			EmbedBuilder embed = new EmbedBuilder().setDescription(
					"Error: No parameters defined. Command structure is !listSeries <Type>");
			return embed;
		}
		
		if (userCollection.getTypeIndex(parameters.get(0)) == -1) {
			EmbedBuilder embed = new EmbedBuilder()
					.setDescription("Error: Parameter \"" + parameters.get(0) + "\" was not recognized!");
			return embed;
		}
		
		EmbedBuilder embed = new EmbedBuilder()
				.setDescription(userCollection.getSeriesList(userCollection.getTypeAt(userCollection.getTypeIndex(parameters.get(0)))).toString());
		updateLength(userCollection.getSeriesList(userCollection.getTypeAt(userCollection.getTypeIndex(parameters.get(0)))).toString());
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
