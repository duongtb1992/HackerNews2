package sg.com.user.hackernews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duongtb on 31/05/2017.
 */

public class StoryModel implements Serializable {

    @Expose
    public long id;

    @SerializedName("title")
    public String title;

    @SerializedName("by")
    public String author;

    @SerializedName("time")
    public long time;

    @SerializedName("url")
    public String url;

    @SerializedName("kids")
    public List<Long> kids;

    @SerializedName("score")
    public int point;

    @Expose
    public boolean isLoaded;

    public StoryModel(long id) {
        this.id = id;
        isLoaded = false;
    }

    public void updateInfo(String title, String author, int point, long date, String url, List<Long> kids)
    {
        isLoaded = true;
        this.title = title;
        this.author = author;
        this.point = point;
        this.time = date;
        this.url = url;
        this.kids = kids;

    }

    public static List<StoryModel> generateStories(List<Long> ids) {
        List<StoryModel> result = new ArrayList<>();

        if (ids != null) {
            for (Long id : ids) {
                result.add(new StoryModel(id));
            }
        }
        return result;

    }

}
