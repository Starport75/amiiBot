package amiiBot;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		ArrayList<AbstractCommand> commandList = addCommands();
		String commandToken = "!";

		UserAmiiboList mainList = new UserAmiiboList("205877471067766784");
		UserAmiiboList secondaryList = mainList;

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
		} else if (debugString.equals("False")){
			debugMode = false;
		} else {
			System.out.println("ERROR: The debug string in the token.txt could not be read");
		}
		
		System.out.println("Debug mode is set to <" + debugMode.toString().toUpperCase() + ">");
		
		if (debugMode) {
			token = juniorToken;
		} else {
			token = seniorToken;
		}

		DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

		// Print the invite url of your bot
		System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());

		api.addMessageCreateListener(event -> {
			for (int i = 0; i < commandList.size(); i++) {
				String message = event.getMessageContent();
				String mainCommand = "";
				if (message.indexOf(' ') > 0) {
					mainCommand = message.substring(0, message.indexOf(' '));
				} else {
					mainCommand = message;
				}
				if (mainCommand.equalsIgnoreCase(commandToken + commandList.get(i).getCommand())) {
					ArrayList<String> params = new ArrayList<String>();
					while (message.contains("<") & message.contains(">")
							& message.indexOf("<") < message.indexOf(">")) {

						params.add(message.substring(message.indexOf('<') + 1, message.indexOf('>')));
						message = message.substring(message.indexOf('>') + 1);
					}

					String discordID = event.getMessage().getAuthor().getIdAsString();

					BetterEmbed messageOutput = commandList.get(i).getOutput(discordID, mainList, params);
					event.getChannel().sendMessage(messageOutput.getEmbed());
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
		list.add(new ListCollectionCommand());
		list.add(new ShowInfoCommand());
		list.add(new AddAmiiboCommand());

		return list;
	}
}
