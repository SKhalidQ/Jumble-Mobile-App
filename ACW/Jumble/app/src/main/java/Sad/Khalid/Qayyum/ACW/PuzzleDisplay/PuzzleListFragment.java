package Sad.Khalid.Qayyum.ACW.PuzzleDisplay;

import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import Sad.Khalid.Qayyum.ACW.MenusFolder.PuzzleActivity;
import Sad.Khalid.Qayyum.ACW.MenusFolder.SelectedPuzzleFragment;
import Sad.Khalid.Qayyum.ACW.PuzzleLogic.Puzzle;
import Sad.Khalid.Qayyum.ACW.R;

public class PuzzleListFragment extends ListFragment {
    int mCurCheckPosition = 0;
    boolean mSingleActivity;
    MyViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);
        final Observer<List<Puzzle>> puzzleObserver = new Observer<List<Puzzle>>(){
            @Override
            public void onChanged(@Nullable final List<Puzzle> puzzle){
                PuzzleMenuAdapter puzzleMenuAdapter = new PuzzleMenuAdapter(getActivity(), mViewModel.getPuzzles().getValue());
                setListAdapter(puzzleMenuAdapter);
            }
        };
        mViewModel.getPuzzles().observe(this, puzzleObserver);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1));

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        View contentFrame = getActivity().findViewById(R.id.content);

        mSingleActivity = contentFrame != null && contentFrame.getVisibility() == View.VISIBLE;

        if(savedInstanceState != null) {
            //restore last state for check position.
            mCurCheckPosition = savedInstanceState.getInt("outChoice, 0");
        }

        if (mSingleActivity) {
            showContent(mCurCheckPosition);
        }
        else {
            getListView().setItemChecked(mCurCheckPosition, true);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        mViewModel.selectPuzzle(position);
        showContent(position);
    }

    void showContent(int index) {
        mCurCheckPosition = index;

        if(mSingleActivity) {
            getListView().setItemChecked(index, true);

            //Check what fragment is currently showing, replace if needed.
            SelectedPuzzleFragment content = (SelectedPuzzleFragment) getFragmentManager().findFragmentById(R.id.content);
            if(content == null || content.getShowIndex() != index) {
                //Make new fragment to show this selection.
                content = SelectedPuzzleFragment.newInstance(index);

                //Execute a transaction, replacing any existing fragment with this one inside the frame.
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content, content);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        }
        else {
            //Create an intent for starting the DetailsActivity.
            Intent intent = new Intent();

            //Explicitly set the activity content an class associated with the intent (context, class)
            intent.setClass(getActivity(), PuzzleActivity.class);

            //Pass the current position
            intent.putExtra("index", index);

            startActivity(intent);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("curChoice", mCurCheckPosition);
    }

}


