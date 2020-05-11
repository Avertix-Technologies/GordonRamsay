package org.kowlintech.utils.reddit;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dv8tion.jda.internal.utils.IOUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.util.Objects;

public class Reddit {

    private static final String USER_AGENT = "happybot:io.github.jroy:v0.1 (by /u/wheezygold7931)";

    public static MemePost getRandomMedia(String subReddit) {
        JsonElement jsonElement = new JsonParser().parse(Objects.requireNonNull(readUrl("https://www.reddit.com/r/" + subReddit + "/random/.json")));
        JsonObject jsonObject;
        if (jsonElement.isJsonArray()) {
            jsonObject = jsonElement.getAsJsonArray().get(0).getAsJsonObject();
        } else {
            jsonObject = jsonElement.getAsJsonObject();
        }
        return new MemePost(jsonObject.getAsJsonObject("data").getAsJsonArray("children").get(0).getAsJsonObject().getAsJsonObject("data"));
    }

    @SuppressWarnings("deprecation")
    public static String readUrl(String urlString) {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(urlString);
            request.addHeader("User-Agent", USER_AGENT);

            HttpResponse response = client.execute(request);
            InputStream content = response.getEntity().getContent();
            byte[] bytes = IOUtil.readFully(content);
            return new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}