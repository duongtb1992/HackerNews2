package sg.com.user.hackernews.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sg.com.user.hackernews.R;
import sg.com.user.hackernews.activity.DetailActivity;
import sg.com.user.hackernews.inf.DataLoadStatusListener;
import sg.com.user.hackernews.model.StoryModel;
import sg.com.user.hackernews.restful.RestfulController;
import sg.com.user.hackernews.utils.DefExtra;

/**
 * Created by duongtb on 31/05/2017.
 */

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ItemViewHolder> {

    private Context context;
    private List<StoryModel> stories;
    private List<StoryModel> showedStories;
    private PrettyTime prettyTime;
    private DataLoadStatusListener dataLoadStatusListener;

    private static final int PREPARED_NUM = 20;

    public StoryAdapter(Context context, final List<StoryModel> stories, DataLoadStatusListener dataLoadStatusListener) {
        this.context = context;
        this.stories = stories;
        this.prettyTime = new PrettyTime();
        this.dataLoadStatusListener = dataLoadStatusListener;
        this.showedStories = new ArrayList<>();

        preparedData(0);

    }


    private void preparedData(int fromPosition) {
        for (int i = fromPosition; i < fromPosition + PREPARED_NUM; i++) {
            if (i >= getItemCount()) {
                break;
            }
            final StoryModel cacheStories = stories.get(i);

            if (!cacheStories.isLoaded) {
                cacheStories.isLoaded = true;

                RestfulController.getStoryInfo(cacheStories.id, new Handler(new Handler.Callback() {
                    int loadingPosition = stories.indexOf(cacheStories);

                    @Override
                    public boolean handleMessage(Message msg) {

                        if (msg != null && msg.obj != null) {

                            if (StoryAdapter.this.dataLoadStatusListener != null) {
                                StoryAdapter.this.dataLoadStatusListener.onDataLoadFinished();
                                StoryAdapter.this.dataLoadStatusListener = null;
                            }
                            StoryModel loadedStory = (StoryModel) msg.obj;
                            StoryModel inListStory = stories.get(loadingPosition);
                            inListStory.updateInfo(loadedStory.title, loadedStory.author, loadedStory.point, loadedStory.time, loadedStory.url, loadedStory.kids);
                            notifyItemChanged(loadingPosition);
                        }
                        return false;
                    }
                }));

            }
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_story, parent, false);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        final StoryModel storyModel = stories.get(position);

        showedStories.add(storyModel);

        if (storyModel.isLoaded) {
            updateUI(holder, storyModel);
        } else {
            loadingUI(holder);
        }
        preparedData(position + 1);
    }

    private void loadingUI(ItemViewHolder holder) {
        holder.tvTitle.setText("");
        holder.tvAuthor.setText("");
        holder.tvPoint.setText("");
        holder.tvTime.setText("");
        holder.ivOpenUrl.setOnClickListener(null);
        holder.llRoot.setOnClickListener(null);


    }

    private void updateUI(ItemViewHolder holder, final StoryModel storyModel) {
        holder.tvTitle.setText(storyModel.title);
        holder.tvAuthor.setText(context.getString(R.string.text_story_author, storyModel.author));
        holder.tvPoint.setText(context.getString(storyModel.point <= 1 ? R.string.text_story_point : R.string.text_story_points, storyModel.point));
        holder.tvTime.setText(prettyTime.format(new Date(storyModel.time * 1000)));

        holder.ivOpenUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl(storyModel.url);
            }
        });

        holder.llRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDetail(storyModel);
            }
        });

    }

    private void goDetail(StoryModel storyModel) {

        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DefExtra.DEF_EXTRA_STORY_MODEL, storyModel);
        context.startActivity(intent);

    }

    private void openUrl(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }


    @Override
    public int getItemCount() {
        if (stories == null) {
            return 0;
        }

        return stories.size();
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_story_title)
        TextView tvTitle;

        @BindView(R.id.tv_story_author)
        TextView tvAuthor;

        @BindView(R.id.tv_story_point)
        TextView tvPoint;

        @BindView(R.id.tv_story_time)
        TextView tvTime;

        @BindView(R.id.iv_open_url)
        ImageView ivOpenUrl;

        @BindView(R.id.ll_root)
        LinearLayout llRoot;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
