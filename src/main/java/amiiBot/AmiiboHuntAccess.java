package amiiBot;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AmiiboHuntAccess {

	String key = getKey();
	String lastOutput;
	String lastError;

	public boolean sendPostRequest(String url, String discordID, String amiiboID, String isBoxed) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("api_key", key));
		if (discordID != null) {
			params.add(new BasicNameValuePair("discord_id", discordID));
		}
		if (amiiboID != null) {
			params.add(new BasicNameValuePair("amiibo_id", amiiboID));
		}
		if (isBoxed != null) {
			params.add(new BasicNameValuePair("is_boxed", isBoxed));
		}

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		CloseableHttpResponse response = null;
		try {
			response = client.execute(httpPost);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result = null;
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instream = null;
			try {
				instream = entity.getContent();
			} catch (UnsupportedOperationException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte[] bytes = null;
			try {
				bytes = IOUtils.toByteArray(instream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				result = new String(bytes, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				instream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		lastOutput = result;

		boolean isError;
		int endpoint;
		if (result.length() > 50) {
			endpoint = 50;
		} else {
			endpoint = result.length();
		}
		String subResult = result.substring(0, endpoint);
		isError = subResult.contains("error");
		if (isError) {
			System.out.println("Error Found: " + result.toString().substring(0, endpoint));
			if (subResult.contains("user profile is not public")) {
				lastError = "Error: Cannot access account data, as the user is set to Private.";
			} else if (subResult.contains("discord ID not found")) {
				lastError = "Error: You don't have an AmiiboHunt account linked to their Discord account! Click [here](https://www.amiibohunt.com/oauth/discord/redirect) to link and/or create your account!";
			} else if (subResult.contains("invalid discord ID")) {
				lastError = "An error has occured while interacting with Discord. Please contact Starport75 for assistance";
			} else if (subResult.contains("invalid key")) {
				lastError = "An error has occured while interacting with amiiboHunt. Please contact Starport75 for assistance";
			} else {
				lastError = "An unknown error has occured. Please contact Starport75 for assistance.";
			}
		}
		return !isError;
	}

	public String getKey() {
		File keyFile = new File("src\\main\\resources\\amiiboHuntKey.txt");
		try {
			Scanner tokenScanner = new Scanner(keyFile);
			key = tokenScanner.next();
			tokenScanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("FILE NOT FOUND");
			e.printStackTrace();
		}

		return key;
	}

	public String getLastRequestString() {
		return lastOutput;
	}
	
	public String getLastError() {
		return lastError;
	}
}
