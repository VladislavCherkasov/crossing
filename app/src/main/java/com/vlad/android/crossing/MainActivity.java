package com.vlad.android.crossing;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;
import java.util.ArrayList;




public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    GridLayout GameLayout;

  Button button;
  Button new_game_button;
   ArrayList<Button> game_field_buttons=new ArrayList<>();
    DisplayMetrics metrics ;
    int width ;
    char [][] game_table = new char[7][7];
    boolean game_won=false;



    ArrayList<Integer> user_pressed_buttons_IDs = new ArrayList<>();
    ArrayList<Integer> computer_pressed_buttons_IDs = new ArrayList<>();
    GradientDrawable gd = new GradientDrawable();
    GradientDrawable gd_pressed = new GradientDrawable();
    GradientDrawable gd_computer_move = new GradientDrawable();
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

         metrics = this.getResources().getDisplayMetrics();
         width = metrics.widthPixels;

      //  GameLayout = new GridLayout(this);
        GameLayout = findViewById(R.id.grid);

        GameLayout.setColumnCount(7);
        GameLayout.setRowCount(7);

        gd.setColor(getResources().getColor(R.color.dark_blue));
        gd.setStroke(1, 0xFF000000);





      //  ViewGroup.LayoutParams lpView = new ViewGroup.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

     //   GameLayout.setLayoutParams(lpView);


        for (int i=0;i<7;i++)
            for (int j=0;j<7;j++) game_table[i][j]='1'; //инициализация игровой таблицы




        for ( int i=0;i<49;i++)
        {

            button = new Button(this);


            button.setTextSize(width/35);

          //  button.setWidth(30);

        //    button.setEms(1);
        //    button.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
          //  button.requestLayout();
            button.setPadding(0,0,0,0);



            button.setBackground(gd);

            button.setId(i);



            GridLayout.LayoutParams param = new GridLayout.LayoutParams();



            param.width = (int) Math.ceil((double) width/7);
            param.height = param.width;
           // param.width = 50;
           button.setLayoutParams(param);

            GameLayout.addView(button);
            button.setOnClickListener(this);
            game_field_buttons.add(button);
        }


        new_game_button = findViewById(R.id.new_game_button);

        new_game_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                start_Again();
            }
        });


    }



        public void onClick(View v) {
      //  game_won=false;
if(!user_pressed_buttons_IDs.contains(v.getId()) & !computer_pressed_buttons_IDs.contains(v.getId())) {
    ((Button) v).setText("X");
    gd_pressed.setColor(getResources().getColor(R.color.dark_red));

    gd_pressed.setStroke(1, 0xFF000000);
    ((Button) v).setBackground(gd_pressed);

    user_pressed_buttons_IDs.add(v.getId());
    game_table[((int)v.getId()/7)][((int)v.getId()%7)]='X'; // заполнение игровой таблицы
}
             //int a= (v.getId());
          //  Toast toast = Toast.makeText(MainActivity.this,String.valueOf(a),Toast.LENGTH_SHORT);
         //   toast.show();

     game_won =win_Test(game_table,'X');
            if (game_won) {
                Toast.makeText(MainActivity.this, R.string.You_won_message, Toast.LENGTH_SHORT).show();
for (Button b: game_field_buttons) {
   // b.setBackgroundColor(Color.YELLOW);
    b.setEnabled(false);
}

            }


        if   (!checkIfGameOver() & !game_won)
            computerMove();
        }



    private void start_Again() {

        setContentView(R.layout.activity_main);

        game_won=false;
        user_pressed_buttons_IDs.clear();
        computer_pressed_buttons_IDs.clear();
        for (int i=0;i<7;i++)
            for (int j=0;j<7;j++) game_table[i][j]='1'; //инициализация игровой таблицы



        metrics = this.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        GameLayout = findViewById(R.id.grid);

        GameLayout.setColumnCount(7);
        GameLayout.setRowCount(7);

        //for(Button b:game_field_buttons) {b.setText("");GameLayout.addView(button);}


        for ( int i=0;i<49;i++) {

            button = new Button(this);

            button.setTextSize(width/35);


            button.setPadding(0,0,0,0);

            GradientDrawable gd = new GradientDrawable();
            gd.setColor(getResources().getColor(R.color.dark_blue));
            gd.setStroke(1, 0xFF000000);
            button.setBackground(gd);

            button.setId(i);


            GridLayout.LayoutParams param = new GridLayout.LayoutParams();



            param.width = (int) Math.ceil((double) width/7);
            param.height = param.width;

            button.setLayoutParams(param);

            GameLayout.addView(button);
            button.setOnClickListener(this);
            game_field_buttons.add(button);
        }




        new_game_button = findViewById(R.id.new_game_button);
        new_game_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                start_Again();
            }
        });




    }

    private void computerMove() {
        Button button=null;
      while (true) {
        int i = (int) (49 * Math.random());
        if (!user_pressed_buttons_IDs.contains(i) & !computer_pressed_buttons_IDs.contains(i)) {
            computer_pressed_buttons_IDs.add(i);
            button = findViewById(i);
            if(button!=null) {
                button.setText("O");
                gd_computer_move.setColor(getResources().getColor(R.color.green));

                gd_computer_move.setStroke(1, 0xFF000000);
                button.setBackground(gd_computer_move);
                game_table[((int)button.getId()/7)][((int)button.getId()%7)]='O'; // заполнение игровой таблицы
            }
            break;
        }
    }
      if (win_Test(game_table,'O')) Toast.makeText(MainActivity.this,R.string.You_lost_message,Toast.LENGTH_SHORT).show();
      checkIfGameOver();
    }

private boolean checkIfGameOver() {
        if ((user_pressed_buttons_IDs.size()+computer_pressed_buttons_IDs.size())==49) {
            Toast toast = Toast.makeText(MainActivity.this,R.string.Game_over_message,Toast.LENGTH_SHORT);

            toast.show();
            return true;

        }
        else return false;
}

    private boolean win_Test (char [][] game_table, char sign){

         boolean win=false;



         //создание копии матрицы для последующего поворота

         char [][] game_table_to_turn = new char [7][7];


         for (int i=0;i<7;i++){
             for (int j=0;j<7;j++)
             {

                 game_table_to_turn[i][j]=game_table[i][j];
             }
         }
// конец создания копии


         //поворот матрицы


         char[][] game_table_turned = new char[game_table_to_turn[0].length][game_table_to_turn.length];
         for (int i = 0; i < game_table_to_turn.length; i++) {
             for (int j = 0; j < game_table_to_turn[i].length; j++) {
                 game_table_turned[j][game_table_to_turn.length - i - 1] = game_table_to_turn[i][j];
             }
         }

         //конец поворота




         //  проверка строк матрицы на выигрыш


         for (int i=0;i<7;i++){
             for (int j=0;j<6;j++)
             {
                 if (game_table[i][j]==sign &  game_table[i][j]==game_table[i][j+1]) win=true;
                 else {
                     win = false;
                     break;
                 }
             }
             if (win) return win;
         }


         // конец проверки строк на выигрыш

         //  проверка столбцов матрицы на выигрыш


         for (int j=0;j<7;j++){
             for (int i=0;i<6;i++)
             {
                 if (game_table[i][j]==sign &  game_table[i][j]==game_table[i+1][j]) win=true;
                 else {
                     win = false;
                     break;
                 }
             }
             if (win) return win;
         }

         // конец проверки столбцов на выигрыш

         // приведение первоначальной матрицы к проверяемому виду
         for (int k=0;k<game_table.length;k++)
         {
             for (int i=0;i<game_table.length-1;i++)
             {

                 //    if (i<game_table[i].length-1)
                 for (int j=0;j<7;j++)
                 {


                     //       if ((j>0) )  if (game_table[i][j]==sign &  game_table[i][j]==game_table[i+1][j-1]) game_table[i][j-1]=game_table[i+1][j-1];
                     //    if (j<7)    if (game_table[i][j]==sign &   game_table[i][j]==game_table[i+1][j+1]) game_table[i][j+1]=game_table[i+1][j+1];
                     if ((j>0 & (j<6)) ) {
                         if (game_table[i][j] == sign & game_table[i][j] == game_table[i + 1][j - 1])
                             game_table[i][j - 1] = game_table[i + 1][j - 1];

                         if (game_table[i][j] == sign & game_table[i][j] == game_table[i + 1][j + 1])
                             game_table[i][j + 1] = game_table[i + 1][j + 1];
                     }

                     if (j==0)
                         if (game_table[i][j] == sign & game_table[i][j] == game_table[i + 1][j + 1])
                             game_table[i][j + 1] = game_table[i + 1][j + 1];
                     if (j==6)
                         if (game_table[i][j] == sign & game_table[i][j] == game_table[i + 1][j - 1])
                             game_table[i][j - 1] = game_table[i + 1][j - 1];
                 }
             }
         }
// конец приведения матрицы к проверямому виду



//  проверка строк измененной матрицы на выигрыш
         for (int i=0;i<6;i++){
             for (int j=0;j<6;j++)
             {
                 if (game_table[i][j]==sign &  game_table[i][j]==game_table[i][j+1]) win=true;
                 else {
                     win = false;
                     break;
                 }
             }
             if (win) return win;
         }
         // конец проверки строк на выигрыш



         // приведение повернутой матрицы к проверяемому виду
         for (int k=0;k<game_table_turned.length;k++)
         {
             for (int i=0;i<game_table_turned.length-1;i++)
             {

                 for (int j=0;j<7;j++)
                 {


                     //       if ((j>0) )  if (game_table[i][j]==sign &  game_table[i][j]==game_table[i+1][j-1]) game_table[i][j-1]=game_table[i+1][j-1];
                     //    if (j<7)    if (game_table[i][j]==sign &   game_table[i][j]==game_table[i+1][j+1]) game_table[i][j+1]=game_table[i+1][j+1];
                     if ((j>0 & (j<6)) ) {
                         if (game_table_turned[i][j] == sign & game_table_turned[i][j] == game_table_turned[i + 1][j - 1])
                             game_table_turned[i][j - 1] = game_table_turned[i + 1][j - 1];

                         if (game_table_turned[i][j] == sign & game_table_turned[i][j] == game_table_turned[i + 1][j + 1])
                             game_table_turned[i][j + 1] = game_table_turned[i + 1][j + 1];
                     }

                     if (j==0)
                         if (game_table_turned[i][j] == sign & game_table_turned[i][j] == game_table_turned[i + 1][j + 1])
                             game_table_turned[i][j + 1] = game_table_turned[i + 1][j + 1];
                     if (j==6)
                         if (game_table_turned[i][j] == sign & game_table_turned[i][j] == game_table_turned[i + 1][j - 1])
                             game_table_turned[i][j - 1] = game_table_turned[i + 1][j - 1];
                 }



             }
         }

         // конец приведения матрицы к проверямому виду


         //  проверка строк измененной повернутой матрицы на выигрыш

         for (int i=0;i<6;i++){
             for (int j=0;j<6;j++)
             {
                 if (game_table_turned[i][j]==sign &  game_table_turned[i][j]==game_table_turned[i][j+1]) win=true;
                 else {
                     win = false;
                     break;
                 }
             }
             if (win) return win;
         }
         // конец проверки строк на выигрыш





         return win;


    }




}
