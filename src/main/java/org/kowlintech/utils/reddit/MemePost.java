package org.kowlintech.utils.reddit;

import com.google.gson.JsonObject;
import lombok.Getter;

@Getter
public class MemePost {
    private String title;
    private String subreddit;
    private String permaLink;
    private String mediaUrl;

    private boolean selfPost;
    private String selfText;

    private boolean isNsfw;

    MemePost(JsonObject dataObject) {
        title = dataObject.get("title").getAsString();
        subreddit = dataObject.get("subreddit").getAsString();
        permaLink = "https://reddit.com" + dataObject.get("permalink").getAsString();

        selfPost = dataObject.get("is_self").getAsBoolean();
        selfText = dataObject.get("selftext").getAsString();

        if (!selfPost) {
            mediaUrl = dataObject.get("url").getAsString();
        }

        isNsfw = dataObject.get("over_18").getAsBoolean();
    }

    public boolean isNsfw() {
        return isNsfw;
    }

    public String mediaUrl() {
        return mediaUrl;
    }

    public String title() {
        return title;
    }
}
