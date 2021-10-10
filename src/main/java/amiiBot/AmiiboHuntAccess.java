package amiiBot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class AmiiboHuntAccess {

	public String sendPostRequest() throws IOException, InterruptedException {
		CloseableHttpClient client = HttpClients.createDefault();
	    HttpPost httpPost = new HttpPost("https://www.amiibohunt.com/api/discord/v1/getCollectionById");

	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    params.add(new BasicNameValuePair("api_key", "9b6e5039fdf8a36430ecf0d437c117b0"));
	    params.add(new BasicNameValuePair("discord_id", "240899417010470912"));
	    httpPost.setEntity(new UrlEncodedFormEntity(params));

	    CloseableHttpResponse response = client.execute(httpPost);
	    String output = response.;
	    System.out.println("Status code: " + response.getStatusLine().getStatusCode());
	    client.close();
	    return output.toString();
	}

}
