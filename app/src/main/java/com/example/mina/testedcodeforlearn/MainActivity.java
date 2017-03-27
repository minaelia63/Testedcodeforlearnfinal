package com.example.mina.testedcodeforlearn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Model.GridItem;
import adapter.GridViewAdapter;
import adapter.GridViewadapter2;

public class MainActivity extends AppCompatActivity {
    boolean haveConnectedWifi = false;
    boolean haveConnectedMobile = false;

        private static final String TAG = MainActivity.class.getSimpleName();
        private GridView mGridView;
        private ProgressBar mProgressBar;
        private GridViewAdapter mGridAdapter;
    //added
    private GridViewadapter2 mGridAdapter2;
  //  private ArrayList<GridItem> mGridData2;



//
    private ArrayList<String> images;

    private ArrayList<String> names;
    private ArrayList<String> prices;
    private ArrayList<String> height;
    private ArrayList<String> width;
    public static final String TAG_IMAGE_node = "image";
    public static final String TAG_IMAGE_URL = "url";
    public static final String TAG_Productionname = "productDescription";
    public static final String TAG_price = "price";
    public static final String TAG_height = "height";
    public static final String TAG_width = "width";

        private String FEED_URL = "http://grapes-n-berries.getsandbox.com/new_products?count=15&from=2";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            mGridView = (GridView) findViewById(R.id.gridView);
           // mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

            //Initialize with empty data
            images = new ArrayList<>();
            names = new ArrayList<>();
            prices=new ArrayList<>();
            height=new ArrayList<>();
            width=new ArrayList<>();
            //Start download
          ////  new AsyncHttpTask().execute(FEED_URL);
          //  mProgressBar.setVisibility(View.VISIBLE);

            //added

            mGridAdapter2 = new GridViewadapter2(getApplicationContext());

            //
            getData();
         /*   mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                    //Get item at position
                    GridItem item = (GridItem) parent.getItemAtPosition(position);

                    //Pass the image title and url to DetailsActivity
                    Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                    intent.putExtra("title", item.getTitle());
                    intent.putExtra("image", item.getImage());
                    intent.putExtra("price", item.getPrice());

                    //Start details activity
                    startActivity(intent);
                }
            });*/

            //added



         //   mGridData2 = new ArrayList<>();
          ////  mGridAdapter2 = new GridViewadapter2(this, R.layout.grid_item_layout, mGridData2);
         //   mGridView.setAdapter(mGridAdapter2);
            //

            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    //Pass the image title and url to DetailsActivity
                    Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                    intent.putExtra("title", names.get(position));
                    intent.putExtra("image", images.get(position));
                   intent.putExtra("price", prices.get(position));
                    intent.putExtra("width", width.get(position));
                    intent.putExtra("height", height.get(position));

                    //Start details activity
                    startActivity(intent);
                }
            });

        }

    private void getData(){
        //Showing a progress dialog while our app fetches the data from url
        final ProgressDialog loading = ProgressDialog.show(this, "Please wait...","Fetching data...",false,false);

if(haveNetworkConnection()) {


    //Creating a json array request to get the json from our api
    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(FEED_URL,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    //Dismissing the progressdialog on response
                    loading.dismiss();

                    //Displaying our grid
                    showGrid(response);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
    );

    //Creating a request queue
    RequestQueue requestQueue = Volley.newRequestQueue(this);
    //Adding our request to the queue
    requestQueue.add(jsonArrayRequest);
}
        else
{
    Toast.makeText(this, "there is not internet", Toast.LENGTH_SHORT).show();
    loading.dismiss();
}
    }


    private void showGrid(JSONArray jsonArray){
        //Looping through all the elements of json array
        for(int i = 0; i<jsonArray.length(); i++){
            //Creating a json object of the current index
            JSONObject obj = null;


            try {
                //added
            //    GridItem item = null;
                GridItem g=new GridItem();
                //
                //getting json object from current index
                obj = jsonArray.getJSONObject(i);

                //getting image url and title from json object
                //images.add(obj.getString(TAG_IMAGE_URL));
                names.add(obj.getString(TAG_Productionname));

                prices.add(obj.getString(TAG_price));
                //node in json
                JSONObject node = obj.getJSONObject(TAG_IMAGE_node);
                images.add(node.getString(TAG_IMAGE_URL));
                height.add(node.getString(TAG_height));
                width.add(node.getString(TAG_width));

//added
            //    String price2=obj.getString(TAG_price);
              //  String name2=obj.getString(TAG_Productionname);
             //   String image2=node.getString(TAG_IMAGE_URL);
                    g.setImage(node.getString(TAG_IMAGE_URL));
                g.setPrice(obj.getString(TAG_price));
                g.setTitle(obj.getString(TAG_Productionname));
//sout
                System.out.println(node.getString(TAG_IMAGE_URL));
                mGridAdapter2.additem(g);
             //   item.setPrice(price2);
            //    item.setTitle  (name2);
           //     item.setImage(image2);

           //     mGridData2.add(item);
                //


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //added
     //   mGridAdapter2.setGridData(mGridData2);
            mGridView.setAdapter(mGridAdapter2);
        //added
        //Creating GridViewAdapter Object
       //mGridAdapter = new GridViewAdapter(this,images,names,prices);


        //Adding adapter to gridview
       // mGridView.setAdapter(mGridAdapter);

    }
    private boolean haveNetworkConnection() {
       haveConnectedWifi = false;
        haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

}


