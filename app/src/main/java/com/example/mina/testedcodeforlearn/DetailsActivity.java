package com.example.mina.testedcodeforlearn;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private TextView descriptionproduct;
    private TextView priceproduct;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        String description = getIntent().getStringExtra("title");
        String image = getIntent().getStringExtra("image");
        String price = getIntent().getStringExtra("price");
        String width = getIntent().getStringExtra("width");
        String height = getIntent().getStringExtra("height");
        descriptionproduct = (TextView) findViewById(R.id.textView);
        priceproduct = (TextView) findViewById(R.id.textView2);
        imageView = (ImageView) findViewById(R.id.imageViewproduct);

        Picasso.with(this).load(image).resize(Integer.parseInt(width), Integer.parseInt(height)).into(imageView);
       // Glide.with(this).load(image).into(imageView);

        descriptionproduct.setText(description);
        priceproduct.setText(price);

       // Picasso.with(this).load(image).resize(Integer.parseInt(width), Integer.parseInt(height)).into(imageView);
    }
}
