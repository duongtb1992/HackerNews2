package sg.com.user.hackernews.activity;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dinuscxj.refresh.RecyclerRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sg.com.user.hackernews.R;
import sg.com.user.hackernews.activity.abs.BaseActivity;
import sg.com.user.hackernews.adapter.StoryAdapter;
import sg.com.user.hackernews.inf.DataLoadStatusListener;
import sg.com.user.hackernews.model.StoryModel;
import sg.com.user.hackernews.restful.RestfulController;

public class MainActivity extends BaseActivity implements DataLoadStatusListener {

    @BindView(R.id.recycler_view)
    RecyclerView rcv;

    @BindView(R.id.ln_container)
    LinearLayout lnContainer;

    @BindView(R.id.refresh_layout)
    RecyclerRefreshLayout refreshLayout;

    @BindView(R.id.pb)
    View pb;

    @BindView(R.id.btn_try_again)
    Button btnTry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        init();


        loadData();
    }

    private void init() {
        refreshLayout.setOnRefreshListener(new RecyclerRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                loadData();
            }
        });
        btnTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTry.setVisibility(View.GONE);
                pb.setVisibility(View.VISIBLE);
                loadData();
            }
        });
    }

    private void loadData() {
        RestfulController.getTopIndex(new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                refreshLayout.setRefreshing(false);
                if (msg != null && msg.obj != null) {
                    List<Long> ids = (List<Long>) msg.obj;
                    showData(StoryModel.generateStories(ids));
                } else {
                    emptyData();
                }

                return false;
            }
        }));
    }
    private void emptyData() {
        Toast.makeText(this, R.string.alert_cant_get_data, Toast.LENGTH_SHORT).show();
        pb.setVisibility(View.GONE);
        btnTry.setVisibility(View.VISIBLE);
    }

    private void showData(List<StoryModel> stories) {
        rcv.setLayoutManager(new LinearLayoutManager(this));
        rcv.setAdapter(new StoryAdapter(this, stories,this));

    }


    @Override
    public void onDataLoadFinished() {
        pb.setVisibility(View.GONE);
    }
}
