package com.vlad.android.crossing;

public class GameLogic {

   static protected boolean winTest (char [][] game_table, char sign){

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
