package amiiBot;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

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
	        	for(int i = 0; i < commandList.size(); i++) {
	            if (event.getMessageContent().equalsIgnoreCase(commandToken + commandList.get(i).getCommand())) {
	            	event.getChannel().sendMessage(commandList.get(i).getOutput().setColor(Color.BLUE));	            
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
