package amiiBot;

import java.util.ArrayList;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.channel.TextChannelEvent;
import org.javacord.api.event.message.MessageEvent;
import org.json.JSONObject;

public class GenerateCollectionImageCommand extends AbstractCommand{
	String description = "Default description. Contact the creator if you are reading this message";
	String command = "generateImage";

	public EmbedBuilder getOutput(MasterList masterList, String userDiscordID, ArrayList<String> parameters) {
		
		AmiiboHuntAccess websiteData = new AmiiboHuntAccess();

		JSONObject data = new JSONObject(websiteData.sendPostRequest("https://www.amiibohunt.com/api/discord/v1/getCollectionImageById", userDiscordID));
		System.out.println(data);
		String output = data.get("val").toString();
		
		EmbedBuilder embed = new EmbedBuilder()
				.setImage(output);
		return embed;
	}

	public String getCommand() {
		return command;
	}

	public String getDescription() {
		return description;
	}
}
