package com.ogmall.indexscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;



/**
 * Created by Administrator on 2019/3/11.
 */

public class StoreyActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storey);
        findViewById(R.id.sil_one).setOnClickListener(this);
        findViewById(R.id.sil_two).setOnClickListener(this);
        findViewById(R.id.sil_three).setOnClickListener(this);
        findViewById(R.id.sil_four).setOnClickListener(this);
        findViewById(R.id.sil_five).setOnClickListener(this);
        findViewById(R.id.sil_sex).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(),StoreyInfoActivity.class);

        switch (v.getId()){
            case R.id.sil_one:
                intent.putExtra(StoreyInfoActivity.STORE_INDEX,1);
                break;
            case R.id.sil_two:
                intent.putExtra(StoreyInfoActivity.STORE_INDEX,2);
                break;
            case R.id.sil_three:
                intent.putExtra(StoreyInfoActivity.STORE_INDEX,3);
                break;
            case R.id.sil_four:
                intent.putExtra(StoreyInfoActivity.STORE_INDEX,4);
                break;
            case R.id.sil_five:
                intent.putExtra(StoreyInfoActivity.STORE_INDEX,5);
                break;
            case R.id.sil_sex:
                intent.putExtra(StoreyInfoActivity.STORE_INDEX,6);
                break;
        }
        startActivity(intent);
    }
}
