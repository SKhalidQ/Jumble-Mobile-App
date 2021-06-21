package Sad.Khalid.Qayyum.ACW.PuzzleDisplay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import Sad.Khalid.Qayyum.ACW.PuzzleLogic.Puzzle;
import Sad.Khalid.Qayyum.ACW.R;

public class PuzzleMenuAdapter extends ArrayAdapter {
    private Context mContext;
    private List<Puzzle> mPuzzleList;

    public PuzzleMenuAdapter(@NonNull Context pContext, ArrayList<Puzzle> pList) {
        super(pContext, 0, pList);
        mContext = pContext;
        mPuzzleList = pList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listPuzzle = convertView;
        if (listPuzzle == null) {
            listPuzzle = LayoutInflater.from(mContext).inflate(R.layout.puzzle_menu_layout, parent, false);
        }
        Puzzle currentPuzzle = mPuzzleList.get(position);

        TextView name = (TextView) listPuzzle.findViewById(R.id.textView_puzzleName);
        name.setText(currentPuzzle.getPuzzleName());

        return listPuzzle;
    }
}
