package com.vlad.android.crossing;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
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
    DisplayMetrics metrics ;
    int width ;
    ArrayList<Integer> pressed_buttons_IDs = new ArrayList<>();
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







        for ( int i=0;i<49;i++){

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
if(!user_pressed_buttons_IDs.contains(v.getId()) & !computer_pressed_buttons_IDs.contains(v.getId())) {
    ((Button) v).setText("X");
    gd_pressed.setColor(getResources().getColor(R.color.dark_red));

    gd_pressed.setStroke(1, 0xFF000000);
    ((Button) v).setBackground(gd_pressed);

    user_pressed_buttons_IDs.add(v.getId());
}
            Toast toast = Toast.makeText(MainActivity.this,String.valueOf(v.getId()),Toast.LENGTH_SHORT);

            toast.show();
            computerMove();
        }



    private void start_Again() {

        setContentView(R.layout.activity_main);
        user_pressed_buttons_IDs.clear();
        computer_pressed_buttons_IDs.clear();
        metrics = this.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        GameLayout = findViewById(R.id.grid);

        GameLayout.setColumnCount(7);
        GameLayout.setRowCount(7);

        for ( int i=0;i<49;i++){

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
            }
            break;
        }
    }
    }



}
