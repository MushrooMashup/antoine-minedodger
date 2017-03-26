package minedodgerteam.minedodger;



import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.lang.String;
import java.io.FileNotFoundException;




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
        /**
         * @author Eric Longueville
         *
         * Cette fonction est appellée lorsque le joueur clique sur Options et affiche la page d'options avec ses composants initialisés
         */
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
            /**
             * @author Eric Longueville
             *
             * Cette fonction est appellée lorsque le joueur choisit une autre langue et inscrit son choix dans un fichier nommé ficLangue
             */
            langue = choixLangue.getSelectedItem().toString();
            //Maintenant, il faut écrire dans le fichier
            FileOutputStream output;
            try
            {
                output = openFileOutput("ficLangue", MODE_PRIVATE);
                output.write(langue.getBytes());
                if(output != null)
                    output.close();
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {
            /**
             * @author Eric Longueville
             *
             * Cette fonction a été créée pour pallier à la possibilité "aucun item sélectionné" et éviter une erreur de compilation
             */
            // TODO Auto-generated method stub
        }
    }

    public class SonJoueurListener implements OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged (CompoundButton buttonView, boolean nouveauEtatJoueur)
        {
            /**
             * @author Eric Longueville
             *
             * Cette fonction est appellée lorsque le joueur coche ou décoche l'option correspondant au son de son personnage et inscrit son choix dans un fichier nommé ficLangue
             */
            sonJoueurBool = nouveauEtatJoueur;
            //Maintenant, il faut écrire dans le fichier
            FileOutputStream output;
            try
            {
                output = openFileOutput("ficSon", MODE_PRIVATE);
                output.write(String.valueOf(sonJoueurBool).getBytes());
                if(output != null)
                    output.close();
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public class SonEnvironnementListener implements OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged (CompoundButton buttonView, boolean nouveauEtatEnvironnement)
        {
            /**
             * @author Eric Longueville
             *
             * Cette fonction est appellée lorsque le joueur coche ou décoche l'option correspondant au son de l'environnement et inscrit son choix dans un fichier nommé ficLangue
             */
            sonEnvironnementBool = nouveauEtatEnvironnement;
            //Maintenant, il faut écrire dans le fichier
            FileOutputStream output;
            try
            {
                output = openFileOutput("ficEnv", MODE_PRIVATE);
                output.write(String.valueOf(sonEnvironnementBool).getBytes());
                if(output != null)
                    output.close();
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
