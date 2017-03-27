package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import Volleycachingandloading.CustomVolleyRequest;
import Model.GridItem;
import com.example.mina.testedcodeforlearn.R;

import java.util.ArrayList;

/**
 * Created by mina on 3/27/2017.
 */
//public class GridViewadapter2 extends ArrayAdapter<GridItem>
    //this is work adapter one iuse in main activity one

public class GridViewadapter2 extends BaseAdapter {

    private Context mContext;
    private int layoutResourceId;
    private ImageLoader imageLoader;
    private ArrayList<GridItem> mGridData = new ArrayList<GridItem>();
    private LayoutInflater minflater;

  /*  public GridViewadapter2(Context mContext, int layoutResourceId, ArrayList<GridItem> mGridData) {
        super(mContext, layoutResourceId, mGridData);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mGridData = mGridData;
    }*/
  public GridViewadapter2(Context mContext) {
    minflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      this.mContext = mContext;
  }

    /**
     * Updates grid data and refresh grid items.
     * @param
     */
    /*public void setGridData(ArrayList<GridItem> mGridData) {
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }*/
    public void additem(GridItem GridData) {
        mGridData.add(GridData);
        notifyDataSetChanged();
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
       return mGridData.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public GridItem getItem(int position) {
        return mGridData.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder=new ViewHolder();

        if (row == null) {

            row = minflater.inflate(R.layout.grid_item_layout,null);
            holder.description=new TextView(mContext);
            holder.setDescription((TextView) row.findViewById(R.id.grid_item_title));
            holder.price=new TextView(mContext);
            holder.setPrice((TextView) row.findViewById(R.id.grid_item_price));
            holder.imageView=new NetworkImageView(mContext);
            holder.setImageView((NetworkImageView) row.findViewById(R.id.grid_item_image));
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        //row=new View(mContext);

        GridItem item = mGridData.get(position);
//        holder.description = new TextView(mContext);
        holder.getDescription().setText(item.getTitle()+"");
      //  holder.price = new TextView(mContext);
        holder.getPrice().setText(item.getPrice()+"");
      //  holder.imageView.setImageResource(item.getImage().indexOf(position));

       // holder.imageView  = new NetworkImageView(mContext);

        //Initializing ImageLoader
        imageLoader = CustomVolleyRequest.getInstance(mContext).getImageLoader();
        imageLoader.get( item.getImage(), ImageLoader.getImageListener(holder.getImageView(), R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

        //Setting the image url to load
       holder.getImageView().setImageUrl(item.getImage(),imageLoader);

holder.imageView.setTag(item);

        return row;
    }

    static class ViewHolder {
        private TextView description;
        private NetworkImageView imageView;
        private TextView price;

        public TextView getDescription() {
            return description;
        }

        public void setDescription(TextView description) {
            this.description = description;
        }

        public NetworkImageView getImageView() {
            return imageView;
        }

        public void setImageView(NetworkImageView imageView) {
            this.imageView = imageView;
        }

        public TextView getPrice() {
            return price;
        }

        public void setPrice(TextView price) {
            this.price = price;
        }
    }


 /*   public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.description = (TextView) row.findViewById(R.id.grid_item_title);
            holder.price = (TextView) row.findViewById(R.id.grid_item_price);
            holder.imageView = (ImageView) row.findViewById(R.id.grid_item_image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        GridItem item = mGridData.get(position);
        holder.description.setText(item.getTitle());
        holder.price.setText(item.getPrice());

        Picasso.with(mContext).load(item.getImage()).into(holder.imageView);
        return row;
    }

    static class ViewHolder {
        TextView description;
        ImageView imageView;
        TextView price;
    }*/




}
