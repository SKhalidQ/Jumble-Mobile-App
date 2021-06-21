package Sad.Khalid.Qayyum.ACW.PuzzleDisplay;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import Sad.Khalid.Qayyum.ACW.PuzzleLogic.Puzzle;
import Sad.Khalid.Qayyum.ACW.R;

public class PuzzleGridAdapter extends BaseAdapter {
    private Context mContext;
    private Puzzle mPuzzle;
    ArrayList<Bitmap> mImages;


    //Eventually turn it into the Array list from Layout.json
    private int[] GridLayoutArray = {0, 2, 1, 3, 5, 6, 7, 10, 8, 9, 11, 4}; //Comment this in order to get both picture sets

    public PuzzleGridAdapter(Context mContext, Puzzle pPuzzle) {
        this.mContext = mContext;
        this.mPuzzle = pPuzzle;
        this.mImages = this.mPuzzle.getImageList();
    }

    public WindowManager getWindowManager() {
        return getWindowManager();
    }


    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public Object getItem(int position) {
        return mImages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listPuzzle = convertView;

        if(listPuzzle == null) {
            listPuzzle = LayoutInflater.from(mContext).inflate(R.layout.activity_puzzle, parent, false);
        }

        ImageView imageView = new ImageView(mContext);
        imageView.setImageBitmap(mImages.get(GridLayoutArray [position])); //Comment this for 2 picture sets
        //imageView.setImageBitmap(mImages.get(position)); //Use this is for 2 picture sets
        imageView.setMinimumHeight(400);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageBitmap(mImages.get(GridLayoutArray [position])); //Again, comment this for 2 picture sets
        //imageView.setImageBitmap(mImages.get(position)); //Again, for 2 picture sets
        return imageView;
    }
}
