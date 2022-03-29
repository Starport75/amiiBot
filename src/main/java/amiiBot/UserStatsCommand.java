package amiiBot;

import java.util.ArrayList;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class UserStatsCommand extends AbstractCommand {
	String description = "Shows various stats about the user";
	String command = "generateStats";
	String parameterString = "";
	int accessLevel = 0;

	
	
	public BetterEmbed getOutput(String userDiscordID, int accessLevel, UserAmiiboList amiiboList,
			ArrayList<String> parameters, EasterEgg egg, DiscordApi api, Server currServer, User currUser) {
		String userName = currUser.getDisplayName(currServer);
		AmiiboHuntAccess websiteData = new AmiiboHuntAccess();
		
		if (!amiiboList.updateCollectionData(userDiscordID, websiteData) && parameters.size() > 1) {
			return new BetterEmbed().setError(websiteData.getLastError());
		}
		
		return new BetterEmbed().setTitle(userName + "'s Stats:")
				.setDescription(
						userName + " has " + amiiboList.getNumCollectedInType("figure") + "/" + amiiboList.getNumInType("figure") + " Figures" +
						"\n" + userName + " has " + amiiboList.getNumCollectedInType("card") + "/" + amiiboList.getNumInType("card") + " Cards" +
						"\n" + userName + " has " + amiiboList.getNumCollectedInType("band") + "/" + amiiboList.getNumInType("band") + " Bands"
						);
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