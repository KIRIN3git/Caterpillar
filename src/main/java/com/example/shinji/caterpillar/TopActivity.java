package com.example.shinji.caterpillar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by shinji on 2016/06/08.
 */
public class TopActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top);
        setMoveActivity();
    }


    /**
     * ゲーム開始ボタン押下処理
     * （匿名クラス）
     */
    private void setMoveActivity() {
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // インテントのインスタンス生成
                Intent intent = new Intent(TopActivity.this, MainActivity.class);

                // メイン画面の起動
                startActivity(intent);
            }
        });
    }
}