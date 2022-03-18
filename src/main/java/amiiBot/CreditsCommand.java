package amiiBot;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class CreditsCommand extends AbstractCommand {
	String description = "See who helped make amiiBot a reality!";
	String command = "credits";
	String parameterString = "";
	int accessLevel = 0;

	public BetterEmbed getOutput(String userDiscordID, int accessLevel, UserAmiiboList amiiboList,
			ArrayList<String> parameters, EasterEgg egg, DiscordApi api, Server currServer, User currUser) {
		return new BetterEmbed()
				.addField("**Head amiiBot Programmer: **", getUserName(api, "205877471067766784"))
				.addField("**amiiboHunt Developer and amiiBot Programming Support: **", getUserName(api, "240899417010470912"))
				.addField("**Data Collectors: **", 
						getUserName(api, "571177163789697025") + " \n" +
						getUserName(api, "272452471056760832") + " \n" +
						getUserName(api, "268428850239766529") + " \n" +
						getUserName(api, "170514612792328192") + " \n" +
						getUserName(api, "277092431814590464") + " \n" +
						getUserName(api, "854531142388547584") + " \n" +
						getUserName(api, "221300998617038848") + " \n" +
						getUserName(api, "363119455544410113") + " \n" )
						
				.addField("\u200b", "and a huge shoutout to " + getUserName(api, "83396497059614720") + " for allowing amiiBot to exist!")
				;
	};
	
	public String getUserName(DiscordApi api, String discordID) {
		String userName = "Error";
		User currUser = null;
		try {
			currUser = api.getUserById(discordID).get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (currUser != null) {
			userName = currUser.getMentionTag();
		}
		return userName;
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
