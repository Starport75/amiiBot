package amiiBot;

import org.javacord.api.entity.message.embed.EmbedBuilder;

abstract class AbstractCommand {
	String description = "Default description. Contact the creator if you are reading this message";
	String command;

	public EmbedBuilder getOutput(MasterList masterList, String restOfCommand) {
		EmbedBuilder embed = new EmbedBuilder()
				.setDescription("This command has not been set up. Please contact the creator");
		return embed;
	}

	public String getCommand() {
		return command;
	}

	public String getDescription() {
		return description;
	}
}
