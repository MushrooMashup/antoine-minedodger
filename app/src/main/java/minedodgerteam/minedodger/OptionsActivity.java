package minedodgerteam.minedodger;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.Random;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.AdapterView.OnItemSelectedListener;


/**
 * Created by Eric Longueville on 02/03/2017.
 */

public class OptionsActivity extends AppCompatActivity
{

    public boolean sonJoueurBool;
    public boolean sonEnvironnementBool;
    public String langue;
    public Spinner choixLangue;
    public CheckBox sonJoueur;
    public CheckBox sonEnvironnement;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        sonJoueurBool = false;
        sonEnvironnementBool = false;
        langue = "Français";

        choixLangue = (Spinner) findViewById(R.id.spinner);
        choixLangue.setOnItemSelectedListener (new LangueChoisie());

        sonJoueur = (CheckBox) findViewById(R.id.sonJoueur);
        sonJoueur.setOnCheckedChangeListener (new SonJoueurListener());

        sonEnvironnement = (CheckBox) findViewById(R.id.sonJeu);
        sonEnvironnement.setOnCheckedChangeListener (new SonEnvironnementListener());
    }

    public class LangueChoisie implements OnItemSelectedListener
    {
        @Override
        public void onItemSelectedListener ()
        {
            langue = choixLangue.getSelectedItem();
        }
    }

    public class SonJoueurListener implements OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged (CompoundButton buttonView, boolean sonJoueurBool)
        {
            sonJoueurBool = true;
        }
    }

    public class SonEnvironnementListener implements OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged (CompoundButton buttonView, boolean sonEnvironnementBool)
        {
            sonEnvironnementBool = true;
        }
    }
}
