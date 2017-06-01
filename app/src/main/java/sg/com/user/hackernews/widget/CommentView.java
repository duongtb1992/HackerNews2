package sg.com.user.hackernews.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import sg.com.user.hackernews.R;
import sg.com.user.hackernews.adapter.CommentAdapter;
import sg.com.user.hackernews.model.CommentModel;

/**
 * Created by duongtb on 01/06/2017.
 */

public class CommentView extends LinearLayout {

    private Context context;
    private CommentModel commentModel;
    private View view;
    private PrettyTime prettyTime;

    @BindView(R.id.tv_comment_author)
    TextView tvAuthor;

    @BindView(R.id.tv_comment_content)
    TextView tvContent;

    @BindView(R.id.tv_comment_time)
    TextView tvTime;

    @BindView(R.id.ll_root)
    LinearLayout llRoot;

    @BindView(R.id.rcv)
    RecyclerView rcv;

    @BindView(R.id.v_reply_padding)
    View vReplyPadding;

    public CommentView(Context context) {
        super(context);
        this.context = context;
        this.prettyTime = new PrettyTime();

        init();
    }

    private void init() {
        view = LayoutInflater.from(context).inflate(R.layout.item_comment, this);
        ButterKnife.bind(this, view);
    }

    public void loadingUI() {
        tvAuthor.setText("");
        tvContent.setText("");
        tvTime.setText("");
        llRoot.setVisibility(View.GONE);
        rcv.setVisibility(View.GONE);
    }

    public void updateUI(final CommentModel commentModel) {

        this.commentModel = commentModel;
        Log.d("duong", "id : " + commentModel.id);
        tvContent.setText(Html.fromHtml(commentModel.text));
        tvAuthor.setText(commentModel.author);
        tvTime.setText(prettyTime.format(new Date(commentModel.time * 1000)));
        llRoot.setVisibility(View.VISIBLE);

        if (commentModel.isParent) {
            vReplyPadding.setVisibility(GONE);
            rcv.setLayoutManager(new LinearLayoutManager(context));
            if (commentModel.replies == null) {
                commentModel.replies = CommentModel.generateComments(commentModel.kids, false);
            }
            rcv.setAdapter(new CommentAdapter(context,commentModel.replies, null));

        } else {
            rcv.setVisibility(View.INVISIBLE);
            vReplyPadding.setVisibility(View.INVISIBLE);
        }


    }


}
