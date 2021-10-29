package amiiBot;

import java.awt.Color;
import java.util.ArrayList;

public class ShowInfoCommand extends AbstractCommand {
	String description = "Default description. Contact the creator if you are reading this message";
	String command = "showInfo";
	int length = -1;

	public BetterEmbed getOutput(String userDiscordID, UserAmiiboList amiiboList, ArrayList<String> parameters, EasterEgg egg) {

		if (parameters.size() < 1) {
			return new BetterEmbed().setError("Error: Not all parameters defined. Command structure is !showInfo <amiibo Name> <Series (if needed)>");
		}

		String amiiboName = "";
		String seriesName = "";

		if (parameters.size() == 1) {
			Amiibo tempAmiibo = amiiboList.amiiboNameLookup(parameters.get(0));
			if (tempAmiibo != null) {
				seriesName = tempAmiibo.getSeries();
				amiiboName = tempAmiibo.getName();
			} else {
				return new BetterEmbed().setError("Error: Parameter \"" + parameters.get(0) + "\" was not recognized as <amiibo Name>!");
			}
		}

		if (parameters.size() >= 2) {

			if (!amiiboList.doesSeriesExist(parameters.get(1))) {
				return new BetterEmbed().setError("Error: Parameter \"" + parameters.get(1) + "\" was not recognized as <series>!");
			}

			if (!amiiboList.isInSeries(parameters.get(0), parameters.get(1))) {
				return new BetterEmbed().setError("Error: Parameter \"" + parameters.get(0) + "\" was not recognized as <amiibo Name>!");
			}

			amiiboName = parameters.get(0);
			seriesName = parameters.get(1);

		}

		amiiboList.updateCollectionData(userDiscordID);
		Amiibo currAmiibo = amiiboList.getAmiibo(amiiboName, seriesName);
		currAmiibo.updateIndividualFigureData();

		String output = "";

		BetterEmbed embed = new BetterEmbed().setTitle(currAmiibo.getName()).setImage(currAmiibo.getImage(egg, userDiscordID))
				.addField("Release Dates:",
						"🇯🇵: " + currAmiibo.getReleaseJP() + "\n🇺🇸: " + currAmiibo.getReleaseNA() + "\n🇪🇺: "
								+ currAmiibo.getReleaseEU() + "\n🇦🇺: " + currAmiibo.getReleaseAU())
				.addField("\u200b", "**Average Completed & Sold Prices** *(est.)*")
				.addInlineField("Average Price NiB",
						currAmiibo.getFormattedNewPriceCompletedNA() + "\n"
								+ currAmiibo.getFormattedNewPriceCompletedUK())
				.addInlineField("Average Price OoB",
						currAmiibo.getFormattedUsedPriceCompletedNA() + "\n"
								+ currAmiibo.getFormattedUsedPriceCompletedUK())
				.addField("\u200b", "**Average Current Listed Prices** *(est.)*")
				.addInlineField("Average Price NiB",
						currAmiibo.getFormattedNewPriceListedNA() + "\n"
								+ currAmiibo.getFormattedNewPriceListedUK())
				.addInlineField("Average Price OoB",
						currAmiibo.getFormattedUsedPriceListedNA() + "\n"
								+ currAmiibo.getFormattedUsedPriceListedUK())
				.setColor(currAmiibo.getColor());
		return embed;
	}

	public String getCommand() {
		return command;
	}

	public String getDescription() {
		return description;
	}
}