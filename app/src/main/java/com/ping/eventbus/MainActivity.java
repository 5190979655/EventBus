package com.ping.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.jump)
    Button mJump;
    @BindView(R.id.send)
    Button mSend;
    @BindView(R.id.tv_result)
    TextView mTvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        try {
            //注册订阅者
            EventBus.getDefault().register(this);
        }catch (Exception e){

        }

    }

    @OnClick({R.id.jump, R.id.send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.jump:
                startActivity(new Intent(MainActivity.this, SecActivity.class));
                break;
            case R.id.send:
                EventBus.getDefault().postSticky(new MessageEvent("粘性事件", "urgent"));
                startActivity(new Intent(MainActivity.this, SecActivity.class));
                break;
        }
    }

    //定义处理接收的方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UserEvent userEvent){
        Log.e("qwer1",userEvent.toString());
        mTvResult.setText(userEvent.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销注册
        EventBus.getDefault().unregister(this);
    }
}
