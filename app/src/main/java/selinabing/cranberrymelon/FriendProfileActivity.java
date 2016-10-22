package selinabing.cranberrymelon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FriendProfileActivity extends AppCompatActivity {

    @BindView(R.id.avatar_imageview)
    ImageView ivAvatar;
    @BindView(R.id.title_textview)
    TextView tvFriendName;
    @BindView(R.id.subtitle_textview)
    TextView tvFriendLocation;
    @BindView(R.id.tvTime) TextView tvRelativeTime;
    @BindView(R.id.tvTimeOfDay) TextView tvPreciseTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        

    }



}
