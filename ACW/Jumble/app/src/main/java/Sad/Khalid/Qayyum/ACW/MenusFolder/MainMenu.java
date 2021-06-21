package Sad.Khalid.Qayyum.ACW.MenusFolder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import Sad.Khalid.Qayyum.ACW.AcceptSSLCerts;
import Sad.Khalid.Qayyum.ACW.PuzzleDisplay.ListActivity;
import Sad.Khalid.Qayyum.ACW.R;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        AcceptSSLCerts.accept();
    }

    public void openJumble(View view){
        //Respond to the button when pressed
        Intent openJumbleIntent = new Intent(getApplicationContext(), ListActivity.class );
        startActivity(openJumbleIntent);

    }

    public void openHighScore(View view) {
        //Respond to the button when pressed
        Intent openHighScoreIntent = new Intent(getApplicationContext(), HighScoreActivity.class);
        startActivity(openHighScoreIntent);

    }
}
