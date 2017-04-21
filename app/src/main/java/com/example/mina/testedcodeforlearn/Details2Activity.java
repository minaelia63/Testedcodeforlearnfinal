package com.example.mina.testedcodeforlearn;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
//this is work activity
public class Details2Activity extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TextView descriptionproduct;
    private TextView priceproduct;
    private ImageView imageView;
    //this is used and be this view now
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details2);
        String description = getIntent().getStringExtra("title");
        String image = getIntent().getStringExtra("image");
        String price = getIntent().getStringExtra("price");
        String width = getIntent().getStringExtra("width");
        String height = getIntent().getStringExtra("height");
        descriptionproduct = (TextView) findViewById(R.id.title);
        priceproduct = (TextView) findViewById(R.id.price);
        imageView = (ImageView) findViewById(R.id.image);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(description);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        final ImageView mimage = (ImageView) findViewById(R.id.image);
        Picasso.with(this).load(image).into(mimage, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) mimage.getDrawable()).getBitmap();
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette palette) {
                        applyPalette(palette);
                    }
                });
            }

            @Override
            public void onError() {

            }
        });

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(description);
        TextView price2 = (TextView) findViewById(R.id.price);
        price2.setText("$"+price+"");
    }
    private void applyPalette(Palette palette) {
        int primaryDark = getResources().getColor(R.color.colorPrimary);
        int primary = getResources().getColor(R.color.colorPrimary);
        collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
        collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));
        updateBackground((FloatingActionButton) findViewById(R.id.fab), palette);
        supportStartPostponedEnterTransition();
    }

    private void updateBackground(FloatingActionButton fab, Palette palette) {
        int lightVibrantColor = palette.getLightVibrantColor(getResources().getColor(android.R.color.white));
        int vibrantColor = palette.getVibrantColor(getResources().getColor(R.color.colorAccent));

        fab.setRippleColor(lightVibrantColor);
        fab.setBackgroundTintList(ColorStateList.valueOf(vibrantColor));
    }



}
