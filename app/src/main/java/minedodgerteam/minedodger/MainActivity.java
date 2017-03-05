package minedodgerteam.minedodger;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.ImageView;
import java.util.Random;


/**
 *Main activity de l'application
 * @author Antoine
 */
public class MainActivity extends AppCompatActivity {

    ImageView myImageView;

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
        drawPath();

        myImageView.setImageBitmap(drawingBitmap);

        alert("Pour les prochains niveaux, les mines vont disparaitre après 3s. \n" +
                "Bonne chance. ");

    }

    // onTouchEvent () method gets called when User performs any touch event on screen
    // Method to handle touch event like left to right swap and right to left swap
    /**
     * @params touchevent
     *                  L'évènement qui va gérer l'appui sur l'écran
     *
     * Cette fonction va nous permettre de gérer les swipes
     */
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
                        }
                        break;
                    } else if (x1 - x2 >= width / 5) // if right to left sweep event on screen
                    {
                        if (playerX > 0) {
                            drawPlayer(erase);
                            playerX -= 1;
                            drawPlayer(playerColor);
                        }
                        break;
                    } else if (y2 - y1 >= height / 10) // if UP to Down sweep event on screen
                    {
                        if (playerY < rows - 1) {
                            drawPlayer(erase);
                            playerY += 1;
                            drawPlayer(playerColor);
                        }
                        break;
                    } else if (y1 - y2 >= height / 10) //if Down to UP sweep event on screen
                    {
                        if (playerY > 0) {
                            drawPlayer(erase);
                            playerY -= 1;
                            drawPlayer(playerColor);
                        }
                        break;
                    }
                    break;
                }
            }
        }
        if (minesTab[playerX][playerY] == -1){
            alert("Perdu ! Tu as marché sur une mine ! \n    Clique ici pour recommencer !");
            restartApp();
        }

        if (playerX == 0 && playerY == 0){
            level++;
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
            drawPath();

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
        while ((x != 0) && (y != 0)){
            direction = r.nextInt(100);
            if (direction < 40){
                // Left 40%
                newX = x-1;
                newY = y;
            } else if(direction < 80) {
                // Up 40%
                newX = x;
                newY = y-1;
            } else {
                // Right 20%
                newX = x+1;
                newY = y;
            }
            // Si la case existe
            if(minesTab[newX][newY] != null){
                x = newX;
                y = newY;
                if (minesTab[x][y] != 1) {
                    minesTab[x][y] = 1;
                    nbCasesParcourues++;
                }
            }
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
     * Permet de relancer l'application
     */
    private void restartApp(){
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

}
