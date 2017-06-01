package sg.com.user.hackernews.restful;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sg.com.user.hackernews.model.CommentModel;
import sg.com.user.hackernews.model.StoryModel;

/**
 * Created by duongtbL on 4/26/2016.
 */
public class RestfulController {



    public static void getTopIndex(final Handler handler) {
        HackerNewsService service = RestfulClient.getClient();
        Call<List<Long>> call = service.getTopIndex();


        call.enqueue(new Callback<List<Long>>() {
            @Override
            public void onResponse(Call<List<Long>> call, Response<List<Long>> response) {
                try {
                    List<Long> data;
                    Message message;

                    data = response.body();
                    message = new Message();

                    message.obj = data;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                    handler.sendMessage(new Message());
                }
            }

            @Override
            public void onFailure(Call<List<Long>> call, Throwable t) {
                t.printStackTrace();
                handler.sendMessage(new Message());
            }
        });
    }

    public static void getStoryInfo(long index, final Handler handler) {
        HackerNewsService service = RestfulClient.getClient();
        Call<StoryModel> call = service.getStoryInfo(index);


        call.enqueue(new Callback<StoryModel>() {
            @Override
            public void onResponse(Call<StoryModel> call, Response<StoryModel> response) {
                try {
                    StoryModel data;
                    Message message;

                    data = response.body();
                    message = new Message();

                    message.obj = data;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                    handler.sendMessage(new Message());
                }
            }

            @Override
            public void onFailure(Call<StoryModel> call, Throwable t) {
                t.printStackTrace();
                handler.sendMessage(new Message());
            }
        });
    }

    public static void getCommentInfo(long index, final Handler handler) {
        HackerNewsService service = RestfulClient.getClient();
        Call<CommentModel> call = service.getCommentInfo(index);


        call.enqueue(new Callback<CommentModel>() {
            @Override
            public void onResponse(Call<CommentModel> call, Response<CommentModel> response) {
                try {
                    CommentModel data;
                    Message message;

                    data = response.body();
                    message = new Message();

                    message.obj = data;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                    handler.sendMessage(new Message());
                }
            }

            @Override
            public void onFailure(Call<CommentModel> call, Throwable t) {
                t.printStackTrace();
                handler.sendMessage(new Message());
            }
        });
    }



}
