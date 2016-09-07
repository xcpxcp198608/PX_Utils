package com.px.px_utils.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.px.px_utils.R;

/**
 * Created by PX on 2016/8/27.
 */
public class BootActivity extends BaseActivity implements View.OnClickListener {
    private Button bt_FileDownload , bt_Volley ,bt_Json;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot);

        bt_FileDownload = (Button) findViewById(R.id.bt_fileDownload);
        bt_Volley = (Button) findViewById(R.id.bt_Volley);
        bt_Json = (Button) findViewById(R.id.bt_json);

        bt_FileDownload.setOnClickListener(this);
        bt_Volley.setOnClickListener(this);
        bt_Json.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_fileDownload:
                startActivity(new Intent(BootActivity.this , Demo_File_Download.class));
                break;
            case R.id.bt_Volley:
                startActivity(new Intent(BootActivity.this , Demo_OkHttp.class));
                break;
            case R.id.bt_json:
                startActivity(new Intent(BootActivity.this , Demo_Json.class));
                break;
        }
    }
}
