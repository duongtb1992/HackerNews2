package sg.com.user.hackernews.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sg.com.user.hackernews.R;
import sg.com.user.hackernews.activity.abs.BaseActivity;
import sg.com.user.hackernews.adapter.CommentAdapter;
import sg.com.user.hackernews.adapter.StoryAdapter;
import sg.com.user.hackernews.inf.DataLoadStatusListener;
import sg.com.user.hackernews.model.CommentModel;
import sg.com.user.hackernews.model.StoryModel;
import sg.com.user.hackernews.utils.DefExtra;

/**
 * Created by duongtb on 31/05/2017.
 */

public class DetailActivity extends BaseActivity implements DataLoadStatusListener {

    private StoryModel storyModel;

    @BindView(R.id.recycler_view)
    RecyclerView rcv;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.ln_container)
    LinearLayout lnContainer;

    @BindView(R.id.pb)
    View pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        init();
        showData();
    }

    private void init() {
        storyModel = (StoryModel) getIntent().getSerializableExtra(DefExtra.DEF_EXTRA_STORY_MODEL);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setTitle("");
        toolbar.setSubtitle("");
    }

    private void showData() {

        rcv.setLayoutManager(new LinearLayoutManager(this));
        rcv.setAdapter(new CommentAdapter(this, CommentModel.generateComments(storyModel.kids, true), this));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataLoadFinished() {
        pb.setVisibility(View.GONE);
    }
}
