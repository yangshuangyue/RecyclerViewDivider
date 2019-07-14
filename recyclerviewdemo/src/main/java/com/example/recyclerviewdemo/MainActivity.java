package com.example.recyclerviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.recyclerviewdemo.horizontalgrid.GridHorizontalActivity;
import com.example.recyclerviewdemo.horizontalstaggered.StaggeredHorizontalActivity;
import com.example.recyclerviewdemo.verticalgrid.GridVerticalActivity;
import com.example.recyclerviewdemo.horizontal.HorizontalActivity;
import com.example.recyclerviewdemo.verticalstaggered.StaggeredActivity;
import com.example.recyclerviewdemo.vertical.VerticalActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        //添加注释
        findViewById(R.id.btn_vertical).setOnClickListener(this);
        findViewById(R.id.btn_horizontal).setOnClickListener(this);
        findViewById(R.id.btn_vertical_grid).setOnClickListener(this);
        findViewById(R.id.btn_horizontal_grid).setOnClickListener(this);
        findViewById(R.id.btn_vertical_staggered).setOnClickListener(this);
        findViewById(R.id.btn_horizontal_staggered).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_vertical:
                VerticalActivity.activeStart(this);
                break;
            case R.id.btn_horizontal:
                HorizontalActivity.activeStart(this);
                break;
            case R.id.btn_vertical_grid:
                GridVerticalActivity.activeStart(this);
                break;
            case R.id.btn_horizontal_grid:
                GridHorizontalActivity.activeStart(this);
                break;
            case R.id.btn_vertical_staggered:
                StaggeredActivity.activeStart(this);
                break;
            case R.id.btn_horizontal_staggered:
                StaggeredHorizontalActivity.activeStart(this);
                break;
        }
    }
}
