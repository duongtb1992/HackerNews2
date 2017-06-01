package sg.com.user.hackernews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duongtb on 31/05/2017.
 */

public class CommentModel implements Serializable {

    @Expose
    public long id;

    @SerializedName("by")
    public String author;

    @SerializedName("kids")
    public List<Long> kids;

    @SerializedName("text")
    public String text;

    @SerializedName("time")
    public long time;

    @Expose
    public List<CommentModel> replies;

    private static final int MAX_LOADING_COMMENT = 10;

    @Expose
    public boolean isParent;

    @Expose
    public boolean isLoaded;

    public CommentModel(long id, boolean isParent) {
        this.id = id;
        this.isParent = isParent;
        this.isLoaded = false;
    }

    public void updateInfo(String text, String author, List<Long> kids, long time) {
        this.text = text;
        this.author = author;
        this.kids = kids;
        this.time = time;
        this.isLoaded = true;
    }

    public static List<CommentModel> generateComments(List<Long> kids, boolean isParent) {
        List<CommentModel> result = new ArrayList<>();

        if (kids != null) {
            for (Long id : kids) {
                result.add(new CommentModel(id, isParent));
                if (result.size() >= MAX_LOADING_COMMENT) {
                    break;
                }
            }
        }
        return result;

    }
}
