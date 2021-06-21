package Sad.Khalid.Qayyum.ACW.PuzzleLogic;

import android.graphics.Bitmap;

import org.json.JSONObject;

import java.util.ArrayList;

public class Puzzle {
    private String mPuzzleName;
    private ArrayList<String> mPictureSet;
    private JSONObject mLayout;
    private String mLayoutName;
    private int mRows;
    private int mColumns;
    private ArrayList<Bitmap> mImageList;
    private String [][] mGridLayout;


    public Puzzle(String pPuzzleName, String pLayoutName, ArrayList<String> pPictureSet) {
        setPuzzleName(pPuzzleName);
        setLayoutName(pLayoutName);
        setPictureSet(pPictureSet);
        mImageList = new ArrayList<Bitmap>();
    }

    public String getPuzzleName(){ return mPuzzleName; }
    public void setPuzzleName (String pPuzzleName) { mPuzzleName = pPuzzleName; }

    public ArrayList<String> getPictureSet() { return mPictureSet; }
    public void setPictureSet (ArrayList<String> pPictureSet) { mPictureSet = pPictureSet; }

    public String getLayoutName() { return mLayoutName; }
    public void setLayoutName(String pLayoutName) { mLayoutName = pLayoutName; }

    public  JSONObject getLayout() { return mLayout; }
    public void setLayout(JSONObject pLayout) { mLayout = pLayout; }

    public int getRows() { return mRows; }
    public void setRows ( int pRows ) { mRows = pRows; }

    public int geColumns() { return mColumns; }
    public void setColumns ( int pColumns) { mColumns = pColumns; }

    public String [][] getGridLayout() { return mGridLayout; }
    public void setGridLayout(String [][] pGridLayout) { mGridLayout = pGridLayout; }

    public ArrayList<Bitmap> getImageList() { return mImageList; }
    public void setImageList(ArrayList<Bitmap> pImageList) { pImageList = mImageList; }
    public void addImageToList(Bitmap bitmap) { mImageList.add(bitmap); }

}

