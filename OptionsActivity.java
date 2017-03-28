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

    public boolean sonJoueurBool; //etat du son du joueur
    public boolean sonExplosionBool; //etat du son des explosions
    public boolean sonLvlUpBool; //etat du son du passage au niveau suivant
    public boolean sonMusiqueBool; //etat de la musique

    public String langue; //langue de l'application
    public Spinner choixLangue;
    public CheckBox sonJoueur;
    public CheckBox sonExplosion;
    public CheckBox lvlUp;
    public CheckBox sonMusique;

    public String sonJoueurString; //variable utilisée dans le onCreate
    public String sonMusiqueString; //variable utilisée dans le onCreate
    public String sonExplosionString; //variable utilisée dans le onCreate
    public String sonLvlUpString; //variable utilisée dans le onCreate

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
            input2 = new BufferedReader(new InputStreamReader(openFileInput("ficExplo")));
            StringBuffer buffer = new StringBuffer();
            while((sonExplosionString = input2.readLine())!= null)
            {
                buffer.append(sonExplosionString + eo2);
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
        sonExplosionBool = Boolean.parseBoolean(sonExplosionString);



        //ici, on regarde le contenu du fichier pour la langue et on remplit la variable avec ce contenu
        String eo3 = System.getProperty("line.separator");
        BufferedReader input3 = null;
        try
        {
            input3 = new BufferedReader(new InputStreamReader(openFileInput("ficLangue")));
            StringBuffer buffer = new StringBuffer();
            while((langue = input3.readLine())!= null)
            {
                buffer.append(langue + eo3);
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


        //ici, on regarde le contenu du fichier pour le son de l'environnement et on remplit la variable avec ce contenu
        String eo4 = System.getProperty("line.separator");
        BufferedReader input4 = null;
        try
        {
            input4 = new BufferedReader(new InputStreamReader(openFileInput("ficMusique")));
            StringBuffer buffer = new StringBuffer();
            while((sonMusiqueString = input4.readLine())!= null)
            {
                buffer.append(sonMusiqueString + eo4);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (input4 != null)
            {
                try
                {
                   input4.close();
                }
                catch (IOException IOE)
                {
                    IOE.printStackTrace();
                }
            }
        }
        sonMusiqueBool = Boolean.parseBoolean(sonMusiqueString);


        //ici, on regarde le contenu du fichier pour le son de l'environnement et on remplit la variable avec ce contenu
        String eo5 = System.getProperty("line.separator");
        BufferedReader input5 = null;
        try
        {
            input5 = new BufferedReader(new InputStreamReader(openFileInput("ficLvlUp")));
            StringBuffer buffer = new StringBuffer();
            while((sonLvlUpString = input5.readLine())!= null)
            {
                buffer.append(sonLvlUpString + eo5);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (input5 != null)
            {
                try
                {
                    input5.close();
                }
                catch (IOException IOE)
                {
                    IOE.printStackTrace();
                }
            }
        }
        sonLvlUpBool = Boolean.parseBoolean(sonLvlUpString);

        choixLangue = (Spinner) findViewById(R.id.spinner);
        choixLangue.setOnItemSelectedListener (new LangueChoisie());
        //Remplissage de la liste déroulante avec le tableau de chaines de caractères du fichier strings.xml
        ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.listeLangues,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choixLangue.setAdapter(adapter);

        sonJoueur = (CheckBox) findViewById(R.id.sonJoueur);
        sonJoueur.setOnCheckedChangeListener (new SonJoueurListener());

        sonExplosion = (CheckBox) findViewById(R.id.sonExplosion);
        sonExplosion.setOnCheckedChangeListener (new SonExplosionListener());

        sonMusique = (CheckBox) findViewById(R.id.sonMusique);
        sonMusique.setOnCheckedChangeListener (new sonMusiqueListener());

        lvlUp = (CheckBox) findViewById(R.id.sonLvlUp);
        lvlUp.setOnCheckedChangeListener (new SonLvlUpListener());
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

    public class sonMusiqueListener implements OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged (CompoundButton buttonView, boolean nouveauEtatMusique)
        {
            /**
             * @author Eric Longueville
             *
             * Cette fonction est appellée lorsque le joueur coche ou décoche l'option correspondant au son de la musique et inscrit son choix dans un fichier nommé ficLangue
             */
            sonMusiqueBool = nouveauEtatMusique;
            //Maintenant, il faut écrire dans le fichier
            FileOutputStream output;
            try
            {
                output = openFileOutput("ficMusique", MODE_PRIVATE);
                output.write(String.valueOf(sonMusiqueBool).getBytes());
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

    public class SonLvlUpListener implements OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged (CompoundButton buttonView, boolean nouveauEtatLvlUp)
        {
            /**
             * @author Eric Longueville
             *
             * Cette fonction est appellée lorsque le joueur coche ou décoche l'option correspondant au son de passage au niveau suivant et inscrit son choix dans un fichier nommé ficLangue
             */
            sonLvlUpBool = nouveauEtatLvlUp;
            //Maintenant, il faut écrire dans le fichier
            FileOutputStream output;
            try
            {
                output = openFileOutput("ficLvlUp", MODE_PRIVATE);
                output.write(String.valueOf(sonLvlUpBool).getBytes());
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

    public class SonExplosionListener implements OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged (CompoundButton buttonView, boolean nouveauEtatLvlUp)
        {
            /**
             * @author Eric Longueville
             *
             * Cette fonction est appellée lorsque le joueur coche ou décoche l'option correspondant au son des explosions et inscrit son choix dans un fichier nommé ficLangue
             */
            sonExplosionBool = nouveauEtatLvlUp;
            //Maintenant, il faut écrire dans le fichier
            FileOutputStream output;
            try
            {
                output = openFileOutput("ficExplo", MODE_PRIVATE);
                output.write(String.valueOf(sonExplosionBool).getBytes());
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
