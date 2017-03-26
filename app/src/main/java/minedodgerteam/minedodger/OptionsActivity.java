package minedodgerteam.minedodger;



import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;


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
    String sonJoueurString;
    String sonEnvironnementString;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        //ici, on regarde le contenu du fichier pour le son du joueur et on remplit la variable avec ce contenu
        String eo1 = System.getProperty("line.separator");
        BufferedReader input = null;
        try
        {
            input = new BufferedReader(new InputStreamReader(openFileInput("ficSon")));
            StringBuffer buffer = new StringBuffer();
            while((sonJoueurString = input.readLine())!= null)
            {
                buffer.append(sonJoueurString + eo1);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (input != null)
            {
                try
                {
                    input.close();
                }
                catch (IOException IOE)
                {
                    IOE.printStackTrace();
                }
            }
        }
        sonJoueurBool = Boolean.parseBoolean(sonJoueurString);


        //ici, on regarde le contenu du fichier pour le son de l'environnement et on remplit la variable avec ce contenu
        String eo2 = System.getProperty("line.separator");
        BufferedReader input2 = null;
        try
        {
            input2 = new BufferedReader(new InputStreamReader(openFileInput("ficEnv")));
            StringBuffer buffer = new StringBuffer();
            while((sonEnvironnementString = input2.readLine())!= null)
            {
                buffer.append(sonJoueurString + eo2);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (input2 != null)
            {
                try
                {
                    input2.close();
                }
                catch (IOException IOE)
                {
                    IOE.printStackTrace();
                }
            }
        }
        sonEnvironnementBool = Boolean.parseBoolean(sonEnvironnementString);


        //ici, on regarde le contenu du fichier pour la langue et on remplit la variable avec ce contenu
        String eo3 = System.getProperty("line.separator");
        BufferedReader input3 = null;
        try
        {
            input3 = new BufferedReader(new InputStreamReader(openFileInput("ficLangue")));
            StringBuffer buffer = new StringBuffer();
            while((langue = input3.readLine())!= null)
            {
                buffer.append(sonJoueurString + eo3);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (input3 != null)
            {
                try
                {
                    input3.close();
                }
                catch (IOException IOE)
                {
                    IOE.printStackTrace();
                }
            }
    }

        choixLangue = (Spinner) findViewById(R.id.spinner);
        choixLangue.setOnItemSelectedListener (new LangueChoisie());
        //Remplissage de la liste déroulante avec le tableau de chaines de caractères du fichier strings.xml
        ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.listeLangues,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choixLangue.setAdapter(adapter);

        sonJoueur = (CheckBox) findViewById(R.id.sonJoueur);
        sonJoueur.setOnCheckedChangeListener (new SonJoueurListener());

        sonEnvironnement = (CheckBox) findViewById(R.id.sonJeu);
        sonEnvironnement.setOnCheckedChangeListener (new SonEnvironnementListener());
    }

    public class LangueChoisie implements OnItemSelectedListener
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            langue = choixLangue.getSelectedItem().toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {
            // TODO Auto-generated method stub
            //Pas besoin de faire quoi que ce soit, cette fonction a été créée pour pallier à la possibilité "aucun item sélectionné" et éviter une erreur de compilation
        }
    }

    public class SonJoueurListener implements OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged (CompoundButton buttonView, boolean nouveauEtatJoueur)
        {
            sonJoueurBool = nouveauEtatJoueur;
        }
    }

    public class SonEnvironnementListener implements OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged (CompoundButton buttonView, boolean nouveauEtatEnvironnement)
        {
            sonEnvironnementBool = nouveauEtatEnvironnement;
        }
    }
}
