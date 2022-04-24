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
		
		String output = userName + "'s collection consists of: ";
		String type;
		String formattedType;
		
		for (int i = 0; i < amiiboList.getNumOfTypes(); i++) {
			type = amiiboList.getTypeAt(i);
			formattedType = type.toUpperCase().substring(0,1) + type.substring(1);
			output = output + "\n" + amiiboList.getNumCollectedInType(type) + 
					"/" + amiiboList.getNumInType(type) + " " + formattedType + "s";
		}
		
		return new BetterEmbed().setTitle(userName + "'s Stats:")
				.setDescription(
						output
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