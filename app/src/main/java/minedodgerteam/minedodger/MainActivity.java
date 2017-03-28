package minedodgerteam.minedodger;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import static android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION;


/**
 *Main activity de l'application
 * @author Antoine
 */
public class MainActivity extends AppCompatActivity {

    ImageView myImageView;
    TextView score;
    TextView textCases;
    int nbCasesJoueur = 0;

    Point size = new Point();

    Bitmap.Config conf = Bitmap.Config.ARGB_8888;
    Bitmap drawingBitmap;
    Canvas drawingCanvas;

    Paint lines = new Paint();
    Paint lastCase = new Paint();
    Paint playerColor = new Paint();
    Paint erase = new Paint();
    Paint mineColor = new Paint();

    float x1,x2;
    float y1,y2;

    boolean canMove = true;

    int playerX, playerY;

    int width, height;

    int columns = 7; int rows = 7;

    int level = 1;
    int nbMines = 4;

    public boolean sonJoueurBool; //etat du son du joueur
    public boolean sonExplosionBool; //etat du son des explosions
    public boolean sonLvlUpBool; //etat du son du passage au niveau suivant
    public boolean sonMusiqueBool; //etat de la musique

    public String sonJoueurString;
    public String sonMusiqueString;
    public String sonExplosionString;
    public String sonLvlUpString;
    public String langue;
    public String highscoreString;

    int highscore;

    Integer[][] minesTab;

    @Override
    /**
     * @author Antoine
     * @version 1
     *
     * Cette fonction est appelée à chaque création de l'instance de l'application
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ce code est utilisé lorsque l'instance de l'application est créée.

        // On met le fond de l'appli en blanc
        myImageView = (ImageView) findViewById(R.id.image);
        myImageView.setBackgroundColor(Color.WHITE);

        score = (TextView) findViewById(R.id.text);
        score.setText("Score : "+level);

        textCases = (TextView) findViewById(R.id.text2);
        textCases.setText ("Nombre de cases parcourues : "+nbCasesJoueur);

        Display display = getWindowManager().getDefaultDisplay();

        // On récupère la taille de l'écran
        display.getSize(size);
        width = size.x;
        height = size.y;

        // On initialise le tableau
        minesTab = new Integer[20][20];
        initTab();

        // On initialise la position du joueur
        playerX = columns - 1;
        playerY = rows - 1;

        // Définition du bitmap et du canvas qui va dessiner sur le bitmap
        drawingBitmap = Bitmap.createBitmap(width, height, conf);

        drawingCanvas = new Canvas(drawingBitmap);
        drawingCanvas.drawBitmap(drawingBitmap, 0, 0, null);

        // Définition des couleurs que nous allons utiliser
        lines.setColor(Color.BLACK);
        lastCase.setColor(Color.GREEN);
        playerColor.setColor(Color.BLUE);
        erase.setColor(Color.WHITE);
        mineColor.setColor(Color.RED);

        // On dessine la grille initiale
        for (int i = 0; i <= width; i += width / columns) {
            drawingCanvas.drawLine(i, 0, i, height, lines);
        }
        for (int j = 0; j <= height; j += height / rows) {
            drawingCanvas.drawLine(0, j, width, j, lines);
        }

        // On dessine la dernière case et le joueur
        drawingCanvas.drawRect(0, 0, width / columns, height / rows, lastCase);

        drawPlayer(playerColor);
        // On génère le chemin et pose les mines
        while (!generationChemin());
        setMines();

        drawMines(mineColor);
        //drawPath();

        myImageView.setImageBitmap(drawingBitmap);

        alert("Pour les prochains niveaux, les mines vont disparaitre après 3s. \n" +
                "Bonne chance. ");



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


        //ici, on regarde le contenu du fichier pour le son de l'environnement et on remplit la variable avec ce contenu
        String eo6 = System.getProperty("line.separator");
        BufferedReader input6 = null;
        try
        {
            input6 = new BufferedReader(new InputStreamReader(openFileInput("ficHighscore")));
            StringBuffer buffer = new StringBuffer();
            while((highscoreString = input6.readLine())!= null)
            {
                buffer.append(highscoreString + eo6);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (input6 != null)
            {
                try
                {
                    input6.close();
                }
                catch (IOException IOE)
                {
                    IOE.printStackTrace();
                }
            }
        }
        highscore = Integer.parseInt(highscoreString);

    }

    // onTouchEvent () method gets called when User performs any touch event on screen
    // Method to handle touch event like left to right swap and right to left swap
    /**
     * Cette fonction est une surcharge d'une fonction basique d'Android
     * Elle est modifiée pour nous permettre de gérer les "swipes"
     *
     * @params touchevent
     *                  L'évènement qui va gérer l'appui sur l'écran
     * @return Return un booléen, true quand l'écran est appuyé, false sinon
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean onTouchEvent(MotionEvent touchevent)
    {
        switch (touchevent.getAction())
        {
            // when user first touches the screen we get x and y coordinate
            case MotionEvent.ACTION_DOWN:
            {
                x1 = touchevent.getX();
                y1 = touchevent.getY();
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                x2 = touchevent.getX();
                y2 = touchevent.getY();
                if (canMove) {
                    //if left to right sweep event on screen
                    if (x2 - x1 >= width / 5) {
                        if (playerX < columns - 1) {
                            drawPlayer(erase);
                            playerX += 1;
                            drawPlayer(playerColor);
                            nbCasesJoueur++;
                            textCases.setText ("Nombre de cases parcourues : "+nbCasesJoueur);

                        }
                        break;
                    } else if (x1 - x2 >= width / 5) // if right to left sweep event on screen
                    {
                        if (playerX > 0) {
                            drawPlayer(erase);
                            playerX -= 1;
                            drawPlayer(playerColor);
                            nbCasesJoueur++;
                            textCases.setText ("Nombre de cases parcourues : "+nbCasesJoueur);

                        }
                        break;
                    } else if (y2 - y1 >= height / 10) // if UP to Down sweep event on screen
                    {
                        if (playerY < rows - 1) {
                            drawPlayer(erase);
                            playerY += 1;
                            drawPlayer(playerColor);
                            nbCasesJoueur++;
                            textCases.setText ("Nombre de cases parcourues : "+nbCasesJoueur);

                        }
                        break;
                    } else if (y1 - y2 >= height / 10) //if Down to UP sweep event on screen
                    {
                        if (playerY > 0) {
                            drawPlayer(erase);
                            playerY -= 1;
                            drawPlayer(playerColor);
                            nbCasesJoueur++;
                            textCases.setText ("Nombre de cases parcourues : "+nbCasesJoueur);

                        }
                        break;
                    }
                    break;
                }
            }
        }
        if (minesTab[playerX][playerY] == -1){
            if (sonExplosionBool)
            {
                AudioAttributes attributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build();
                SoundPool soundPool = new SoundPool.Builder()
                        .setAudioAttributes(attributes)
                        .build();
                int son = soundPool.load("Boom.wav", 1);
                soundPool.play(son, 1, 1, 0, 0, 1);
                soundPool.release();
            }
            if (level > highscore)
            {
                highscore = level;
                //Maintenant, il faut écrire dans le fichier
                FileOutputStream output;
                try
                {
                    output = openFileOutput("ficHighscore", MODE_PRIVATE);
                    output.write(highscore);
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
            alert("Perdu ! Tu as marché sur une mine ! \n Clique ici pour recommencer ! \n Ton score est "+level+" \n Ton meilleur score est "+highscore);
            restartActivity();
        }

        if (playerX == 0 && playerY == 0){
            level++;
            if (sonLvlUpBool)
            {
                AudioAttributes attributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build();
                SoundPool soundPool = new SoundPool.Builder()
                        .setAudioAttributes(attributes)
                        .build();
                int son = soundPool.load("Level_passed.wav", 1);
                soundPool.play(son, 1, 1, 0, 0, 1);
                soundPool.release();
            }
            // Augmenter colonne et ligne en fonction du niveau et nombre de bombes
            // à faire

            if (((mod(level, 5) == 0) && (nbMines < 15)) || (level < 8)){
                nbMines++;
            }

            if ((mod(level, 10) == 0) && (columns <= 20)){
                columns++;
                rows++;
            }
            // Clean du canvas
            drawingCanvas.drawColor(Color.WHITE);

            // Repositionnement du joueur
            playerX = columns - 1;
            playerY = rows - 1;

            // Etat initial du jeu à nouveau
            initTab();
            drawingCanvas.drawRect(0, 0, width / columns, height / rows, lastCase);
            drawPlayer(playerColor);
            while(!generationChemin());
            setMines();
            drawMines(mineColor);
            //drawPath();
            score.setText("Score : "+level);
            textCases.setText ("Nombre de cases parcourues : "+nbCasesJoueur);



            // On redessine les lignes
            for (int i = 0; i <= width; i += width / columns) {
                drawingCanvas.drawLine(i, 0, i, height, lines);
            }

            for (int j = 0; j <= height; j += height / rows) {
                drawingCanvas.drawLine(0, j, width, j, lines);
            }

            // On affiche les modifications
            myImageView.setImageResource(android.R.color.transparent);
            myImageView.setImageBitmap(drawingBitmap);

            canMove = false;
        }

        if (!(canMove)){
            eraseMines();
        }

        // On redessine les lignes
        for (int i = 0; i <= width; i += width / columns) {
            drawingCanvas.drawLine(i, 0, i, height, lines);
        }

        for (int j = 0; j <= height; j += height / rows) {
            drawingCanvas.drawLine(0, j, width, j, lines);
        }

        // On affiche les modifications
        myImageView.setImageResource(android.R.color.transparent);
        myImageView.setImageBitmap(drawingBitmap);

        return false;
    }
    // Fonction qui permet de placer les mines dans le tableau

    /**
     * Permet de placer les mines dans notre tableau
     */
    public void setMines(){
        Random r = new Random();
        boolean minePosee;

        for (int i = 0; i < nbMines; i++) {
            minePosee = false;
            while (!minePosee) {
                int xMine = r.nextInt(columns);
                int yMine = r.nextInt(rows);

                if (minesTab[xMine][yMine] == 0) {
                    minesTab[xMine][yMine] = -1;
                    minePosee = true;
                }
            }
        }

    }

    /**
     * Permet d"intialiser les valeurs du tableau à 0
     */
    public void initTab(){

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                minesTab[i][j] = 0;
            }
        }

        minesTab[0][0] = 2;

        minesTab[columns - 1][rows - 1] = 1;


    }

    /**
     * Fonction qui génère un chemin aléatoire
     * @return Retourne un booléen, true si le chemin trouvé est valide, false sinon.
     */
    public boolean generationChemin(){
        int x = columns - 1;
        int y = rows - 1;
        int newX, newY;
        int tailleMax = columns * rows - nbMines;
        int nbCasesParcourues = 1;
        Random r = new Random();
        int direction;
        while (true){
            direction = r.nextInt(100);
            if (direction < 40){
                // Left 40%
                newX = x-1;
                newY = y;
            } else if(direction < 75) {
                // Up 35%
                newX = x;
                newY = y-1;
            } else {
                // Right 25%
                newX = x+1;
                newY = y;
            }
            // Si la case existe
            if(newX >= 0 && newX < columns && newY >= 0){
                x = newX;
                y = newY;
                if (minesTab[x][y] != 1) {
                    minesTab[x][y] = 1;
                    nbCasesParcourues++;
                }
            }
            if(x == 0 && y == 0)
                break;
        }
        return (nbCasesParcourues < tailleMax);
    }

    // Fonction qui permet d'afficher les mines dans le tableau

    /**
     * Cette fonction nous permet d'afficher les mines du tableau
     * @param color
     *          La couleur renseignée permet d'afficher ou effacer les mines
     */
    public void drawMines(Paint color){
        for (int i=0; i<20; i++){
            for (int j=0; j<20; j++){
                if (minesTab[i][j] == -1) {

                    drawingCanvas.drawRect(i * (width / columns), j * (height / rows),
                            i * (width / columns) + (width / columns),
                            j * (height / rows) + (height / rows),
                            color);
                }
            }
        }
    }

    /**
     * Cette fonction permet de tester si le chemin est créé en affichant le chemin
     * Seulement utile en période de développement.
     */
    public void drawPath(){
        for (int i=0; i<20; i++){
            for (int j=0; j<20; j++){
                if (minesTab[i][j] == 1) {

                    drawingCanvas.drawRect(i * (width / columns), j * (height / rows),
                            i * (width / columns) + (width / columns),
                            j * (height / rows) + (height / rows),
                            lastCase);
                }
            }
        }
    }

    /**
     * Cette fonction permet d'effacer les mines après 3 secondes
     * @see #drawMines(Paint)
     */
    public void eraseMines(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                drawMines(erase);
                // On affiche les lignes à nouveau pour corriger un bug d'affichage
                for (int i = 0; i <= width; i += width / columns) {
                    drawingCanvas.drawLine(i, 0, i, height, lines);
                }
                for (int j = 0; j <= height; j += height / rows) {
                    drawingCanvas.drawLine(0, j, width, j, lines);
                }
                // affichage des modifs
                myImageView.setImageBitmap(drawingBitmap);
                // Le joueur peut se déplacer
                canMove = true;
            }
        }, 3000);
    }

    /**
     * Cette fonction permet d'afficher le joueur à l'écran
     * @param color
     *          La couleur permet d'afficher le joueur de la couleur voulue
     *          Bleue pour afficher le joueur
     *          Blanche pour l'effacer
     */
    public void drawPlayer(Paint color){
        drawingCanvas.drawRect(playerX * (width/columns), playerY * (height/rows),
                playerX * (width/columns) + (width/columns),
                playerY * (height/rows) + (height/rows),
                color);
    }

    /**
     * Permet l'utilisation de modulo
     * @param x
     * @param y
     * @return Renvoie 0 si y est un diviseur de x
     */
    private int mod(int x, int y)
    {
        int result = x % y;
        return result < 0? result + y : result;
    }

    /**
     * Permet d'afficher un popup à l'écran
     * @param message
     *          String du message à afficher
     */
    private void alert(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
                throw new RuntimeException();
            }
        });
        builder.create().show();
        try
        {
            Looper.loop();
        }
        catch (RuntimeException ex)
        {
        }
    }

    /**
     * Permet de relancer l'activité contenant l'écran de jeu
     * Est appelé lors de la défaite du joueur et remplace restartApp()
     * @see #restartApp()
     */
    private void restartActivity(){
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Permet de relancer l'application
     * This function is deprecated
     * @see #restartActivity()
     */
    private void restartApp(){
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

}
