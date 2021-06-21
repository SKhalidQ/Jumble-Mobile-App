package Sad.Khalid.Qayyum.ACW.MenusFolder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import Sad.Khalid.Qayyum.ACW.R;

public class HighScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
    }

    @Override
    protected void onPause() {
        super.onPause();
        setContentView(R.layout.activity_high_score);
        Log.i("Activity Lifecycle", "onPause");
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_high_score);
        Log.i("Activity Lifecycle", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_high_score);
        Log.i("Activity Lifecycle", "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        setContentView(R.layout.activity_high_score);
        Log.i("Activity Lifecycle", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setContentView(R.layout.activity_high_score);
        Log.i("Activity Lifecycle", "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setContentView(R.layout.activity_high_score);
        Log.i("Activity Lifecycle", "onRestart");
    }
}
