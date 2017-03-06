package minedodgerteam.minedodger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Eric Longueville on 02/03/2017.
 */
public class MenuActivity extends Activity {
     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

         final Button jouerButton = (Button) findViewById(R.id.jouer);

         jouerButton.setOnClickListener(new View.OnClickListener() {

  @Override
  public void onClick(View v) {
    Intent intent = new Intent(MenuActivity.this, MainActivity.class);
    startActivity(intent);
    }
});
    }
}
