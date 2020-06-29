package com.huseen.abo.aita1998.timemagment.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

 import com.huseen.abo.aita1998.timemagment.adtional.YourPreference;
import com.huseen.abo.aita1998.timemagment.catagurey.CatagureyActivity;
import com.huseen.abo.aita1998.timemagment.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_DURATION = 2000;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_activity);
        constraintLayout = findViewById(R.id.constraintLayout);
        setBackground();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //   if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                //    Intent intent = new Intent(SplashActivity.this, HomePageActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
                //         } else {
                //    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                Intent intent = new Intent(SplashActivity.this, CatagureyActivity.class);
                startActivity(intent);
                finish();
//                }else{
//                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                }
            }
            //  }
        }, SPLASH_DISPLAY_DURATION);
    }

    private void setBackground() {
        YourPreference yourPreference = YourPreference.getInstance(getApplicationContext());

        final String screen1 = yourPreference.getScreen1();

        Drawable yourDrawable = null;
        try {
            InputStream inputStream = getContentResolver().openInputStream(Uri.parse(screen1));
            yourDrawable = Drawable.createFromStream(inputStream, screen1);
        } catch (FileNotFoundException e) {
            //     yourDrawable = getResources().getDrawable(R.drawable.default_image);
        }
        if (!screen1.equals("")) {
            constraintLayout.setBackground(yourDrawable);

        }

    }
}
