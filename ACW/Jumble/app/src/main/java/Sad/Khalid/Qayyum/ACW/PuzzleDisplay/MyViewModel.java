package Sad.Khalid.Qayyum.ACW.PuzzleDisplay;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;

import Sad.Khalid.Qayyum.ACW.PuzzleDownload.PuzzlesRepository;
import Sad.Khalid.Qayyum.ACW.PuzzleLogic.Puzzle;

public class MyViewModel extends AndroidViewModel {
    private LiveData<ArrayList<Puzzle>> mPuzzles;
    private LiveData<Puzzle> mSelectedPuzzle;
    private PuzzlesRepository mPuzzleRepository;
    private int mSelectedIndex;

    public MyViewModel(@NonNull Application pApplication){
        super(pApplication);
        mPuzzleRepository = PuzzlesRepository.getInstance(getApplication());
    }

    public LiveData<ArrayList<Puzzle>> getPuzzles(){
        if(mPuzzles == null){
            mPuzzles = mPuzzleRepository.getPuzzles();
        }
        return mPuzzles;
    }

    public LiveData<Puzzle> getPuzzle(int pPuzzleIndex) {
        return mPuzzleRepository.getPuzzle(pPuzzleIndex);
    }

    public void selectPuzzle(int pIndex) {
        if(pIndex != mSelectedIndex || mSelectedPuzzle == null) {
            mSelectedIndex = pIndex;
            mSelectedPuzzle = getPuzzle(mSelectedIndex);
        }
    }

    public LiveData<Puzzle> getSelectedPuzzle() {
        return mSelectedPuzzle;
    }
}
