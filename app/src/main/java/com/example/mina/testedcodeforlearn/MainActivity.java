package com.example.mina.testedcodeforlearn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import Model.GridItem;
import adapter.GridViewadapter2;

public class MainActivity extends AppCompatActivity {
    boolean haveConnectedWifi = false;
    boolean haveConnectedMobile = false;
    ArrayList<GridItem>lst = new ArrayList<>();

        private static final String TAG = MainActivity.class.getSimpleName();
        private GridView mGridView;


    private GridViewadapter2 mGridAdapter2;

  private ArrayList<GridItem> mProductList;


//
public int currentId=0;
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
    public Handler mHandler;
    public View ftView;
    public boolean isLoading = false;
        private String FEED_URL = "http://grapes-n-berries.getsandbox.com/new_products?count=10&from="+currentId;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            mGridView = (GridView) findViewById(R.id.gridView);


            mHandler = new MyHandler();

            images = new ArrayList<>();
            names = new ArrayList<>();
            prices=new ArrayList<>();
            height=new ArrayList<>();
            width=new ArrayList<>();


            mGridAdapter2 = new GridViewadapter2(getApplicationContext());
            new DownloadTask().execute(FEED_URL);






            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    Intent intent = new Intent(MainActivity.this, Details2Activity.class);

                    intent.putExtra("title", names.get(position));
                    intent.putExtra("image", images.get(position));
                   intent.putExtra("price", prices.get(position));
                    intent.putExtra("width", width.get(position));
                    intent.putExtra("height", height.get(position));


                    startActivity(intent);
                }
            });
            mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                    //Check when scroll to last item in listview, in this tut, init data in listview = 10 item
                    if(view.getLastVisiblePosition() == totalItemCount-1 && mGridView.getCount() >=10 && isLoading == false) {
                        isLoading = true;
                        Thread thread = new ThreadGetMoreData();
                        //Start thread
                        thread.start();
                    }

                }
            });



        }

    private void getData(){
        //Showing a progress dialog while our app fetches the data from url
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setMessage("adding more item");
        loading.show();

if(haveNetworkConnection()) {



    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(FEED_URL,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    loading.dismiss();


                    showGrid2(response);
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

                GridItem g=new GridItem();
                mProductList = new ArrayList<>();
                //

                obj = jsonArray.getJSONObject(i);


                names.add(obj.getString(TAG_Productionname));

                prices.add(obj.getString(TAG_price));
                //node in json
                JSONObject node = obj.getJSONObject(TAG_IMAGE_node);
                images.add(node.getString(TAG_IMAGE_URL));
                height.add(node.getString(TAG_height));
                width.add(node.getString(TAG_width));


                    g.setImage(node.getString(TAG_IMAGE_URL));
                g.setPrice(obj.getString(TAG_price));
                g.setTitle(obj.getString(TAG_Productionname));


                mGridAdapter2.additem(g);

                mProductList.add(g);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


            mGridView.setAdapter(mGridAdapter2);


    }
    //added
    public class DownloadTask extends AsyncTask<String,Integer,String> {


        ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setTitle("Download");
            dialog.setMessage("Downloading...");
            dialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            String urlStr = params[0];

            try {

                URL u = new URL(urlStr);
                BufferedReader br = new BufferedReader(new InputStreamReader(u.openStream()));
                String line = br.readLine();
                StringBuilder sb = new StringBuilder();
                while (line != null) {
                    sb.append(line + "\n");
                    line = br.readLine();
                }

                return sb.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();

            if(haveNetworkConnection()) {


                //Creating a json array request to get the json from our api
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(FEED_URL,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                //Dismissing the progressdialog on response
                                dialog.dismiss();

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
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                //Adding our request to the queue
                requestQueue.add(jsonArrayRequest);
            }
            else
            {
                Toast.makeText(MainActivity.this, "there is not internet", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }


        }
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

    public class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 0:
                    //Add loading view during search processing
                  //  mGridView.addFooterView(ftView);
                    break;
                case 1:
                    //Update data adapter and UI
                    mGridAdapter2.addListItemToAdapter((ArrayList<GridItem>)msg.obj);
                    //Remove loading view after update listview
                  //  mGridView.removeFooterView(ftView);
                    isLoading=false;
                    break;
                default:
                    break;
            }
        }
    }

    private ArrayList<GridItem> getMoreData() {


        currentId=currentId+10;
        getData();








        return lst;
    }
    public class ThreadGetMoreData extends Thread {
        @Override
        public void run() {
            Looper.prepare();
            //Add footer view after get data
           // mHandler.sendEmptyMessage(0);
            //Search more data
            ArrayList<GridItem> lstResult = getMoreData();
            //Delay time to show loading footer when debug, remove it when release
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Send the result to Handle
            Message msg = mHandler.obtainMessage(1, lstResult);
            mHandler.sendMessage(msg);

        }
    }
    private void showGrid2(JSONArray jsonArray){
        //Looping through all the elements of json array
        for(int i = 0; i<jsonArray.length(); i++){
            //Creating a json object of the current index
            JSONObject obj = null;


            try {

                GridItem g=new GridItem();
                mProductList = new ArrayList<>();
                //

                obj = jsonArray.getJSONObject(i);


                names.add(obj.getString(TAG_Productionname));

                prices.add(obj.getString(TAG_price));

                JSONObject node = obj.getJSONObject(TAG_IMAGE_node);
                images.add(node.getString(TAG_IMAGE_URL));
                height.add(node.getString(TAG_height));
                width.add(node.getString(TAG_width));


                g.setImage(node.getString(TAG_IMAGE_URL));
                g.setPrice(obj.getString(TAG_price));
                g.setTitle(obj.getString(TAG_Productionname));


                lst.add(g);



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }






    }
}


