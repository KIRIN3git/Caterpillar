package com.example.shinji.caterpillar;

import android.content.DialogInterface;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Chronometer;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


    //===========================================================
    // パズルのピースを配列として設定する。（imageButtonのidを格納）
    //===========================================================
    /*
    static final int imgViewArray[][] = {
            { R.id.imageView0, R.id.imageView1, R.id.imageView2 },
            { R.id.imageView3, R.id.imageView4, R.id.imageView5 },
            { R.id.imageView6, R.id.imageView7, R.id.imageView8 }
    };
    */

    static final int imgViewArray[][] = {
            {R.id.imageView0, R.id.imageView1, R.id.imageView2, R.id.imageView3},
            {R.id.imageView4, R.id.imageView5, R.id.imageView6, R.id.imageView7},
            {R.id.imageView8, R.id.imageView9, R.id.imageView10, R.id.imageView11},
            {R.id.imageView12, R.id.imageView13, R.id.imageView14, R.id.imageView15}
    };

    /*
    static final int imgViewArray2[] = {
            R.id.imageView0, R.id.imageView1, R.id.imageView2, R.id.imageView3,
            R.id.imageView4, R.id.imageView5, R.id.imageView6, R.id.imageView7,
            R.id.imageView8, R.id.imageView9, R.id.imageView10, R.id.imageView11,
            R.id.imageView12, R.id.imageView13, R.id.imageView14, R.id.imageView15
    };
    */

    //===========================================================
    // パズルのピースの画像を配列として設定する。（リソースidを格納）
    //===========================================================
    /*
    static final int panelImageArray[][] = {
            { R.drawable.red, R.drawable.yellow, R.drawable.blue },
            { R.drawable.red, R.drawable.yellow, R.drawable.blue },
            { R.drawable.red, R.drawable.yellow, R.drawable.blue }
    };
    */

    static final int panelImageArray[][] = {
            { R.drawable.red, R.drawable.yellow, R.drawable.blue, R.drawable.green },
            { R.drawable.red, R.drawable.yellow, R.drawable.blue, R.drawable.green },
            { R.drawable.red, R.drawable.yellow, R.drawable.blue, R.drawable.green },
            { R.drawable.red, R.drawable.yellow, R.drawable.blue, R.drawable.green }
    };


    /*
    static final int panelImageArray2[] = {
            R.drawable.red, R.drawable.yellow, R.drawable.blue, R.drawable.green,
            R.drawable.red, R.drawable.yellow, R.drawable.blue, R.drawable.green,
            R.drawable.red, R.drawable.yellow, R.drawable.blue, R.drawable.green,
            R.drawable.red, R.drawable.yellow, R.drawable.blue, R.drawable.green
    };
    */

    int col_len = imgViewArray.length;
    int row_len = imgViewArray[0].length;

    // PanelControllerインスタンス
    PanelController panel[] = new PanelController[col_len * row_len];

    // X軸最低スワイプスピード
    private static final int SWIPE_SPEED = 150;

    // パネルサイズ
    private static final int PANEL_SIZE = 260;

    // レベル
    private int lebel = 1;




    // ゲームがスタートされたか判定するフラグ
    private boolean startFlg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // レイアウトを作成する
        setContentView(R.layout.activity_main3);

    }

    /**
     * スタートボタン押下処理
     * （匿名クラス）
     */
    public void onClickStart(View view) {

        createPanelController();
        startGame();
        startChronometer();

        startFlg = true;
    }

    /**
     * パズルピースの設定
     *
     * インスタンスを設定し、
     * イメージボタンに画像を乗っける
     */
    private void createPanelController() {

        // ピースの生成を行う
        for(int i = 0; i < col_len; i++) {
            for(int j = 0; j < row_len; j++) {
                ImageView imgView = (ImageView)findViewById(imgViewArray[i][j]);
                  panel[(i * row_len) + j] = new PanelController(imgView, panelImageArray[i][j], i, j);
            }
        }
    }

    /**
     * パズルのスタート準備
     *
     *  右下だけは変化なし
     */
    private void startGame() {

        int random_col,random_row;
        // パズルを並び替える

        for(int i = 0; i < col_len; i++){
            for(int j = 0; j < row_len; j++){
                random_col = (int)(Math.random() * col_len);
                random_row = (int)(Math.random() * row_len);
                panel[(i * row_len) + j].changeImage(panel[(random_col * row_len) + random_row]);
            }
        }

    }

    private boolean checkClear(){

        boolean clear_flg;


        if( startFlg == false ) return false;

        clear_flg = true;
        for(int i = 0; i < col_len; i++){
            for(int j = 0; j < row_len; j++){
                if( panel[i * row_len].ImageId != panel[(i * row_len) + j].ImageId ){
                    clear_flg = false;
                }
            }
        }

        if(clear_flg == true) return clear_flg;

        clear_flg = true;
        for(int i = 0; i < row_len; i++){
            for(int j = 0; j < col_len; j++){
                if( panel[i].ImageId != panel[i + (j * row_len)].ImageId ){
                    clear_flg = false;
                }
            }
        }

        return clear_flg;


/*
        if( lebel == 1 ) {
            if (panel[0].ImageId == panel[1].ImageId && panel[0].ImageId == panel[2].ImageId
                    && panel[3].ImageId == panel[4].ImageId && panel[3].ImageId == panel[5].ImageId
                    && panel[6].ImageId == panel[7].ImageId && panel[6].ImageId == panel[8].ImageId) {

                return true;
            } else if (panel[0].ImageId == panel[3].ImageId && panel[0].ImageId == panel[6].ImageId
                    && panel[1].ImageId == panel[4].ImageId && panel[1].ImageId == panel[7].ImageId
                    && panel[2].ImageId == panel[5].ImageId && panel[2].ImageId == panel[8].ImageId) {

                return true;
            } else {

                return false;
            }
        }
        else if( lebel == 2 ) {
            if (panel[0].ImageId == panel[1].ImageId && panel[0].ImageId == panel[2].ImageId && panel[0].ImageId == panel[3].ImageId
                    && panel[4].ImageId == panel[5].ImageId && panel[4].ImageId == panel[6].ImageId && panel[4].ImageId == panel[7].ImageId
                    && panel[8].ImageId == panel[9].ImageId && panel[8].ImageId == panel[10].ImageId && panel[8].ImageId == panel[11].ImageId
                    && panel[12].ImageId == panel[13].ImageId && panel[12].ImageId == panel[14].ImageId && panel[12].ImageId == panel[15].ImageId) {
                return true;
            } else if (panel[0].ImageId == panel[3].ImageId && panel[0].ImageId == panel[6].ImageId
                    && panel[1].ImageId == panel[4].ImageId && panel[1].ImageId == panel[7].ImageId
                    && panel[2].ImageId == panel[5].ImageId && panel[2].ImageId == panel[8].ImageId) {


                if (panel[0].ImageId == panel[4].ImageId && panel[0].ImageId == panel[8].ImageId && panel[0].ImageId == panel[12].ImageId
                        && panel[1].ImageId == panel[5].ImageId && panel[1].ImageId == panel[9].ImageId && panel[1].ImageId == panel[13].ImageId
                        && panel[2].ImageId == panel[6].ImageId && panel[2].ImageId == panel[10].ImageId && panel[2].ImageId == panel[14].ImageId
                        && panel[3].ImageId == panel[7].ImageId && panel[3].ImageId == panel[11].ImageId && panel[3].ImageId == panel[15].ImageId) {

                    return true;
                return true;
            } else {

                return false;
            }
        }
        else return false;
        */
    }

    private void clearEvent(){
        // 経過時間を取得
        long msec = stopChronometer();
        String message;

        message = msec / 1000 + "秒\n";
        if( msec <  30000 ) message = message + " ☆☆☆☆☆";
        else if( msec <  60000 ) message = message + " ☆☆☆☆";
        else if( msec <  120000 ) message = message + " ☆☆☆";
        else if( msec <  300000 ) message = message + " ☆☆";
        else message = message + " ☆";

        // ダイアログの生成
        AlertDialog.Builder alertDlgBld = new AlertDialog.Builder(this);
        alertDlgBld.setTitle("正解");
        alertDlgBld.setMessage(message);

        alertDlgBld.setPositiveButton(
                "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDlgBld.show();

        // スタートフラグを初期化する
        startFlg = false;
    }

    /**
     * クロノメーター開始
     *
     */
    private void startChronometer() {
        Chronometer chrono = (Chronometer)findViewById(R.id.chronometer1);
        chrono.setBase(SystemClock.elapsedRealtime());
        chrono.start();
    }

    /**
     * クロノメーター停止
     *
     */
    private long stopChronometer() {
        Chronometer chrono =
                (Chronometer)findViewById(R.id.chronometer1);
        chrono.stop();
        return SystemClock.elapsedRealtime() - chrono.getBase();
    }

    /**
     * パネルコントロール
     */
    class PanelController  implements View.OnTouchListener {
        ImageView imgView;
        int index_row;   // 横インデックス番号
        int index_col;   // 縦インデックス番号
        int ImageId;    // イメージID
        int data[];
//        int row,col;

        int moveCount = 0; // 移動が全部終わったかのカウント

        // 最初にタッチされた座標
        float startTouchX = 0;
        float startTouchY = 0;

        // 現在タッチ中の座標
        float nowTouchedX = 0;
        float nowTouchedY = 0;

        // タッチ中の差分
        float diffTouchedX = 0;
        float diffTouchedY = 0;

        public PanelController(ImageView argImgView, int argImgColor, int i, int j) {
            imgView = argImgView;
            imgView.setOnTouchListener(this);
            index_col = i;
            index_row = j;
            setImage(argImgColor);
/*
            data = checkPosition(index_ro,index_co);
            row = data[0];
            col = data[1];
*/
        }

        /**
         * 画像リソースセット
         */
        private int setImage(int argImageId){
            int oldImgID = ImageId;

            ImageId = argImageId;
            imgView.setImageResource(ImageId);
            return oldImgID;
        }

        /**
         * 画像交換

         */
        public void changeImage(PanelController other) {
            int newPosition = other.setImage(ImageId);
            setImage(newPosition);
        }

        public class AnimationListenerAdapter implements Animation.AnimationListener {

            @Override
            public void onAnimationEnd(Animation animation) {
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
            @Override
            public void onAnimationStart(Animation animation) {
            }

        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {
                // タッチ
                case MotionEvent.ACTION_DOWN:
                    startTouchX = event.getX();
                    startTouchY = event.getY();
                    break;

                // タッチが離れた
                case MotionEvent.ACTION_UP:
                    nowTouchedX = event.getX();
                    nowTouchedY = event.getY();

                    diffTouchedX = Math.abs(nowTouchedX - startTouchX);
                    diffTouchedY = Math.abs(nowTouchedY - startTouchY);

                    if( moveCount == 0 ) { // 前の動作が終わるまで移動禁止
                        if (startTouchY > nowTouchedY && diffTouchedY > diffTouchedX) {
                            SlideUp(index_row);
                        } else if (startTouchY < nowTouchedY && diffTouchedY > diffTouchedX) {
                            SlideDown(index_row);
                        } else if (startTouchX < nowTouchedX && diffTouchedX > diffTouchedY) {
                            SlideRight(index_col);
                        } else if (startTouchX > nowTouchedX && diffTouchedX > diffTouchedY) {
                            SlideLeft(index_col);
                        }
                        break;
                    }
            }

            return true;
        }


        private void SlideUp(int argRow) {
            int setImgNum; //設定する画像のパネル位置
            int imageId;

            for( int i = 0; i < col_len; i++ ){

                if( i == col_len - 1 ) setImgNum = argRow;
                else setImgNum = (i + 1) * row_len + argRow;

                imageId = panel[setImgNum].ImageId;

                Animation( (i * row_len) + argRow,imageId,1 );
            }
        }

        private void SlideDown(int argRow) {
            int setImgNum; //設定する画像のパネル位置
            int imageId;

            for( int i = 0; i < col_len; i++ ){

                if( i == 0 ) setImgNum = (col_len - 1) * row_len + argRow;
                else setImgNum = (i - 1) * row_len + argRow;

                imageId = panel[setImgNum].ImageId;

                Animation( (i * row_len) + argRow,imageId,4 );
            }
        }

        private void SlideRight(int argCol) {
            int setImgNum; //設定する画像のパネル位置
            int imageId;

            for( int i = 0; i < row_len; i++ ){

                if( i == 0 ) setImgNum = ( argCol * row_len ) + row_len - 1;
                else setImgNum = (argCol * row_len) + i - 1;

                imageId = panel[setImgNum].ImageId;

                Animation( (argCol * row_len) + i,imageId,2 );
            }
        }

        private void SlideLeft(int argCol) {
            int setImgNum; //設定する画像のパネル位置
            int imageId;

            for( int i = 0; i < row_len; i++ ){

                if( i == row_len - 1 ) setImgNum = argCol * row_len;
                else setImgNum = (argCol * row_len) + i + 1;

                imageId = panel[setImgNum].ImageId;

                Animation( (argCol * row_len) + i,imageId,3 );
            }
        }

        /* direction 1:上,2:右,3:左,4:下 */
        private void Animation(final int position,final int imageId,final int direction){

            TranslateAnimation animation_translate;

            if( direction == 1 ) animation_translate = new TranslateAnimation( 0, 0, 0, -PANEL_SIZE );
            else if( direction == 2 ) animation_translate = new TranslateAnimation( 0, PANEL_SIZE, 0, 0 );
            else if( direction == 3 ) animation_translate = new TranslateAnimation( 0, -PANEL_SIZE, 0, 0 );
            else if( direction == 4 ) animation_translate = new TranslateAnimation( 0, 0, 0, PANEL_SIZE );
            else return;

            animation_translate.setDuration( SWIPE_SPEED );

            animation_translate.setAnimationListener(new AnimationListenerAdapter() {
                @Override
                public void onAnimationEnd(Animation animation) {

                    panel[position].imgView.setAnimation(null);
                    panel[position].setImage(imageId);

                    moveCount++;

                    if ( ( ( direction == 1 || direction == 4 ) && moveCount ==  col_len)
                        || ( ( direction == 2 || direction == 3 ) && moveCount ==  row_len) ){ // すべて移動したら
                        if (checkClear() == true) {
                            clearEvent();
                        }
                        moveCount = 0;
                    }
                }
            });
            panel[position].imgView.startAnimation( animation_translate );
        }
    }
}
