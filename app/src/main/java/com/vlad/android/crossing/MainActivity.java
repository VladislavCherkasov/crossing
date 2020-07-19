package com.vlad.android.crossing;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;
import java.util.ArrayList;




public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final String GAME_TABLE_INDEX = "game_table_index";
    private static final String GAME_WON_INDEX = "game_won_index";
    private static final String USER_PRESSED_BUTTONS_IDS_INDEX = "user_pressed_buttons_IDs_index";
    private static final String COMPUTER_PRESSED_BUTTONS_IDS_INDEX = "computer_pressed_buttons_IDs_index";


   private GridLayout GameLayout;

 private Button button;
 private Button new_game_button;
  private ArrayList<Button> game_field_buttons;
 private    DisplayMetrics metrics ;
  private   int width ;
   private char [][] game_table;
 private boolean game_won;



    ArrayList<Integer> user_pressed_buttons_IDs;
    ArrayList<Integer> computer_pressed_buttons_IDs;
    GradientDrawable gd = new GradientDrawable();
    GradientDrawable gd_pressed = new GradientDrawable();
    GradientDrawable gd_computer_move = new GradientDrawable();
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        game_won=false;
        game_table = new char[7][7];
        user_pressed_buttons_IDs = new ArrayList<>();
        computer_pressed_buttons_IDs = new ArrayList<>();
        game_field_buttons=new ArrayList<>();

        if (savedInstanceState != null) {
            game_table = (char[][]) savedInstanceState.getSerializable(GAME_TABLE_INDEX);
            game_won = savedInstanceState.getBoolean(GAME_WON_INDEX);
            user_pressed_buttons_IDs = savedInstanceState.getIntegerArrayList(USER_PRESSED_BUTTONS_IDS_INDEX);
            computer_pressed_buttons_IDs = savedInstanceState.getIntegerArrayList(COMPUTER_PRESSED_BUTTONS_IDS_INDEX);
        }


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
         //   button.setText(String.valueOf(new Integer(i)));

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

       // if (v==new_game_button) start_Again();
      //  game_won=false;
if(!user_pressed_buttons_IDs.contains(v.getId()) & !computer_pressed_buttons_IDs.contains(v.getId()) & !game_won) {
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

     game_won = GameLogic.winTest(game_table,'X');
            if (game_won) {
               Toast toast = Toast.makeText(MainActivity.this, R.string.You_won_message, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
for (Button b: game_field_buttons) {
   // b.setBackgroundColor(Color.YELLOW);
    b.setEnabled(false);
}

            }


        if   (!checkIfGameOver() & !game_won)
            computerMove();
        }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putSerializable(GAME_TABLE_INDEX, game_table);
        savedInstanceState.putBoolean(GAME_WON_INDEX,game_won);
        savedInstanceState.putIntegerArrayList(USER_PRESSED_BUTTONS_IDS_INDEX,user_pressed_buttons_IDs);
        savedInstanceState.putIntegerArrayList(COMPUTER_PRESSED_BUTTONS_IDS_INDEX,computer_pressed_buttons_IDs);


    }



    private void start_Again() {



        game_won=false;
        user_pressed_buttons_IDs.clear();
        computer_pressed_buttons_IDs.clear();

        for (int i=0;i<7;i++)
            for (int j=0;j<7;j++) game_table[i][j]='1'; //инициализация игровой таблицы

        setContentView(R.layout.activity_main);

        metrics = this.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        GameLayout = findViewById(R.id.grid);

        GameLayout.setColumnCount(7);
        GameLayout.setRowCount(7);

        //for(Button b:game_field_buttons) {b.setText("");GameLayout.addView(button);}


        for ( int i=0;i<49;i++) {

            button = new Button(this);

            button.setTextSize(width/35);
        //    button.setText(String.valueOf(new Integer(i)));

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
        Button button;
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
                game_table[i/7][i%7]='O'; // заполнение игровой таблицы
            }
            break;
        }
    }
      if (GameLogic.winTest(game_table,'O')) Toast.makeText(MainActivity.this,R.string.You_lost_message,Toast.LENGTH_SHORT).show();
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






}
