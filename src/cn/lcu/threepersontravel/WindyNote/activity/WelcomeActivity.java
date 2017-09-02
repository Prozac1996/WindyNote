package cn.lcu.threepersontravel.WindyNote.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.lcu.threepersontravel.WindyNote.R;

/**
 * Created by lenovo on 2017/6/12.
 */

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welocme);
        TextView welcomeTitle = (TextView) findViewById(R.id.welcome_title);
        AlphaAnimation aa1 = new AlphaAnimation(0,1);
        AlphaAnimation aa2 = new AlphaAnimation(0,1);
        aa1.setDuration(1000);
        aa2.setDuration(2000);
        welcomeTitle.startAnimation(aa1);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.welcome);
        linearLayout.startAnimation(aa2);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WelcomeActivity.this,MyActivity.class);
                startActivity(i);
                finish();
            }
        });



    }
}
