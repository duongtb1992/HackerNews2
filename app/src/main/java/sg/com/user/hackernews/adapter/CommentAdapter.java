package sg.com.user.hackernews.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import sg.com.user.hackernews.inf.DataLoadStatusListener;
import sg.com.user.hackernews.model.CommentModel;
import sg.com.user.hackernews.restful.RestfulController;
import sg.com.user.hackernews.widget.CommentView;

/**
 * Created by duongtb on 31/05/2017.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ItemViewHolder> {

    private Context context;
    private List<CommentModel> comments;
    private DataLoadStatusListener dataLoadStatusListener;

    public CommentAdapter(Context context, final List<CommentModel> comments, DataLoadStatusListener dataLoadStatusListener) {
        this.context = context;
        this.comments = comments;
        this.dataLoadStatusListener = dataLoadStatusListener;

        for (final CommentModel commentModel : comments) {

            if (!commentModel.isLoaded) {
                RestfulController.getCommentInfo(commentModel.id, new Handler(new Handler.Callback() {
                    int loadingPosition = comments.indexOf(commentModel);

                    @Override
                    public boolean handleMessage(Message msg) {

                        if (msg != null && msg.obj != null) {

                            if (CommentAdapter.this.dataLoadStatusListener != null) {
                                CommentAdapter.this.dataLoadStatusListener.onDataLoadFinished();
                                CommentAdapter.this.dataLoadStatusListener = null;
                            }

                            CommentModel loadedComment = (CommentModel) msg.obj;
                            CommentModel inListComment = comments.get(loadingPosition);
                            inListComment.updateInfo(loadedComment.text, loadedComment.author, loadedComment.kids, loadedComment.time);
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
        return new ItemViewHolder(new CommentView(context));

    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        final CommentModel commentModel = comments.get(position);
        CommentView commentView = holder.commentView;
        if (commentModel.isLoaded && !TextUtils.isEmpty(commentModel.text)) {
            commentView.updateUI(commentModel);
        } else {
            commentView.loadingUI();
        }


    }


    @Override
    public int getItemCount() {
        if (comments == null) {
            return 0;
        }
        return comments.size();
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        CommentView commentView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            commentView = (CommentView) itemView;
        }
    }
}
