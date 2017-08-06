package com.example.lenovo.tic_tac_toe;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public final static int No_Player = 0;
    public final static int Player1 = 1;
    public final static int Player2 = 2;
    public final static int boardSize = 3;
    public final static int Incomplete = 0;
    public final static int drawn = 3;
    public final static int player1_won = 1;
    public final static int player2_won = 2;

    MyButton buttons[][];
    LinearLayout rowLayout[];
    LinearLayout mainLayout;
    boolean player1turn = true;
     boolean gameover =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        setUpBoard();
    }
   void resetBoard(){
       for(int i=0;i<boardSize;i++){
           for(int j=0;j<boardSize;j++){
               buttons[i][j].player=No_Player;
               buttons[i][j].setText("");
           }
       }
       player1turn=true;
       gameover=false;
   }
   public boolean onCreateOptionsMenu(Menu menu){
       getMenuInflater().inflate(R.menu.main_menu,menu);
       return true;
   }

   public boolean onOptionsItemSelected(MenuItem item){
       int id=item.getItemId();
       if(id==R.id.newGame){
           resetBoard();
       }else if(id==R.id.settings){

       }
       return true;
   }
    private void setUpBoard() {

        rowLayout = new LinearLayout[boardSize];
        mainLayout.removeAllViews();
        for (int i = 0; i < boardSize; i++) {
            rowLayout[i] = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f);
            params.setMargins(5, 5, 5, 5);
            rowLayout[i].setLayoutParams(params);
            rowLayout[i].setOrientation(LinearLayout.HORIZONTAL);
            mainLayout.addView(rowLayout[i]);
        }

        buttons = new MyButton[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                buttons[i][j] = new MyButton(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
                params.setMargins(5, 5, 5, 5);
                buttons[i][j].setLayoutParams(params);
                buttons[i][j].setOnClickListener(this);
                buttons[i][j].setTextSize(50);
                buttons[i][j].setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));
                rowLayout[i].addView(buttons[i][j]);
            }
        }

    }

    @Override
    public void onClick(View v) {
        if (gameover) {
            Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show();
            return;
        }
        MyButton button = (MyButton) v;
        if (button.player != No_Player) {
            Toast.makeText(this, "Invalid Move ", Toast.LENGTH_SHORT).show();
            return;
        }

            if (player1turn) {
                button.player = Player1;
                button.setText("O");
                player1turn = !player1turn;
            } else {
                button.player = Player2;
                button.setText("X");
                player1turn = !player1turn;
            }


       int gamestatus = getGameStatus();
        if(gamestatus==player1_won){
            Toast.makeText(this,"O WON ",Toast.LENGTH_SHORT).show();
            gameover=true;
        }else if(gamestatus==player2_won){
            Toast.makeText(this,"X WON ",Toast.LENGTH_SHORT).show();
            gameover=true;
        }else if(gamestatus==drawn){
            Toast.makeText(this,"Match Drawn ",Toast.LENGTH_SHORT).show();
            gameover=true;
        }
    }


    private int getGameStatus() {
        //for cheching wining condition in rows;

        for (int i = 0; i < boardSize; i++) {
            if (buttons[i][0].player != No_Player) {
                boolean flag=true;
                for (int j = 1; j < boardSize; j++) {
                    if (buttons[i][j].player != buttons[i][0].player) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    if (buttons[i][0].player == Player1) {
                        return player1_won;
                    } else if (buttons[i][0].player == Player2) {
                        return player2_won;
                    }
                }
            }
        }
        //for cheching wining condition in columns
        for (int i = 0; i < boardSize; i++) {
            if (buttons[0][i].player != No_Player) {
                boolean flag=true;
                for (int j = 1; j < boardSize; j++) {
                    if (buttons[j][i].player != buttons[0][i].player) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    if (buttons[0][i].player == Player1) {
                        return player1_won;
                    } else if (buttons[0][i].player == Player2) {
                        return player2_won;
                    }
                }
            }
        }



        //for wining condition at diagonal 1
        boolean flag = true;
        for (int i = 0; i <boardSize; i++) {
            if (buttons[i][i].player == No_Player || buttons[0][0].player!= buttons[i][i].player) {
                flag = false;
                break;
            }
        }
        if (flag) {
            if (buttons[0][0].player == Player1) {
                return player1_won;
            } else {
                return player2_won;
            }
        }

        //for wining condition at diagonal 2;
        flag=true;
        for (int i = boardSize - 1; i >= 0; i--) {
            int col = boardSize - 1 - i;
            if (buttons[i][col].player == No_Player ||
                    buttons[boardSize - 1][0].player != buttons[i][col].player) {
                flag = false;
                break;
            }
        }
        if (flag) {
            if (buttons[boardSize - 1][0].player == Player1) {
                return player1_won;
            } else {
                return player2_won;
            }
        }

        // To check if game is incomplete
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (buttons[i][j].player == No_Player) {
                    return Incomplete;
                }
            }
        }
        return drawn;
    }
}
