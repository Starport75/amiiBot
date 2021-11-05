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

	public boolean sendPostRequest(String url, String discordID, String amiiboID, String isBoxed) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("api_key", key));
		params.add(new BasicNameValuePair("discord_id", discordID));
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
		isError = result.substring(0, 50).contains("error");
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
}
