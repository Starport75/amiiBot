package amiiBot;

import java.util.ArrayList;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class HelpCommand extends AbstractCommand {
	static ArrayList<AbstractCommand> commandList;
	public static String commandToken;
	String parameterString = "";
	int accessLevel = 0;

	public static void setCommandToken(char commandToken2) {
		commandToken = commandToken2 + "";
	}

	public static void setCommandList(ArrayList<AbstractCommand> list) {
		commandList = list;
	}

	String command = "help";
	String description = "Lists the commands the user can use. You just used it!";

	public BetterEmbed getOutput(String userDiscordID, int accessLevel, UserAmiiboList amiiboList,
			ArrayList<String> parameters, EasterEgg egg, DiscordApi api, Server currServer, User currUser) {
		String output = "";

		for (int i = 0; i < commandList.size(); i++) {
			if (accessLevel >= commandList.get(i).getAccessLevel()) {
				output = output + "\n**" + commandToken + commandList.get(i).getCommand() + " "
						+ commandList.get(i).getParameters() + "**\n\t*" + commandList.get(i).getDescription() + "*\n";
			}
		}
		BetterEmbed embed = new BetterEmbed().setDescription(output);
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
