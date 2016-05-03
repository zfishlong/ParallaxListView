package com.ilmare.parallaxlistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ilmare.parallaxlistview.Utils.Cheeses;
import com.ilmare.parallaxlistview.View.ParallaxListView;

public class MainActivity extends AppCompatActivity {

    private ParallaxListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ParallaxListView) findViewById(R.id.listView);

        View headerView=View.inflate(this,R.layout.listview_header_view,null);
        final ImageView imageView= (ImageView) headerView.findViewById(R.id.header_view_image);


        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                listView.setHeaderImageView(imageView);
                imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


        //设置headerView
        listView.addHeaderView(headerView);


        //设置adapter
        ArrayAdapter adapter= new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, Cheeses.NAMES);
        listView.setAdapter(adapter);
    }
}
