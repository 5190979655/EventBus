package com.ping.eventbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

/**
 * Created by Mr.sorrow on 2017/5/9.
 */

public class SecActivity extends AppCompatActivity {
    @BindView(R.id.sendData)
    Button mSendData;
    @BindView(R.id.receive)
    Button mReceive;
    @BindView(R.id.tv_receive)
    TextView mTvReceive;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.sendData, R.id.receive})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sendData:
                //发送事件
                EventBus.getDefault().post(new UserEvent("Mr.sorrow", "123456"));
//                finish();
                break;
            case R.id.receive:
                boolean registered = EventBus.getDefault().isRegistered(SecActivity.this);
                if (!registered)
                EventBus.getDefault().register(SecActivity.this);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void receiveEventBus(MessageEvent messageEvent) {
        Log.e("qwer2",messageEvent.toString());
        mTvReceive.setText(messageEvent.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(SecActivity.this);
    }
}
