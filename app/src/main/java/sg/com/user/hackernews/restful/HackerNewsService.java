package sg.com.user.hackernews.restful;



import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;
import sg.com.user.hackernews.model.CommentModel;
import sg.com.user.hackernews.model.StoryModel;

/**
 * Created by duongtbL on 4/26/2016.
 */
public interface HackerNewsService {

    @GET("/v0/topstories.json")
    Call<List<Long>> getTopIndex();

    @GET("/v0/item/{index}.json")
    Call<StoryModel> getStoryInfo(@Path("index") long index);

    @GET("/v0/item/{index}.json")
    Call<CommentModel> getCommentInfo(@Path("index") long index);
}
