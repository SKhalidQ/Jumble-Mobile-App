package Sad.Khalid.Qayyum.ACW.MenusFolder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import Sad.Khalid.Qayyum.ACW.PuzzleLogic.PlayGameActivity;
import Sad.Khalid.Qayyum.ACW.R;

public class PuzzleActivity  extends AppCompatActivity {
    private int mIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        //Create fragment.
        SelectedPuzzleFragment content = new SelectedPuzzleFragment();

        //We can just pass the intent extras straight to the fragment.
        content.setArguments(getIntent().getExtras());
        mIndex = getIntent().getExtras().getInt("index");
        //Gets the fragment and adds it to the view (Line 3)
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_puzzle, content).commit();
    }

    public void OpenPuzzle(View view){
        //Respond to the button when pressed
        Intent openPuzzle = new Intent(getApplicationContext(), PlayGameActivity.class );
        openPuzzle.putExtra("index", mIndex);
        startActivity(openPuzzle);

    }
}
