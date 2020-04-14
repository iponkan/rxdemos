package com.iponkan.rxdemos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends RxAppCompatActivity {

    @BindView(R.id.tv_hello)
    TextView tvHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_hello)
    public void onViewClicked() {
        startActivity(new Intent(this, FirstActivity.class));
    }
}
