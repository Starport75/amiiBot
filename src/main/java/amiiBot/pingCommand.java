package amiiBot;

import org.javacord.api.entity.message.embed.EmbedBuilder;

class PingCommand extends AbstractCommand {
	String command = "ping";
	String description = "A test command";

	public EmbedBuilder getOutput(MasterList masterList) {
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
