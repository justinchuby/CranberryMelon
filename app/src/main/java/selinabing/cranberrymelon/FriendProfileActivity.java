package selinabing.cranberrymelon;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.id.message;

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
    @BindView(R.id.LinkEmail) ImageView ivEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set onclicklisteners
        ivFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //link to webpage

                try {
                    Intent intent = new Intent("android.intent.category.LAUNCHER");
                    intent.setClassName("com.facebook.katana", "com.facebook.katana.LoginActivity");
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse("http://facebook.com"));
                    startActivity(intent);
                }
            }
        });

        ivSkype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent("android.intent.category.LAUNCHER");
                    intent.setClassName("com.skype.raider", "com.skype.raider.Main");
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    //link to webpage
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse("http://skype.com"));
                    startActivity(intent);
                }
            }
        });

        ivMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //link to page
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://messenger.com"));
                startActivity(intent);
            }
        });

        ivPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0377778888"));
                if (callIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(callIntent);
                }
            }
        });

        ivEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "ranhuan2333@gmail.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
                intent.putExtra(Intent.EXTRA_TEXT, "mail body");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(intent, ""));
                }
            }
        });


        ivMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String to = "4127368173";
                Uri smsUri = Uri.parse("tel:" + to);
                Intent intent = new Intent(Intent.ACTION_VIEW, smsUri);
                intent.putExtra("address", to);
                intent.putExtra("sms_body", message);
                intent.setType("vnd.android-dir/mms-sms");//here setType will set the previous data null.
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }
}
