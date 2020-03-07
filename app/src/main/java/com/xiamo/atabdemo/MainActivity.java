package com.xiamo.atabdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xiamo.atab.ATab;
import com.xiamo.atab.ATabItem;

public class MainActivity extends AppCompatActivity {

    private ATab aTab;
    private TextView statusTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aTab = (ATab)findViewById(R.id.atab);
        statusTv = (TextView)findViewById(R.id.status_tv);
        findViewById(R.id.msg_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aTab.setMsg(3,null);
            }
        });

        aTab.addItem(new ATabItem.Builder(this).title("首页").uncheckIcon(getDrawable(R.mipmap.home_un))
                .checkIcon(getDrawable(R.mipmap.home)).checkColor(R.color.colorPrimary).create());
        aTab.addItem(new ATabItem.Builder(this).title("热门").uncheckIcon(getDrawable(R.mipmap.hot_un))
                .checkIcon(getDrawable(R.mipmap.hot)).checkColor(R.color.colorPrimary).create());
        aTab.addItem(new ATabItem.Builder(this).title("会员").uncheckIcon(getDrawable(R.mipmap.vip_un))
                .checkIcon(getDrawable(R.mipmap.vip)).checkColor(R.color.colorAccent).create());
        aTab.addItem(new ATabItem.Builder(this).title("消息").uncheckIcon(getDrawable(R.mipmap.msg_un))
                .checkIcon(getDrawable(R.mipmap.msg)).checkColor(R.color.colorPrimary).msg("哦豁").create());
        aTab.addItem(new ATabItem.Builder(this).title("我的").uncheckIcon(getDrawable(R.mipmap.mine_un))
                .checkIcon(getDrawable(R.mipmap.mine)).checkColor(R.color.colorPrimary).create());


        aTab.setOnItemSelectListener(new ATab.OnItemSelectListener() {
            @Override
            public void onItemSelect(int nowPos, int prePos) {
                statusTv.setText("当前选中："+nowPos);
                Log.e("-----", nowPos+"---"+prePos);
            }
        });
    }
}
