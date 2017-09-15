package com.example.yiyao.fourFragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.yiyao.R;

public class QianDaoActivity extends AppCompatActivity {
    private ImageView shezhireturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qian_dao);
        shezhireturn = (ImageView) findViewById(R.id.shezhireturn);


        shezhireturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QianDaoActivity.this.finish();
            }
        });

    }
}
