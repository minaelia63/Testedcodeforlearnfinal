package com.example.mina.testedcodeforlearn;

import android.graphics.Bitmap;

import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by mina on 3/24/2017.
 */
//for develop by another way make gridviewadapter extends arrayadapter<Griditem>


public class GridItem {

    private  String title;
    //private String image;
    private String image;
    private  String price;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

   // public String getImage() {return image;}
    public String getImage() {
        return image;
    }

   // public void setImage(String image) {this.image = image;}
   public void setImage(String image) {this.image = image;}

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
