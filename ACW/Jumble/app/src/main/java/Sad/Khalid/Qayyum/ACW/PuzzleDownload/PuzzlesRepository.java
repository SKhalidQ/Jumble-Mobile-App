package Sad.Khalid.Qayyum.ACW.PuzzleDownload;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Sad.Khalid.Qayyum.ACW.PuzzleLogic.Puzzle;

public class PuzzlesRepository {

    private static PuzzlesRepository sPuzzlesRepository;
    private Context mApplicationContext;
    private LiveData<ArrayList<Puzzle>> mPuzzles;
    private List<String> puzzleFileNames = new ArrayList<String>();
    private LiveData<Puzzle> mSelectedPuzzle;
    private String url = "https://www.goparker.com/600096/jumble/index.json";
    private RequestQueue mQueue;
    ArrayList<Puzzle> puzzles = new ArrayList<>();
    MutableLiveData<ArrayList<Puzzle>> puzzleList = new MutableLiveData<>();

    private PuzzlesRepository(Context pApplicationContext){

        this.mApplicationContext = pApplicationContext;
        mQueue = Volley.newRequestQueue(mApplicationContext);

    }

    public static PuzzlesRepository getInstance(Context pApplicationContext){
        if (sPuzzlesRepository == null) {
            sPuzzlesRepository = new PuzzlesRepository(pApplicationContext);
        }
        return sPuzzlesRepository;
    }

    public LiveData<Puzzle> getPuzzle(int pPuzzleIndex) {
        LiveData<Puzzle> transformedPuzzle = Transformations.switchMap(mPuzzles, puzzles -> {
            MutableLiveData<Puzzle> puzzleData = new MutableLiveData<>();
            Puzzle puzzle = puzzles.get(pPuzzleIndex);
            puzzleData.setValue(puzzle);
            //loadImage(puzzle.getImageUrl(), puzzleData);
            return puzzleData;
        });

        mSelectedPuzzle = transformedPuzzle;
        return mSelectedPuzzle;
    }

    public Puzzle getPuzzleTemp(int index) {
        return mPuzzles.getValue().get(index);
    }

    public LiveData<ArrayList<Puzzle>> getPuzzles(){
        if(mPuzzles == null) {
            mPuzzles = loadPuzzlesFromJSON();
        }
        return mPuzzles;
    }

    //region Get Puzzle Names
    public LiveData<ArrayList<Puzzle>> loadPuzzlesFromJSON() {
        RequestQueue queue = Volley.newRequestQueue(mApplicationContext);
        final MutableLiveData<ArrayList<Puzzle>> mutablePuzzles = new MutableLiveData<>();

        //Request a jsonObject response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){
                        parseJSONResponse(response);
                        for (int i = 0; i < puzzleFileNames.size(); i++){
                            url = "https://www.goparker.com/600096/jumble/puzzles/" + puzzleFileNames.get(i);
                            loadPuzzleByName();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        queue.add(jsonObjectRequest);
        return puzzleList;
    }

    private void parseJSONResponse(JSONObject pResponse) {
        try {
            JSONArray puzzlesArray = pResponse.getJSONArray("PuzzleIndex");
            for (int i = 0; i < puzzlesArray.length(); i++) {
                String puzzleName = puzzlesArray.getString(i);
                puzzleFileNames.add(puzzleName);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region Get Picture Set
    private void parseJSONPuzzleResponse(JSONObject pResponse) {

        try {
            String puzzleName = pResponse.getString("name");
            String puzzleLayout = pResponse.getString("layout");
            JSONArray pictureSet = pResponse.getJSONArray("PictureSet");
            ArrayList<String> puzzleImageList = new ArrayList<>();

            for (int i = 0; i < pictureSet.length(); i++ ){
                String puzzleImages = pictureSet.getString(i);
                puzzleImageList.add(puzzleImages);
            }
            Puzzle puzzle = new Puzzle(puzzleName, puzzleLayout, puzzleImageList);
            puzzle.setPictureSet(puzzleImageList);
            puzzles.add(puzzle);
            loadPuzzleLayout(puzzle);
        }
        catch (JSONException e){

            e.printStackTrace();

        }
    }

    public LiveData<ArrayList<Puzzle>> loadPuzzleByName() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseJSONPuzzleResponse(response);
                        puzzleList.setValue(puzzles);
                        mPuzzles = puzzleList;
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        mQueue.add(jsonObjectRequest);
        return puzzleList;
    }
    //endregion

    //region Get Puzzle Layout
    private void parseJSONLayoutResponse(JSONObject pResponse, Puzzle pPuzzle) {

        try{
            int puzzleRows =  pResponse.getInt("rows");
            int puzzleColumns = pResponse.getInt("columns");
            JSONArray puzzleLayout = pResponse.getJSONArray("layout");
            String[][] puzzleGrid = new String[puzzleRows][puzzleColumns];

            for (int i = 0; i < puzzleLayout.length(); i++ ){
                JSONArray puzzleRowArray = puzzleLayout.getJSONArray(i);
                for (int j = 0; j < puzzleRowArray.length(); j++) {
                    puzzleGrid[i][j] =  puzzleRowArray.getString(j);
                }
            }
            pPuzzle.setRows(puzzleRows);
            pPuzzle.setColumns(puzzleColumns);
            pPuzzle.setGridLayout(puzzleGrid);

            int puzzleImageCount = puzzleRows * puzzleColumns; //Counting how many puzzles I need
            ArrayList<String> pictureSetList = pPuzzle.getPictureSet(); //I have the list of pictureSets

            for (int i = 1; i <= puzzleImageCount; i++ ) {
                String url = "https://goparker.com/600096/jumble/images/" + pictureSetList.get(0) + "/" + i + ".png"; //Get Images from the next URL
                //String url2 = "https://goparker.com/600096/jumble/images/" + pictureSetList.get(1) + "/"  + i + ".png"; //Get the Back of those Images
                loadPuzzleImages(url, true, pPuzzle, i); //Send it to that method with the url, if it is from, object and the image index
                //loadPuzzleImages(url2, false, pPuzzle, i);
            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public LiveData<ArrayList<Puzzle>> loadPuzzleLayout(Puzzle pPuzzle) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url = "https://goparker.com/600096/jumble/layouts/" + pPuzzle.getLayoutName(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseJSONLayoutResponse(response, pPuzzle);
                        puzzleList.setValue(puzzles);
                        mPuzzles = puzzleList;
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        mQueue.add(jsonObjectRequest);
        return puzzleList;
    }
    //endregion

    //region Get Picture Request
    public void loadPuzzleImages(String url, boolean isFront, Puzzle pPuzzle, int index){
            ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                        pPuzzle.addImageToList(response);
                }
            }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            mQueue.add(imageRequest);
    }
    //endregion

}

