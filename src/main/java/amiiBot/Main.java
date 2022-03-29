/*
 * amiiBot v2.0.0
 * 3/28/2022
 * Created by Starport75 in collaboration with rob
 */



package amiiBot;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.intent.Intent;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {

		ArrayList<AbstractCommand> commandList = addCommands();
		char commandToken = '!';

		UserAmiiboList mainList = new UserAmiiboList();
		UserAmiiboList secondaryList = mainList;
		EasterEgg easterEgg = new EasterEgg();
		String modRoleID;
		String adminRoleID;
		String serverID;

		easterEgg.addEasterEgg(mainList);

		HelpCommand.setCommandList(commandList);
		HelpCommand.setCommandToken(commandToken);

		String token = "";
		String seniorToken = "";
		String juniorToken = "";
		String debugString = "";
		Boolean debugMode = true;

		File tokenFile = new File("src\\main\\resources\\token.txt");
		try {
			Scanner tokenScanner = new Scanner(tokenFile);
			debugString = tokenScanner.next();
			seniorToken = tokenScanner.next();
			juniorToken = tokenScanner.next();
			tokenScanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("FILE NOT FOUND");
			e.printStackTrace();
		}

		if (debugString.equals("True")) {
			debugMode = true;
		} else if (debugString.equals("False")) {
			debugMode = false;
		} else {
			System.out.println("ERROR: The debug string in the token.txt could not be read");
		}

		System.out.println("Debug mode is set to <" + debugMode.toString().toUpperCase() + ">");

		if (debugMode) {
			token = juniorToken;
			// amiibo testing server
			modRoleID = "904857029485670431";
			adminRoleID = "904856959025549323";
			serverID = "895083537647149067";

		} else {
			token = seniorToken;
			// r/amiibo server
			modRoleID = "137302149389484032"; // mod ID
			adminRoleID = "900157089983373323"; // admin ID
			serverID = "115840745549725703";
		}

		if (!debugMode) {
			pressAnyKeyToContinue();
		}

		DiscordApi api = new DiscordApiBuilder().setAllIntentsExcept(Intent.GUILD_PRESENCES).setToken(token).login()
				.join();
		Server currServer = api.getServerById(serverID).get();
		Role adminRole = api.getRoleById(adminRoleID).get();
		Role modRole = api.getRoleById(modRoleID).get();

		// Print the invite url of your bot
		System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());

		api.addMessageCreateListener(event -> {
			if (!event.getServer().get().getIdAsString().equals(serverID)) {
				System.out.println("ERROR: The Discord ID does not match");
			} else {
				String mainCommand = "";
				boolean commandFound = false;
				for (int i = 0; i < commandList.size(); i++) {
					String message = event.getMessageContent();
					if (message.indexOf(' ') > 0) {
						mainCommand = message.substring(0, message.indexOf(' '));
					} else {
						mainCommand = message;
					}
					if (mainCommand.equalsIgnoreCase(commandToken + commandList.get(i).getCommand())) {
						String discordID = event.getMessage().getAuthor().getIdAsString();
						User currUser = null;
						try {
							currUser = api.getUserById(discordID).get();
						} catch (InterruptedException | ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						int accessLevel;
						if (currUser.getRoles(currServer).contains(adminRole)) {
							accessLevel = 2;
						} else if (currUser.getRoles(currServer).contains(modRole)) {
							accessLevel = 1;
						} else {
							accessLevel = 0;
						}

						if (commandList.get(i).getAccessLevel() <= accessLevel) {
							ArrayList<String> params = new ArrayList<String>();
							while (message.contains("<") & message.contains(">")
									& message.indexOf("<") < message.indexOf(">")) {

								params.add(message.substring(message.indexOf('<') + 1, message.indexOf('>')).toLowerCase());
								message = message.substring(message.indexOf('>') + 1);
							}
							commandFound = true;
							BetterEmbed messageOutput = commandList.get(i).getOutput(discordID, accessLevel, mainList,
									params, easterEgg, api, currServer, currUser);
							event.getChannel().sendMessage(messageOutput.getEmbed());
						}
					}
				}
				if (mainCommand.length() > 0 && !commandFound && mainCommand.charAt(0) == commandToken) {
					event.getChannel().sendMessage(
							new BetterEmbed().setError("Error: Unknown command \"" + mainCommand + "\"").getEmbed());
				}
			}
		});
	}

	public static ArrayList<AbstractCommand> addCommands() {
		ArrayList<AbstractCommand> list = new ArrayList<AbstractCommand>();

		list.add(new PingCommand());
		list.add(new HelpCommand());
		list.add(new GenerateCollectionImageCommand());
		list.add(new ListTypesCommand());
		list.add(new ListSeriesCommand());
		list.add(new ListAmiiboCommand());
		list.add(new ShowInfoCommand());
		list.add(new AddAmiiboCommand());
		list.add(new RemoveAmiiboCommand());
		//list.add(new CompareAmiiboCommand());
		list.add(new UserStatsCommand());
		list.add(new EasterEggCommand());
		list.add(new CreditsCommand());

		return list;
	}

	private static void pressAnyKeyToContinue() {
		System.out.println("Press Enter key to continue...");
		try {
			System.in.read();
		} catch (Exception e) {
		}
	}
}
