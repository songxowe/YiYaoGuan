package com.example.yiyao.OneActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yiyao.MainActivity;
import com.example.yiyao.R;

//搜索产品
public class FindDrugActivity extends AppCompatActivity {

    private TextView tvCancel,tvFind;
    private EditText txtInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_drug);

        initView();
        initDate();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        txtInput = (EditText) findViewById(R.id.txt_input);
        tvFind=(TextView)findViewById(R.id.drug_find);
    }
    /**
     * 初始化数据
     */
    private void initDate() {

        //取消的事件
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FindDrugActivity.this, MainActivity.class));

            }
        }
        );

        //搜索的事件
        tvFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将文本框中的值做为查找条件，跳转到查询结果的界面中
                Intent intent = new Intent(FindDrugActivity.this, ResultFindActivity.class);
                String text=txtInput.getText().toString();
                intent.putExtra("find", text);
                startActivity(intent);
            }
        });

    }

    public void onClick(View view){
        //将文本框中的值做为查找条件，跳转到查询结果的界面中
        Intent intent = new Intent(this, ResultFindActivity.class);
        String input="null";
        switch (view.getId()){
            case R.id.btn_ganmao:
                input = "感冒";
                break;
            case R.id.btn_shenxu:
                input = "肾虚";
                break;
            case R.id.btn_jiedu:
                input = "清热解毒";
                break;
            case R.id.btn_a_jiao:
                input = "阿胶";
                break;
            case R.id.btn_jiuzhitang:
                input = "九芝堂";
                break;
            case R.id.btn_jianfeiyao:
                input = "减肥药";
                break;
            case R.id.btn_fuke:
                input = "妇科";
                break;
        }
        txtInput.setText(input);
        intent.putExtra("find", input);
        startActivity(intent);
    }

}
