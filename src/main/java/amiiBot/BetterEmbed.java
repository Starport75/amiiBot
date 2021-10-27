package amiiBot;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class BetterEmbed {

	EmbedBuilder embed;
	int outputLength = 0;
	Color color = Color.blue;
	int maxLength = 5000;

	public BetterEmbed() {
		embed = new EmbedBuilder();
	}

	public EmbedBuilder getEmbed() {
		if (outputLength < maxLength) {
			embed.setColor(color);
			return embed;
		} else {
			String output = "Error: The output exceeds the max character limit by " + (outputLength - maxLength)
					+ " characters. Please refine your parameters and try again.";
			outputLength = output.length();
			return setError(output).getEmbed();
		}

	}

	public BetterEmbed setError(String errorMessage) {
		setColor(Color.red);
		embed.setDescription(errorMessage);
		return this;
	}

	public BetterEmbed setDescription(String description) {
		outputLength = outputLength + description.length();
		embed.setDescription(description);
		return this;
	}

	public BetterEmbed setImage(String image) {
		embed.setImage(image);
		return this;
	}

	public BetterEmbed setTitle(String title) {
		outputLength = outputLength + title.length();
		embed.setTitle(title);
		return this;
	}

	public BetterEmbed addField(String name, String value) {
		outputLength = outputLength + name.length() + value.length();
		embed.addField(name, value);
		return this;
	}

	public BetterEmbed addInlineField(String name, String value) {
		outputLength = outputLength + name.length() + value.length();
		embed.addInlineField(name, value);
		return this;
	}

	public BetterEmbed setColor(Color nColor) {
		color = nColor;
		return this;
	}
}
