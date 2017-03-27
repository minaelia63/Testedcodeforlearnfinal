package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import Volleycachingandloading.CustomVolleyRequest;
import com.example.mina.testedcodeforlearn.R;

import java.util.ArrayList;

/**
 * Created by mina on 3/24/2017.
 */
//this is not used just for try only
//this is my tries if the user need to change the view only
public class GridViewAdapter extends BaseAdapter {

    //Imageloader to load images
    private ImageLoader imageLoader;
     private LayoutInflater inflater=null;
    //Context
    private Context context;

    //Array List that would contain the urls and the description for the images and prices
    private ArrayList<String> images;
    private ArrayList<String> description;
    private ArrayList<String> price;

    public GridViewAdapter (Context context, ArrayList<String> images, ArrayList<String> description,ArrayList<String>price ){
        //Getting all the values
        this.context = context;
        this.images = images;
        this.description = description;
        this.price=price;

    }


    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

      /*  View row = convertView;
        ViewHolder holder;

        if (row == null) {

            LayoutInflater inflater = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.grid_item_layout, null);

            holder = new ViewHolder();



            holder.setDes((TextView) row.findViewById(R.id.grid_item_title));

            holder.setPrice((TextView) row
                    .findViewById(R.id.grid_item_price));


            holder.setDrawableId((NetworkImageView) row.findViewById(R.id.grid_item_image));


            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        GridItem itemListObject = items.get(position);


        holder.getDes().setText(itemListObject.getTitle());

        holder.getPrice().setText(itemListObject.getPrice());


        holder.drawableId.setTag(itemListObject);



        // StrictMode.ThreadPolicy policy = new
        // StrictMode.ThreadPolicy.Builder()
        // .permitAll().build();
        // StrictMode.setThreadPolicy(policy);

        return row;*/


       //Creating a linear layout
        LinearLayout linearLayout = new LinearLayout(context);
       // GridLayoutAnimationController linearLayout = new GridLayoutAnimationController(context);
        // RelativeLayout linearLayout = new RelativeLayout(context);
       // FrameLayout linearLayout = new FrameLayout(context);
     linearLayout.setOrientation(LinearLayout.VERTICAL);

        //Creating a linear layout
       // FrameLayout linearLayout = new FrameLayout(context);


        //NetworkImageView
        NetworkImageView networkImageView = new NetworkImageView(context);

        //Initializing ImageLoader
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(images.get(position), ImageLoader.getImageListener(networkImageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

        //Setting the image url to load
        networkImageView.setImageUrl(images.get(position),imageLoader);

        //Creating a textview to show the title
        TextView textView = new TextView(context);
        textView.setText(description.get(position));

        //creating text view to show price
        TextView textView2 = new TextView(context);
        textView2.setText(price.get(position));
        //Scaling the imageview
        networkImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
       networkImageView.setLayoutParams(new GridView.LayoutParams(220,220));

        //Adding views to the layout
        linearLayout.addView(textView2);
        linearLayout.addView(networkImageView);
        linearLayout.addView(textView);



        //Returnint the layout
        return linearLayout;
    }


   /* private class ViewHolder
    {
        TextView des;
        TextView price;
         NetworkImageView drawableId;

        public TextView getDes() {
            return des;
        }

        public void setDes(TextView des) {
            this.des = des;
        }

        public TextView getPrice() {
            return price;
        }

        public void setPrice(TextView price) {
            this.price = price;
        }

        public NetworkImageView getDrawableId() {
            return drawableId;
        }

        public void setDrawableId(NetworkImageView drawableId) {
            this.drawableId = drawableId;
        }
    }*/
}