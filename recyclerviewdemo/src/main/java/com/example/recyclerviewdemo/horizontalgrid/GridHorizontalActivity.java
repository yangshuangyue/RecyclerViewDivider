package com.example.recyclerviewdemo.horizontalgrid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.Random;

public class GridHorizontalActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<String> datas = new ArrayList<>();
    private GridHorizontalAdapter mAdapter;
    private Random mRandom = new Random();
    private RecyclerView mRv;

    public static void activeStart(Context context){
        Intent intent = new Intent(context, GridHorizontalActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_horizontal);
        init();
    }

    private void init() {
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_remove).setOnClickListener(this);

        mRv = findViewById(R.id.recycler_view);
        mRv.setLayoutManager(new GridLayoutManager(this,3,GridLayoutManager.HORIZONTAL,false));
        mAdapter = new GridHorizontalAdapter();
        mRv.setAdapter(mAdapter);
        mRv.setItemAnimator(new DefaultItemAnimator());
        mRv.addItemDecoration(new GridHorizontalDividerItemDecoration(this));
        for (int i = 0; i < 26; i++) {
            datas.add("" + ((char) (65 + i)));
        }
        mAdapter.addAll(datas);
        mAdapter.setmListener(new GridHorizontalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String s) {
                Toast.makeText(GridHorizontalActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                mAdapter.add(""+ mRandom.nextInt(100));
                //添加完成之后，让RecyclerView滑动到首个Item的位置
                RecyclerView.LayoutManager layoutManager = mRv.getLayoutManager();
                layoutManager.scrollToPosition(0);
                break;
            case R.id.btn_remove:
                mAdapter.remove();
                break;
        }
    }
}
