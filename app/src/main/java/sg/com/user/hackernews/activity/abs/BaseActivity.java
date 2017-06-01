package sg.com.user.hackernews.activity.abs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import sg.com.user.hackernews.R;
import sg.com.user.hackernews.utils.SystemBarTintManager;

/**
 * Created by duongtb on 31/05/2017.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarTintManager.changeSystemStatusBar(R.color.colorPrimary,this);

    }
}
