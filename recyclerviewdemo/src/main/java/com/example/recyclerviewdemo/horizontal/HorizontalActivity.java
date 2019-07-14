package com.example.recyclerviewdemo.horizontal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.Random;

public class HorizontalActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<String> datas = new ArrayList<>();
    private HorizontalAdapter mAdapter;
    private Random mRandom = new Random();

    public static void activeStart(Context context){
        Intent intent = new Intent(context, HorizontalActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal);
        init();
    }

    private void init() {
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_remove).setOnClickListener(this);

        RecyclerView rv = findViewById(R.id.recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL,false));
        mAdapter = new HorizontalAdapter();
        rv.setAdapter(mAdapter);

        rv.addItemDecoration(new HorizontalDividerItemDecoration(this));
        for (int i = 0; i < 26; i++) {
            datas.add("" + ((char) (65 + i)));
        }
        mAdapter.addAll(datas);
        mAdapter.setmListener(new HorizontalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String s) {
                Toast.makeText(HorizontalActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                mAdapter.add(""+ mRandom.nextInt(100));
                break;
            case R.id.btn_remove:
                mAdapter.remove();
                break;
        }
    }
}
