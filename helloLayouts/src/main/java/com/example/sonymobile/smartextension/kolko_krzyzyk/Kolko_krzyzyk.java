
package com.example.sonymobile.smartextension.kolko_krzyzyk;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.sonyericsson.extras.liveware.aef.control.Control;
import com.sonyericsson.extras.liveware.extension.util.control.ControlExtension;
import com.sonyericsson.extras.liveware.extension.util.control.ControlTouchEvent;

import java.util.Random;

/**
 * This demonstrates two different approaches, bitmap and layout, for displaying
 * a UI. The bitmap approach is useful for accessories without layout support,
 * e.g. SmartWatch.
 * This sample shows all UI components that can be used, except Gallery and
 * ListView.
 */
class Kolko_krzyzyk extends ControlExtension {

    Rect[] buttons= new Rect[9];
    Bundle[] mMenuItemsText = new Bundle[1];


    private static final int MENU_ITEM_0 = 0;

private int tt;
    private boolean GAME;



    int[] tab;
    Random generator;


    Kolko_krzyzyk(final String hostAppPackageName, final Context context, Handler handler) {
        super(context, hostAppPackageName);
        if (handler == null) {
            throw new IllegalArgumentException("handler == null");
        }
        Log.d("asdasd", "zaczynam działanie");
        initializeMenus();

        buttons[0] = new Rect(0, 0, 73, 58);
        buttons[1] = new Rect(74, 0, 148, 58);
        buttons[2] = new Rect(149, 0, 220, 58);
        buttons[3] = new Rect(0, 59, 73, 118);
        buttons[4] = new Rect(74, 59, 148, 118);
        buttons[5] = new Rect(149, 59, 220, 118);
        buttons[6] = new Rect(0, 119, 73, 176);
        buttons[7] = new Rect(74, 119, 148, 176);
        buttons[8] = new Rect(149, 119, 220, 176);

        tt=0;
        GAME = true;
    }

    /**
     * Return the width of the screen which this control extension supports.
     *
     * @param context The context.
     * @return The width in pixels.
     */
    public static int getSupportedControlWidth(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.smart_watch_2_control_width);
    }

    /**
     * Return the height of the screen which this control extension supports.
     *
     * @param context The context.
     * @return The height in pixels.
     */
    public static int getSupportedControlHeight(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.smart_watch_2_control_height);
    }

    @Override
    public void onDestroy() {
        Log.d(HelloLayoutsExtensionService.LOG_TAG, "onDestroy: Kolko_krzyzyk");
        GAME = false;
    }





    private void movement(int x, int y){
        if(GAME) {
            Log.d("asdasd", "movemement()");
            for (int i = 0; i < 9; i++) {
                if (buttons[i].contains(x, y) && (tab[i] == 0)) {
                    sendImage(getImage(i + 1), R.drawable.kolko);
                    tab[i] = 1;
                    if (!whoWin()) {
                        ruchKomputera();
                        whoWin();
                    }
                }
            }
        }

    }




    @Override
    public void onTouch(ControlTouchEvent event) {
        Log.d("asdasd", "timestamp " + event.getTimeStamp());
        Log.d("asdasd", "system  " + System.currentTimeMillis());
        super.onTouch(event);
        Log.d("asdasd", "onTouch()");

        if(event.getTimeStamp()-System.currentTimeMillis()>-400)
            movement(event.getX(), event.getY());

    }

    private int getImage(int z) {
        switch (z) {
            case 1:
                return R.id.image1;
            case 2:
                return R.id.image2;
            case 3:
                return R.id.image3;
            case 4:
                return R.id.image4;
            case 5:
                return R.id.image5;
            case 6:
                return R.id.image6;
            case 7:
                return R.id.image7;
            case 8:
                return R.id.image8;
            case 9:
                return R.id.image9;
        }
        return -1;
    }



    private boolean gameOver() {
        if(GAME){
        Log.d("asdasd", "gameOver()");
        for (int i = 0; i < 9; i++) {
            if (tab[i] == 0) {
                return false;
            }
        }
        sendText(R.id.textView,"REMIS");
        restart();
        tt++;
        }
        return true;
    }

    private boolean whoWin() {
        Log.d("asdasd", "whoWin()");
        for (int i = 1; i < 3; i++) {
            if (
                    ((tab[0] == i) && (tab[1] == i) && (tab[2] == i)) ||
                    ((tab[3] == i) && (tab[4] == i) && (tab[5] == i)) ||
                    ((tab[6] == i) && (tab[7] == i) && (tab[8] == i)) ||
                    ((tab[0] == i) && (tab[3] == i) && (tab[6] == i)) ||
                    ((tab[1] == i) && (tab[4] == i) && (tab[7] == i)) ||
                    ((tab[2] == i) && (tab[5] == i) && (tab[8] == i)) ||
                    ((tab[0] == i) && (tab[4] == i) && (tab[8] == i)) ||
                    ((tab[2] == i) && (tab[4] == i) && (tab[6] == i))
                    ) {
                tt++;
                if (i == 1) {
                    sendText(R.id.textView, "You WIN");
                    restart();
                    return true;
                }
                if (i == 2) {
                    sendText(R.id.textView, "You LOSE");
                    restart();
                    return true;
                }

            }
        }
        return false;
    }

    private void restart() {
        Log.d("asdasd", "restart()");
        Long currentTime = System.currentTimeMillis() + 3000;
        while (currentTime > System.currentTimeMillis()) {


            }




        onResume();
    }


    private boolean ruchKomputera() {
        for(int i=2;i>0;i--) {
            if ((tab[0] == i) && (tab[1] == i) && (tab[2] == 0)) {
                sendImage(R.id.image3, R.drawable.krzyzyk);
                tab[2] = 2;
                return true;
            }
            if ((tab[3] == i) && (tab[4] == i) && (tab[5] == 0)) {
                sendImage(R.id.image6, R.drawable.krzyzyk);
                tab[5] = 2;
                return true;
            }
            if ((tab[6] == i) && (tab[7] == i) && (tab[8] == 0)) {
                sendImage(R.id.image9, R.drawable.krzyzyk);
                tab[8] = 2;
                return true;
            }
            if ((tab[0] == i) && (tab[2] == i) && (tab[1] == 0)) {
                sendImage(R.id.image2, R.drawable.krzyzyk);
                tab[1] = 2;
                return true;
            }
            if ((tab[3] == i) && (tab[5] == i) && (tab[4] == 0)) {
                sendImage(R.id.image5, R.drawable.krzyzyk);
                tab[4] = 2;
                return true;
            }
            if ((tab[6] == i) && (tab[8] == i) && (tab[7] == 0)) {
                sendImage(R.id.image8, R.drawable.krzyzyk);
                tab[7] = 2;
                return true;
            }
            if ((tab[1] == i) && (tab[2] == i) && (tab[0] == 0)) {
                sendImage(R.id.image1, R.drawable.krzyzyk);
                tab[0] = 2;
                return true;
            }
            if ((tab[4] == i) && (tab[5] == i) && (tab[3] == 0)) {
                sendImage(R.id.image4, R.drawable.krzyzyk);
                tab[3] = 2;
                return true;
            }
            if ((tab[7] == i) && (tab[8] == i) && (tab[6] == 0)) {
                sendImage(R.id.image7, R.drawable.krzyzyk);
                tab[6] = 2;
                return true;
            }
            //pionowe

            if ((tab[0] == i) && (tab[3] == i) && (tab[6] == 0)) {
                sendImage(R.id.image7, R.drawable.krzyzyk);
                tab[6] = 2;
                return true;
            }
            if ((tab[1] == i) && (tab[4] == i) && (tab[7] == 0)) {
                sendImage(R.id.image8, R.drawable.krzyzyk);
                tab[7] = 2;
                return true;
            }
            if ((tab[2] == i) && (tab[5] == i) && (tab[8] == 0)) {
                sendImage(R.id.image9, R.drawable.krzyzyk);
                tab[8] = 2;
                return true;
            }

            if ((tab[0] == i) && (tab[6] == i) && (tab[3] == 0)) {
                sendImage(R.id.image4, R.drawable.krzyzyk);
                tab[3] = 2;
                return true;
            }
            if ((tab[1] == i) && (tab[7] == i) && (tab[4] == 0)) {
                sendImage(R.id.image5, R.drawable.krzyzyk);
                tab[4] = 2;
                return true;
            }
            if ((tab[2] == i) && (tab[8] == i) && (tab[5] == 0)) {
                sendImage(R.id.image6, R.drawable.krzyzyk);
                tab[5] = 2;
                return true;
            }

            if ((tab[3] == i) && (tab[6] == i) && (tab[0] == 0)) {
                sendImage(R.id.image1, R.drawable.krzyzyk);
                tab[0] = 2;
                return true;
            }
            if ((tab[4] == i) && (tab[7] == i) && (tab[1] == 0)) {
                sendImage(R.id.image2, R.drawable.krzyzyk);
                tab[1] = 2;
                return true;
            }
            if ((tab[5] == i) && (tab[8] == i) && (tab[2] == 0)) {
                sendImage(R.id.image3, R.drawable.krzyzyk);
                tab[2] = 2;
                return true;
            }

            //ukośne
            if ((tab[0] == i) && (tab[4] == i) && (tab[8] == 0)) {
                sendImage(R.id.image9, R.drawable.krzyzyk);
                tab[8] = 2;
                return true;
            }
            if ((tab[0] == i) && (tab[8] == i) && (tab[4] == 0)) {
                sendImage(R.id.image5, R.drawable.krzyzyk);
                tab[4] = 2;
                return true;
            }
            if ((tab[4] == i) && (tab[8] == i) && (tab[0] == 0)) {
                sendImage(R.id.image1, R.drawable.krzyzyk);
                tab[0] = 2;
                return true;
            }

            if ((tab[2] == i) && (tab[4] == i) && (tab[6] == 0)) {
                sendImage(R.id.image7, R.drawable.krzyzyk);
                tab[6] = 2;
                return true;
            }
            if ((tab[2] == i) && (tab[6] == i) && (tab[4] == 0)) {
                sendImage(R.id.image5, R.drawable.krzyzyk);
                tab[4] = 2;
                return true;
            }
            if ((tab[4] == i) && (tab[6] == i) && (tab[2] == 0)) {
                sendImage(R.id.image3, R.drawable.krzyzyk);
                tab[2] = 2;
                return true;
            }


        }




        losowyRuch();
        return true;
    }
    private void losowyRuch(){
        boolean state = true;
        int i = -1;


        while (state & !gameOver()) {

            i = generator.nextInt(8);
            if (tab[i] == 0) {
                state = false;
                sendImage(getImage(i + 1), R.drawable.krzyzyk);
                tab[i] = 2;

            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();


    }

    @Override
    public void onResume() {
        super.onResume();





        tab = new int[9];
        for (int i = 0; i < 9; i++) {
            tab[i] = 0;
        }
        generator = new Random();

        showLayout(R.layout.layout, null);






            if(tt%2==1){
                Log.d("asdasd","Losowy ruch");
                losowyRuch();
            }
        Log.d("asdasd", "TT "+tt+" t%2 "+tt%2);

        GAME=true;
    }

    private void initializeMenus() {
        mMenuItemsText[0] = new Bundle();
        mMenuItemsText[0].putInt(Control.Intents.EXTRA_MENU_ITEM_ID, MENU_ITEM_0);
        mMenuItemsText[0].putString(Control.Intents.EXTRA_MENU_ITEM_TEXT, "Reload");

    }


    @Override
    public void onKey(final int action, final int keyCode, final long timeStamp) {
        //Log.d(HelloEventsExtensionService.LOG_TAG, "onKey()");
        if (action == Control.Intents.KEY_ACTION_RELEASE
                && keyCode == Control.KeyCodes.KEYCODE_OPTIONS) {
            showMenu(mMenuItemsText);
        }
    }


    @Override
    public void onMenuItemSelected(final int menuItem) {
        if(menuItem==0){
            onResume();
        }
    }



}
