package amiiBot;

import java.util.ArrayList;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class ShowInfoCommand extends AbstractCommand {
	String description = "Default description. Contact the creator if you are reading this message";
	String command = "showInfo";
	int length = -1;

	public EmbedBuilder getOutput(String userDiscordID, UserAmiiboList amiiboList, ArrayList<String> parameters) {

		if (parameters.size() < 1) {
			EmbedBuilder embed = new EmbedBuilder().setDescription(
					"Error: Not all parameters defined. Command structure is !showInfo <amiibo Name> <Series (if needed)>");
			return embed;
		}

		String amiiboName = "";
		String seriesName = "";

		if (parameters.size() == 1) {
			Amiibo tempAmiibo = amiiboList.amiiboNameLookup(parameters.get(0));
			if (tempAmiibo != null) {
				seriesName = tempAmiibo.getSeries();
				amiiboName = tempAmiibo.getName();
			} else {
				EmbedBuilder embed = new EmbedBuilder().setDescription(
						"Error: Parameter \"" + parameters.get(0) + "\" was not recognized as <amiibo Name>!");
				return embed;
			}
		}

		if (parameters.size() >= 2) {

			if (!amiiboList.doesSeriesExist(parameters.get(1))) {
				EmbedBuilder embed = new EmbedBuilder().setDescription(
						"Error: Parameter \"" + parameters.get(1) + "\" was not recognized as <series>!");
				return embed;
			}

			if (!amiiboList.isInSeries(parameters.get(0), parameters.get(1))) {
				EmbedBuilder embed = new EmbedBuilder().setDescription(
						"Error: Parameter \"" + parameters.get(0) + "\" was not recognized as <amiibo Name>!");
				return embed;
			}

			amiiboName = parameters.get(0);
			seriesName = parameters.get(1);

		}

		amiiboList.updateCollectionData(userDiscordID);
		Amiibo currAmiibo = amiiboList.getAmiibo(amiiboName, seriesName);
		currAmiibo.updateIndividualFigureData();

		// currAmiibo.updateCollectionData(userDiscordID);
		String output = "";
		output = output + "**" +currAmiibo.getName() + "**\n";

		EmbedBuilder embed = new EmbedBuilder().setDescription(output).setImage(currAmiibo.getImage())
				.addField("Release Dates:",
						"🇯🇵: " + currAmiibo.getReleaseJP() + "\n🇺🇸: " + currAmiibo.getReleaseNA() + "\n🇪🇺: "
								+ currAmiibo.getReleaseEU() + "\n🇦🇺: " + currAmiibo.getReleaseAU())
				.addField("Completed & Sold Prices", "*based on eBay sales over the last month*")
				.addInlineField("Average Price NiB",
						"$" + currAmiibo.getNewPriceCompletedNA() + "\n£" + currAmiibo.getNewPriceCompletedUK())
				.addInlineField("Average Price OoB",
						"$" + currAmiibo.getUsedPriceCompletedNA() + "\n£" + currAmiibo.getUsedPriceCompletedUK())
				.addField("Current Listed Prices", "*based on eBay listings over the last month*")
				.addInlineField("Average Price NiB",
						"$" + currAmiibo.getNewPriceListedNA() + "\n£" + currAmiibo.getNewPriceListedUK())
				.addInlineField("Average Price OoB",
						"$" + currAmiibo.getUsedPriceListedNA() + "\n£" + currAmiibo.getUsedPriceListedUK());
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