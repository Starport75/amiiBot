package amiiBot;

import java.awt.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class Main {

	public static void main(String[] args) {

		ArrayList<AbstractCommand> commandList = addCommands();
		String commandToken = "!";

		HelpCommand.setCommandList(commandList);
		HelpCommand.setCommandToken(commandToken);

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
					ArrayList<String> params = new ArrayList<String>();
					while (message.contains("<") & message.contains(">")
							& message.indexOf("<") < message.indexOf(">")) {

						params.add(message.substring(message.indexOf('<') + 1, message.indexOf('>')));
						message = message.substring(message.indexOf('>') + 1);
					}

					EmbedBuilder messageOutput = commandList.get(i)
							.getOutput(event.getMessage().getAuthor().getIdAsString(), params).setColor(Color.blue);
					int outputSize = commandList.get(i).getLength();
					if (outputSize > 5000) {
						event.getChannel().sendMessage(
								new EmbedBuilder().setDescription("Error: This message exceeds the maximum length by " + (outputSize - 5000) + " characters. Please refine your query and try again"));
					} else {
						event.getChannel().sendMessage(messageOutput);
					}
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
		list.add(new GenerateCollectionImageCommand());
		list.add(new ListTypesCommand());
		list.add(new ListSeriesCommand());
		list.add(new ListCollectionCommand());

		return list;
	}
}
