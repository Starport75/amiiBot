package amiiBot;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class Main {

	public static void main(String[] args) {
		
		AmiiboHuntAccess AHA = new AmiiboHuntAccess();
		try {
			System.out.println("Data from amiiboHunt: " + AHA.sendPostRequest());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ArrayList<AbstractCommand> commandList = addCommands();
		String commandToken = "!";

		HelpCommand.setCommandList(commandList);
		HelpCommand.setCommandToken(commandToken);

		MasterList masterList = new MasterList();
		masterList.updateMasterList();

		String token = "";
		File tokenFile = new File("src\\main\\resources\\token.txt");
		try {
			Scanner tokenScanner = new Scanner(tokenFile);
			token = tokenScanner.next();
			tokenScanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("FILE NOT FOUND");
			e.printStackTrace();
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
					event.getChannel().sendMessage(commandList.get(i)
							.getOutput(masterList, message.substring(mainCommand.length())).setColor(Color.BLUE));
					i = commandList.size();
				}

			}

		});
	}

	public static ArrayList<AbstractCommand> addCommands() {
		ArrayList<AbstractCommand> list = new ArrayList<AbstractCommand>();

		list.add(new PingCommand());
		list.add(new HelpCommand());
		list.add(new ListAmiiboCommand());

		return list;
	}
}
