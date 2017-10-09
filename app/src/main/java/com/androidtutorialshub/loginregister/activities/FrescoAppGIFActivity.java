package com.androidtutorialshub.loginregister.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.androidtutorialshub.loginregister.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

public class FrescoAppGIFActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_fresco_app_gif);
        SimpleDraweeView myDraweeView = (SimpleDraweeView)findViewById(R.id.my_drawee_view);

      //  myDraweeView.setImageURI(Uri.parse("https://apod.nasa.gov/apod/image/1610/ivan_iss_3032.jpg"));

        DraweeController controller =
               Fresco.newDraweeControllerBuilder()
                      .setUri("https://bucom.com/wp-content/uploads/2017/01/sample-gif-image.gif")
                        .setAutoPlayAnimations(true)
                        .build();
        myDraweeView.setController(controller);
    }
}
