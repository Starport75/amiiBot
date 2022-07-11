package amiiBot;

import java.util.ArrayList;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class ShowInfoCommand extends AbstractCommand {
	String description = "Shows various information about the specified amiibo";
	String command = "showInfo";
	String parameterString = "<amiibo name> <series (if needed)>";
	int accessLevel = 0;

	public BetterEmbed getOutput(String userDiscordID, int accessLevel, UserAmiiboList amiiboList,
			ArrayList<String> parameters, EasterEgg egg, DiscordApi api, Server currServer, User currUser) {

		if (parameters.size() < 1) {
			return new BetterEmbed().setError(
					"Error: Not all parameters defined. Command structure is !" + command + " " + parameterString);
		}

		String amiiboName = "";
		String seriesName = "";

		if (parameters.size() == 1) {
			Amiibo tempAmiibo = amiiboList.amiiboNameLookup(parameters.get(0));
			if (tempAmiibo != null) {
				seriesName = tempAmiibo.getSeries();
				amiiboName = tempAmiibo.getName();
			} else {
				return new BetterEmbed().setError(
						"Error: Parameter \"" + parameters.get(0) + "\" was not recognized as <amiibo Name>!");
			}
		}

		if (parameters.size() >= 2) {

			if (!amiiboList.doesSeriesExist(parameters.get(1))) {
				return new BetterEmbed()
						.setError("Error: Parameter \"" + parameters.get(1) + "\" was not recognized as <series>!");
			}

			if (!amiiboList.isInSeries(parameters.get(0), parameters.get(1))) {
				return new BetterEmbed().setError(
						"Error: Parameter \"" + parameters.get(0) + "\" was not recognized as <amiibo Name>!");
			}

			amiiboName = parameters.get(0);
			seriesName = parameters.get(1);

		}

		Amiibo currAmiibo = amiiboList.getAmiibo(amiiboName, seriesName);
		currAmiibo.updateIndividualFigureData();

		String retailOutput = "";

		ArrayList<String[]> retailerData = currAmiibo.getRetailerList();
		int numWithStock = 0;

		if (retailerData.size() == 0) {
			retailOutput = "*No retailers found*";
		} else {
			for (int retailIndex = 0; retailIndex < retailerData.size(); retailIndex++) {
				if (retailerData.get(retailIndex)[0].equals("1")) {
					retailOutput = retailOutput + "[" + retailerData.get(retailIndex)[1] + "]("
							+ retailerData.get(retailIndex)[2] + ")\n";
					numWithStock++;
				}
			}

			if (numWithStock == 0) {
				retailOutput = "*No retailers with stock found*";
			}
		}

		BetterEmbed embed = new BetterEmbed().setTitle(currAmiibo.getNameUppercase())
				.setImage(currAmiibo.getImage(egg, userDiscordID))
				.addField("Release Dates:",
						"🇯🇵: " + currAmiibo.getReleaseJP() + "\n🇺🇸: " + currAmiibo.getReleaseNA() + "\n🇪🇺: "
								+ currAmiibo.getReleaseEU() + "\n🇦🇺: " + currAmiibo.getReleaseAU())
				.addField("**Retailers with Stock**", retailOutput).setColor(currAmiibo.getColor())
				/*
				.addField("\u200b", "**Average Completed & Sold Prices** *(est.)*")
				.addInlineField("Average Price NiB",
						currAmiibo.getFormattedNewPriceCompletedNA() + "\n"
								+ currAmiibo.getFormattedNewPriceCompletedUK())
				.addInlineField("Average Price OoB",
						currAmiibo.getFormattedUsedPriceCompletedNA() + "\n"
								+ currAmiibo.getFormattedUsedPriceCompletedUK())
				*/
				.addField("\u200b", "**Average Current Listed Prices** *(est.)*")
				.addInlineField("Average Price NiB",
						currAmiibo.getFormattedNewPriceListedNA() + "\n" + currAmiibo.getFormattedNewPriceListedUK())
				.addInlineField("Average Price OoB",
						currAmiibo.getFormattedUsedPriceListedNA() + "\n" + currAmiibo.getFormattedUsedPriceListedUK());
		return embed;
	}

	public String getCommand() {
		return command;
	}

	public String getDescription() {
		return description;
	}

	public int getAccessLevel() {
		return accessLevel;
	}

	public String getParameters() {
		return parameterString;
	}
}