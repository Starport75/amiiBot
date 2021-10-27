package amiiBot;

import java.util.ArrayList;

public class HelpCommand extends AbstractCommand {
    static ArrayList<AbstractCommand> commandList;
    public static String commandToken;

    public static void setCommandToken(String token) {
        commandToken = token;
    }

    public static void setCommandList(ArrayList<AbstractCommand> list) {
        commandList = list;
    }

    String command = "help";
    String description = "Lists the commands the user can use. You just used it!";

    public BetterEmbed getOutput(String userDiscordID, UserAmiiboList amiiboList, ArrayList<String> parameters) {
        String output = "";

        for (int i = 0; i < commandList.size(); i++) {
            output = output + "\n**" + commandToken + commandList.get(i).getCommand() + "**\n\t*"
                    + commandList.get(i).getDescription() + "*";
        }
        BetterEmbed embed = new BetterEmbed().setDescription(output);
        updateLength(output);
        return embed;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public void updateLength(String output) {
        length = output.length();
    }

    public int getLength() {
        return length;
    }
}
