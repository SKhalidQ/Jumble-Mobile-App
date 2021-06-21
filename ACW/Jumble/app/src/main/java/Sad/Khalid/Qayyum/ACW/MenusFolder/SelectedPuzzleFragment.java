package Sad.Khalid.Qayyum.ACW.MenusFolder;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import Sad.Khalid.Qayyum.ACW.PuzzleDisplay.MyViewModel;
import Sad.Khalid.Qayyum.ACW.PuzzleLogic.Puzzle;
import Sad.Khalid.Qayyum.ACW.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectedPuzzleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectedPuzzleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_INDEX = "index";
    private int mIndex;
    MyViewModel mViewModel;
    View mInflatedView;

    public int getShowIndex(){
        return mIndex;
    }

    public SelectedPuzzleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param index Parameter 1 is the index of the content data we want to display.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectedPuzzleFragment newInstance(int index) {
        SelectedPuzzleFragment fragment = new SelectedPuzzleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIndex = getArguments().getInt(ARG_INDEX);
        }
        mViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);
        mViewModel.selectPuzzle(mIndex);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(this.getClass().getSimpleName() + " Observer", "onCreateView");
        mInflatedView = inflater.inflate(R.layout.fragment_selected_puzzle, container, false);
        //Create the observer which updated the UI.
        final Observer<Puzzle> puzzleObserver = new Observer<Puzzle>(){
            @Override
            public void onChanged(@Nullable final Puzzle puzzle){
                TextView text = (TextView) mInflatedView.findViewById(R.id.listPuzzleTextView);
                text.setText(puzzle.getPuzzleName());
                text.setTextSize(40);
            }
        };

        mViewModel.getSelectedPuzzle().observe(this, puzzleObserver);
        return mInflatedView;
    }
}
