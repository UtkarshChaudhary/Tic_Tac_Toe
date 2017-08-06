package com.example.lenovo.tic_tac_toe;

import android.content.Context;
import android.widget.Button;

/**
 * Created by lenovo on 16-06-2017.
 */

public class MyButton extends Button {
    int player;
    public MyButton(Context context) {
        super(context);
        player=MainActivity.No_Player;
    }
}
