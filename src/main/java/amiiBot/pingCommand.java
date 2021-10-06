package amiiBot;

import org.javacord.api.entity.message.embed.EmbedBuilder;

class pingCommand extends AbstractCommand {
	String command = "ping";
	String description = "A test command";

	public EmbedBuilder getOutput() {
		EmbedBuilder embed = new EmbedBuilder()
				.setDescription("Pong!");
		return embed;
	}

	public String getCommand() {
		return command;
	}

	public String getDescription() {
		return description;
	}
}
