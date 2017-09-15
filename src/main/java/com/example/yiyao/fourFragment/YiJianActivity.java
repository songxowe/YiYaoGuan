package com.example.yiyao.fourFragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.yiyao.R;
import com.example.yiyao.Utils.TmallUtils;

public class YiJianActivity extends AppCompatActivity {
    private ImageView shezhireturn;
    private EditText etYiJian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yi_jian);
        etYiJian = (EditText) findViewById(R.id.et_yijian);
        shezhireturn = (ImageView) findViewById(R.id.yijian_back);

        shezhireturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YiJianActivity.this.finish();
            }
        });


    }

    public void onClick(View view) {
        String yj = etYiJian.getText().toString();
        if (!yj.equals("") && yj != null) {
            TmallUtils.showToast(this, "意见提交成功！");
        } else {
            TmallUtils.showToast(this, "提交意见不能为空！");

        }
    }
}
