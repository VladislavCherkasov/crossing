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

    public char[][] getGame_table() {
        return game_table;
    }

    private char [][] game_table;
   private boolean game_won;
   private boolean computer_first_move_made;
   private Direction direction;
   private int last_computer_move_button_ID;



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
        computer_first_move_made=false;
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
 //   ((Button) v).setText("X");         //надпись на кнопке
    gd_pressed.setColor(getResources().getColor(R.color.dark_red));

    gd_pressed.setStroke(1, 0xFF000000);
    ((Button) v).setBackground(gd_pressed);

    user_pressed_buttons_IDs.add(v.getId());
    game_table[((int)v.getId()/7)][((int)v.getId()%7)]='X'; // заполнение игровой таблицы
}
             //int a= (v.getId());
          //  Toast toast = Toast.makeText(MainActivity.this,String.valueOf(a),Toast.LENGTH_SHORT);
         //   toast.show();
          //  GameLogic tester = new GameLogic(game_table,'X');

            GameLogic tester = new GameLogic(game_table,'X');
     game_won = tester.winTest();
            if (game_won) {
               Toast toast = Toast.makeText(MainActivity.this, R.string.You_won_message, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
for (Button b: game_field_buttons) {
   // b.setBackgroundColor(Color.YELLOW);
    b.setEnabled(false);
}

            }

if (!computer_first_move_made) computer_first_move_made=computerFirstMove();

else
        if   (!checkIfGameOver() & !game_won)
            computerMove();
     //
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
        computer_first_move_made=false;

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

    private boolean computerFirstMove(){

        Button button;
        int button_ID=0;
        int choose_a_point;

        int choose_a_side = (int) Math.round(Math.random() * 3);

        switch (choose_a_side) {

            case 0:
                while (true) {
                    choose_a_point = (int) Math.round(Math.random() * 6);
                    if (game_table[0][choose_a_point]=='1') {
                        game_table[0][choose_a_point]='O';
                        button_ID=choose_a_point;
                        direction=Direction.DOWN;
                        break;
                    }
                }
                break;

            case 1:
                while (true) {
                    choose_a_point = (int) Math.round(Math.random() * 6);
                    if (game_table[choose_a_point][6]=='1') {
                        game_table[choose_a_point][6]='O';
                        button_ID=7*choose_a_point+6;
                        direction=Direction.LEFT;
                        break;
                    }
                }
                break;

            case 2:
                while (true) {
                    choose_a_point = (int) Math.round(Math.random() * 6);
                    if (game_table[6][choose_a_point]=='1') {
                        game_table[6][choose_a_point]='O';
                        button_ID=42+choose_a_point;
                        direction=Direction.UP;
                        break;
                    }
                }
                break;

            case 3:
                while (true) {
                    choose_a_point = (int) Math.round(Math.random() * 6);
                    if (game_table[choose_a_point][0]=='1') {
                        game_table[choose_a_point][0]='O';
                        button_ID=7*choose_a_point;
                        direction=Direction.RIGHT;
                        break;}
                }
                break;
        }
        computer_pressed_buttons_IDs.add(button_ID);
        button = findViewById(button_ID);
        last_computer_move_button_ID=button_ID;
        if(button!=null) {
         //   button.setText("O"); //надпись на кнопке
            gd_computer_move.setColor(getResources().getColor(R.color.green));

            gd_computer_move.setStroke(1, 0xFF000000);
            button.setBackground(gd_computer_move);

        }

        return true;
    }

/* //рандомный вариант
    private void computerMove() {
        Button button;
      while (true) {
        int i = (int) (49 * Math.random());
        if (!user_pressed_buttons_IDs.contains(i) & !computer_pressed_buttons_IDs.contains(i)) {
            computer_pressed_buttons_IDs.add(i);
            button = findViewById(i);
            last_computer_move_button_ID=i;
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
*/

    private void computerMove() {
        Button button;
        int i=0;

             if (direction==Direction.DOWN)   i = moveDown();
             if (direction==Direction.UP)   i = moveUp();
             if (direction==Direction.LEFT)   i = moveLeft();
             if (direction==Direction.RIGHT)   i = moveRight();




                computer_pressed_buttons_IDs.add(i);
                button = findViewById(i);
                last_computer_move_button_ID=i;
                if(button!=null) {
              //      button.setText("O");   //надпись на кнопке
                    gd_computer_move.setColor(getResources().getColor(R.color.green));

                    gd_computer_move.setStroke(1, 0xFF000000);
                    button.setBackground(gd_computer_move);
                    game_table[i/7][i%7]='O'; // заполнение игровой таблицы
                }




        if (new GameLogic(game_table,'O').winTest()) {
            Toast toast = Toast.makeText(MainActivity.this, R.string.You_lost_message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            for (Button b: game_field_buttons) {

                b.setEnabled(false);
            }
        }
        checkIfGameOver();
    }


private boolean checkIfGameOver() {
        if ((user_pressed_buttons_IDs.size()+computer_pressed_buttons_IDs.size())==49) {
            Toast toast = Toast.makeText(MainActivity.this,R.string.Game_over_message,Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            for (Button b: game_field_buttons) {
                b.setEnabled(false);
            }
            return true;

        }
        else return false;
}

private int moveDown(){

    ArrayList<Integer> last_line_buttons_iDs= new ArrayList<>(); //проверка на конец строки
    for (int i=42;i<48;i++) last_line_buttons_iDs.add(i);
    if (last_line_buttons_iDs.contains(last_computer_move_button_ID)) return last_computer_move_button_ID;

        if (game_table[((int)last_computer_move_button_ID/7)+1][((int)last_computer_move_button_ID%7)]!='X') {
            game_table[((int) last_computer_move_button_ID / 7) + 1][((int) last_computer_move_button_ID % 7)] = 'O'; // заполнение игровой таблицы
            int button_ID = last_computer_move_button_ID + 7;
            return button_ID;
        }
        else
            {                                                             //поиск обходного пути
            int result = analyseRow(last_computer_move_button_ID);
            int difference = last_computer_move_button_ID%7-result;
          if (result==7)  return last_computer_move_button_ID;
          if (difference==-1) return moveRightandDown();
          if (difference<-1) return moveRight();
          if (difference==1) return moveLeftandDown();
          if (difference>1) return moveLeft();
        }
        return last_computer_move_button_ID;
}

    private int moveUp(){

        ArrayList<Integer> first_line_buttons_iDs= new ArrayList<>(); //проверка на конец строки
        for (int i=0;i<7;i++) first_line_buttons_iDs.add(i);
        if (first_line_buttons_iDs.contains(last_computer_move_button_ID)) return last_computer_move_button_ID;

            if (game_table[((int) last_computer_move_button_ID / 7) - 1][((int) last_computer_move_button_ID % 7)] != 'X') {
                game_table[((int) last_computer_move_button_ID / 7) - 1][((int) last_computer_move_button_ID % 7)] = 'O'; // заполнение игровой таблицы
                int button_ID = last_computer_move_button_ID - 7;
                return button_ID;
            }
            else                                                           //поиск обходного пути
            {
                int result = analyseRow(last_computer_move_button_ID);
                int difference = last_computer_move_button_ID%7-result;
                if (result==7)  return last_computer_move_button_ID;
                if (difference==-1) return moveRightandUp();
                if (difference<-1) return moveRight();
                if (difference==1) return moveLeftandUp();
                if (difference>1) return moveLeft();
            }
                return last_computer_move_button_ID;


    }

    private int moveRight() {
        if (last_computer_move_button_ID==6|last_computer_move_button_ID==13|last_computer_move_button_ID==20|last_computer_move_button_ID==27|
                last_computer_move_button_ID==34|last_computer_move_button_ID==41|last_computer_move_button_ID==48)
            return last_computer_move_button_ID; //проверка на конец строки

        if (game_table[((int) last_computer_move_button_ID/7)][((int) last_computer_move_button_ID%7+1)] != 'X') {
            game_table[((int) last_computer_move_button_ID / 7)][((int) last_computer_move_button_ID % 7 + 1)] = 'O'; // заполнение игровой таблицы
            int button_ID = last_computer_move_button_ID + 1;
            return button_ID;
        }
        else
        {
            int result = analyseColumn(last_computer_move_button_ID);
            int difference = last_computer_move_button_ID/7-result;
            if (result==7)  return last_computer_move_button_ID;
            if (difference==-1) return moveRightandDown();
            if (difference<-1) return moveDown();
            if (difference==1) return moveRightandUp();

            if (difference>1) return moveUp();

        }
            return last_computer_move_button_ID;
    }

    private int moveLeft() {

        if (last_computer_move_button_ID==0|last_computer_move_button_ID==7|last_computer_move_button_ID==14|last_computer_move_button_ID==21|
                last_computer_move_button_ID==28|last_computer_move_button_ID==35|last_computer_move_button_ID==42)
            return last_computer_move_button_ID; //проверка на конец строки

        if (game_table[((int) last_computer_move_button_ID/7)][((int) last_computer_move_button_ID%7-1)] != 'X') {
            game_table[((int) last_computer_move_button_ID / 7)][((int) last_computer_move_button_ID % 7 - 1)] = 'O'; // заполнение игровой таблицы
            int button_ID = last_computer_move_button_ID - 1;
            return button_ID;
        }
        else
        {

            int result = analyseColumn(last_computer_move_button_ID);
            int difference = last_computer_move_button_ID/7-result;
            if (result==7)  return last_computer_move_button_ID;
            if (difference==-1) return moveLeftandDown();
            if (difference<-1) return moveDown();
            if (difference==1) return moveLeftandUp();


            if (difference>1) return moveUp();


        }
            return last_computer_move_button_ID;
    }

    private int moveRightandUp(){

        if (last_computer_move_button_ID==6|last_computer_move_button_ID==13|last_computer_move_button_ID==20|last_computer_move_button_ID==27|
                last_computer_move_button_ID==34|last_computer_move_button_ID==41|last_computer_move_button_ID==48)
            return last_computer_move_button_ID; //проверка на конец строки

        ArrayList<Integer> first_line_buttons_iDs= new ArrayList<>(); //проверка на конец строки
        for (int i=0;i<7;i++) first_line_buttons_iDs.add(i);
        if (first_line_buttons_iDs.contains(last_computer_move_button_ID)) return last_computer_move_button_ID;

        if (game_table[((int)last_computer_move_button_ID/7)-1][((int)last_computer_move_button_ID%7+1)]!='X') {
            game_table[((int) last_computer_move_button_ID / 7) - 1][((int) last_computer_move_button_ID % 7 + 1)] = 'O'; // заполнение игровой таблицы
            int button_ID = last_computer_move_button_ID - 6;
            return button_ID;
        }
        else return last_computer_move_button_ID;
    }

    private int moveRightandDown(){

        if (last_computer_move_button_ID==6|last_computer_move_button_ID==13|last_computer_move_button_ID==20|last_computer_move_button_ID==27|
                last_computer_move_button_ID==34|last_computer_move_button_ID==41|last_computer_move_button_ID==48)
            return last_computer_move_button_ID; //проверка на конец строки

        ArrayList<Integer> last_line_buttons_iDs= new ArrayList<>(); //проверка на конец строки
        for (int i=42;i<48;i++) last_line_buttons_iDs.add(i);
        if (last_line_buttons_iDs.contains(last_computer_move_button_ID)) return last_computer_move_button_ID;

        if (game_table[((int)last_computer_move_button_ID/7)+1][((int)last_computer_move_button_ID%7+1)]!='X') {
            game_table[((int) last_computer_move_button_ID / 7) + 1][((int) last_computer_move_button_ID % 7 + 1)] = 'O'; // заполнение игровой таблицы
            int button_ID = last_computer_move_button_ID + 8;
            return button_ID;
        }
        else return last_computer_move_button_ID;
    }

    private int moveLeftandUp() {

        if (last_computer_move_button_ID==0|last_computer_move_button_ID==7|last_computer_move_button_ID==14|last_computer_move_button_ID==21|
                last_computer_move_button_ID==28|last_computer_move_button_ID==35|last_computer_move_button_ID==42)
            return last_computer_move_button_ID; //проверка на конец строки

        ArrayList<Integer> first_line_buttons_iDs= new ArrayList<>(); //проверка на конец строки
        for (int i=0;i<7;i++) first_line_buttons_iDs.add(i);
        if (first_line_buttons_iDs.contains(last_computer_move_button_ID)) return last_computer_move_button_ID;

        if (game_table[((int) last_computer_move_button_ID/7)-1][((int) last_computer_move_button_ID%7-1)] != 'X') {
            game_table[((int) last_computer_move_button_ID / 7) - 1][((int) last_computer_move_button_ID % 7 - 1)] = 'O'; // заполнение игровой таблицы
            int button_ID = last_computer_move_button_ID - 8;
            return button_ID;
        }
        else return last_computer_move_button_ID;
    }

    private int moveLeftandDown() {

        if (last_computer_move_button_ID==0|last_computer_move_button_ID==7|last_computer_move_button_ID==14|last_computer_move_button_ID==21|
                last_computer_move_button_ID==28|last_computer_move_button_ID==35|last_computer_move_button_ID==42)
            return last_computer_move_button_ID; //проверка на конец строки

        ArrayList<Integer> last_line_buttons_iDs= new ArrayList<>(); //проверка на конец строки
        for (int i=42;i<48;i++) last_line_buttons_iDs.add(i);
        if (last_line_buttons_iDs.contains(last_computer_move_button_ID)) return last_computer_move_button_ID;


        if (game_table[((int) last_computer_move_button_ID/7)+1][((int) last_computer_move_button_ID%7-1)] != 'X') {
            game_table[((int) last_computer_move_button_ID / 7) + 1][((int) last_computer_move_button_ID % 7 - 1)] = 'O'; // заполнение игровой таблицы
            int button_ID = last_computer_move_button_ID + 6;
            return button_ID;
        }
        else return last_computer_move_button_ID;
    }

    int analyseRow(int buttonID){    //возвращает номер столбца в анализируемой строке, куда можно сделать ход. 7 - если некуда
        int button_row_number = buttonID/7;
        int button_column_number = buttonID%7;
        int difference;
        int resulting_difference=6;
        int result=7;


        if (direction==Direction.DOWN) {
            for (int n = 0; n < 7; n++) {
                if (game_table[button_row_number + 1][n] == '1') {
                    difference = Math.abs(button_column_number - n);
                    if (difference < resulting_difference) {
                        resulting_difference = difference;
                        result = n;
                    }
                }
            }
        }
        if (direction==Direction.UP)
        {

            for (int n = 0; n < 7; n++) {
                if (game_table[button_row_number - 1][n] == '1') {
                    difference = Math.abs(button_column_number - n);
                    if (difference < resulting_difference) {
                        resulting_difference = difference;
                        result = n;
                    }
                }
            }

        }

        return result;
    }

    int analyseColumn(int buttonID){

        int button_row_number = buttonID/7;
        int button_column_number = buttonID%7;
        int difference;
        int resulting_difference=6;
        int result=7;

        if (direction==Direction.RIGHT) {

            for (int n = 0; n < 7; n++) {
                if (game_table[n][button_column_number+1] == '1') {
                    difference = Math.abs(button_row_number - n);
                    if (difference < resulting_difference) {
                        resulting_difference = difference;
                        result = n;
                    }
                }
            }
            return result;
        }

        if (direction==Direction.LEFT) {

            for (int n = 0; n < 7; n++) {
                if (game_table[n][button_column_number - 1] == '1') {
                    difference = Math.abs(button_row_number - n);
                    if (difference < resulting_difference) {
                        resulting_difference = difference;
                        result = n;
                    }
                }
            }
            return result;
        }

        return 7;
    }





}
