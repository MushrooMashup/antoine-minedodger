package minedodgerteam.minedodger;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.Random;


/**
*Main activity de l'application
*/
public class OptionsActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        String[] langues = new String[2];
        String[0] = "Français";
        String[1] = "English";

        Spinner languesSpinner = (Spinner) findViewById(R.id.spinner);

        languesSpinner.
    }
}
