package com.example.yiyao.fourFragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.yiyao.R;

public class GuanYuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guan_yu);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.guanyureturn:
                GuanYuActivity.this.finish();
                break;
        }
    }
}
