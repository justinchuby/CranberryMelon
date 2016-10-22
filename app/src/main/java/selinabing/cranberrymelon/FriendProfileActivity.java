package selinabing.cranberrymelon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FriendProfileActivity extends AppCompatActivity {

    @BindView(R.id.tvNumber1)
    TextView tvPhone;
    @BindView(R.id.tvNumber3) TextView tvEmail;
    @BindView(R.id.tvNumber5) TextView tvLocation;
    @BindView(R.id.LinkMessenger) ImageView ivMessenger;
    @BindView(R.id.LinkSkype) ImageView ivSkype;
    @BindView(R.id.LinkPhoneCall) ImageView ivPhone;
    @BindView(R.id.LinkMessage) ImageView ivMessage;
    @BindView(R.id.LinkFB) ImageView ivFB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set onclicklisteners
        ivMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //link to send message
            }
        });

    }



}
