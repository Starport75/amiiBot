package amiiBot;

import java.util.ArrayList;

public class HelpCommand extends AbstractCommand {
	static ArrayList<AbstractCommand> commandList;
	public static String commandToken;
	int accessLevel = 0;

	public static void setCommandToken(String token) {
		commandToken = token;
	}

	public static void setCommandList(ArrayList<AbstractCommand> list) {
		commandList = list;
	}

	String command = "help";
	String description = "Lists the commands the user can use. You just used it!";

	public BetterEmbed getOutput(String userDiscordID, int accessLevel, UserAmiiboList amiiboList,
			ArrayList<String> parameters, EasterEgg egg) {
		String output = "";

		for (int i = 0; i < commandList.size(); i++) {
			if (accessLevel >= commandList.get(i).getAccessLevel()) {
				output = output + "\n**" + commandToken + commandList.get(i).getCommand() + "**\n\t*"
						+ commandList.get(i).getDescription() + "*";
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
}
