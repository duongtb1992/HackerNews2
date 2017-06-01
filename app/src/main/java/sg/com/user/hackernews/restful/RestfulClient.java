package sg.com.user.hackernews.restful;




import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by duongtbL on 4/26/2016.
 */
public class RestfulClient {
    private static HackerNewsService nextTVService;
    private static String baseUrl = "https://hacker-news.firebaseio.com";

    public static HackerNewsService getClient() {
        if (nextTVService == null) {

            OkHttpClient okClient = new OkHttpClient.Builder()
                    .addInterceptor(
                            new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Response response = chain.proceed(chain.request());
                                    return response;
                                }
                            })
                    .build();

            Retrofit client = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            nextTVService = client.create(HackerNewsService.class);

        }
        return nextTVService;
    }


}
