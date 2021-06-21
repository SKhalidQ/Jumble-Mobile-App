package Sad.Khalid.Qayyum.ACW.PuzzleLogic;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Sad.Khalid.Qayyum.ACW.PuzzleDisplay.PuzzleGridAdapter;
import Sad.Khalid.Qayyum.ACW.PuzzleDownload.PuzzlesRepository;
import Sad.Khalid.Qayyum.ACW.R;

public class PlayGameActivity extends AppCompatActivity {

    private int currentPosition = -1;
    GridView mGridView;
    PuzzlesRepository mPuzzleRepository;

    //Layout is currently hard coded
    private int[] GridLayoutArray = {0, 2, 1, 3, 5, 6, 7, 10, 8, 9, 11, 4};
    private int[] GridLayoutComplete = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    private String[] CurrentGridLayout = new String [11];
    private int moves = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        mPuzzleRepository = PuzzlesRepository.getInstance(getApplicationContext());
        Puzzle pPuzzle = mPuzzleRepository.getPuzzleTemp(getIntent().getExtras().getInt("index"));
        PuzzleGridAdapter adapter = new PuzzleGridAdapter(getApplicationContext(), pPuzzle);

        mGridView = findViewById(R.id.puzzleGridLayoutFront);
        //mGridView = findViewById(R.id.puzzleGridLayoutBack); //For 2 picture sets
        mGridView.setAdapter(adapter);

        //region Gesture Methods

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changePosition(position);
            }
        });

        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), getString(R.string.debugString), Toast.LENGTH_LONG).show();
                rotatedImage(view, position);

                return true;
            }
        });
        //endregion
    }

    //region Puzzle Playability Methods

    public void changePosition(int position) {

        Puzzle pPuzzle = mPuzzleRepository.getPuzzleTemp(getIntent().getExtras().getInt("index"));

        if(currentPosition < 0) {
            currentPosition = GridLayoutArray[position];
        }
        else {
            Bitmap swapImage = pPuzzle.getImageList().get(GridLayoutArray[position]);
            pPuzzle.getImageList().set(GridLayoutArray[position], pPuzzle.getImageList().get(currentPosition));
            pPuzzle.getImageList().set(currentPosition, swapImage);
            currentPosition = -1;
            PuzzleGridAdapter adapter = new PuzzleGridAdapter(getApplicationContext(), pPuzzle);
            mGridView.setAdapter(adapter);
            moves += 1;
            adapter.notifyDataSetChanged();

            if(moves == 6) {
                Toast.makeText(getApplicationContext(), getString(R.string.debugString) + " " + moves, Toast.LENGTH_LONG).show();
            }
            if (isCompleted()) Toast.makeText(getApplicationContext(), "Completed", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean rotatedImage(View view, int position) {

        //region Rotation Animation
//        ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(view, "rotation", 0f, 180f);
//        rotateAnimation.setDuration(1000);
//
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playTogether(rotateAnimation);
//        animatorSet.start();
        //endregion

        view.setRotation(view.getRotation() + 180);

        return true;
    }

    private boolean isCompleted() {
        boolean solved = false;
        for (int i = 0; i < GridLayoutArray.length; i++) {
            if (GridLayoutArray[i] == GridLayoutComplete[i]) {
                solved = true;
            } else {
                solved = false;
                break;
            }
        }
        return solved;
    }

//endregion
}
