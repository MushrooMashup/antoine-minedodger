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
 * Created by Eric Longueville on 02/03/2017.
 */
public class OptionsActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        String[] langues = new String[2];
        langues[0] = "Français";
        langues[1] = "English";

        Spinner languesSpinner = (Spinner) findViewById(R.id.spinner);
        //ArrayAdapter à partir des ressources
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                                                                             R.array.listeLangues_array,
                                                                             android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Appliquer le tout
        spinner.setAdapter(adapter);
    }
}
