/* **********************************************************************
   Checkers
   Forouraghi 

   This is a very simple graphical interface for your MINIMAX algorithm:

     -- boardPlan[][] keeps track of what pieces are to be displayed on the
        board

     -- there are four pieces: blue/red single pieces bs/rs
                               blue/red kings         bk/rk

     -- interface movePiece(from_i, from_j, to_i, to_j, piece) moves the
        requested "piece" from boardPlan[from_i][from_j] to
        boardPlan[to_i][to_j]

   You would only need to worry about what piece is being moved from what
   position to what other position; capturing of intermediate pieces is
   handled by the existing code.

   ***Warning***
   I didn't have time to implement a check to see whether an attempt is
   made to jump over one's own piece, i.e., blue over blue or red over red.
*
************************************************************************ */

import java.awt.*;
import javax.swing.*;
import java.applet.*;
import java.net.*;
import java.util.*;

//***********************************************************************
public class Checkers extends JFrame
{

   /*   set up an 8 by 8 checkers board with only five pieces
        legends:
                0   - empty
                1/2 = blue  single/king
                3/4 = red   single/king
   */
   private static int [][] boardPlan =
      {
         {0, 0, 0, 0, 0, 0, 0, 0},   //*** blue pieces become king here
         {0, 0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0, 1},
         {1, 0, 4, 0, 3, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 2, 0, 0, 0}    //*** red pieces become king here
      };

   //*** the legend strings
   private String[] legend = {"blank", "bs", "bk", "rs", "rk" };

   //*** create the checkers board
   private GameBoard board;

   //*** the xy dimensions of each checkers cell on board
   private int cellDimension;

   //*** pause in seconds inbetween moves
   private static int pauseDuration = 500;

   //***********************************************************************
   Checkers()
   {
       //*** set up the initial configuration of the board
       board = new GameBoard(boardPlan);

       //*** each board cell is 70 pixels long and wide
       cellDimension = 70;

       //*** set up the frame containign the board
       getContentPane().setLayout(new GridLayout());
       setSize(boardPlan.length*cellDimension, boardPlan.length*cellDimension);
       setDefaultCloseOperation(EXIT_ON_CLOSE);
       getContentPane().add(board);

       //*** enable viewer
       setVisible(true);

       //*** place all initial pieces on board and pause a bit
       putInitialPieces();
       pause(2*pauseDuration);
   }


   //***********************************************************************
   public void pause(int milliseconds)
   {
      try
         {Thread.sleep(milliseconds);}
      catch (Exception e)
         {}
   }


   //***********************************************************************
   void putPiece(int i, int j, String piece)
   {
      //*** can do error checking here to make sure pieces are bs, bk, rs, rk
      board.drawPiece(i, j, "images/" + piece + ".jpg");
   }


   //***********************************************************************
   void putInitialPieces()
   {
      //*** use legend variables to draw one piece at a time
      for (int i=0; i<boardPlan.length; i++)
         for (int j=0; j<boardPlan.length; j++)
            if (boardPlan[i][j] != 0)
                  board.drawPiece(i, j, "images/" + legend[boardPlan[i][j]] + ".jpg");
   }


   //***********************************************************************
   boolean legalPosition(int i)
   {
      //*** can't go outside board boundaries
      return ((i>=0) && (i<boardPlan.length));
   }


   //***********************************************************************
   void movePiece(int i1, int j1, int i2, int j2, String piece)
   {
      //*** raise exception if outside the board or moving into a non-empty
      //*** cell
      if ((boardPlan[i2][j2] != 0) || !legalPosition(i1) || !legalPosition(i2)
                                   || !legalPosition(j1) || !legalPosition(j1))
            throw new IllegalMoveException("An illegal move was attempted.");

      //*** informative console messages
      System.out.println("Moved " + piece + " from position [" +
                         i1 + ", " + j1 + "] to [" + i2 + ", " + j2 + "]");

      //*** erase the old cell
      board.drawPiece(i1, j1, "images/blank.jpg");

      //*** draw the new cell
      board.drawPiece(i2, j2, "images/" + piece + ".jpg");

      //*** erase any captured piece from the board
      if ((Math.abs(i1-i2) == 2) && (Math.abs(j1-j2) == 2))
         {
            //*** this handles  hops of length 2
            //*** the captured piece is halfway in between the two moves
            int captured_i = i1 + (i2-i1)/2;
            int captured_j = j1 + (j2-j1)/2;

            //*** now wait a bit
            pause(pauseDuration);

            //*** erase the captured cell
            board.drawPiece(captured_i, captured_j, "images/blank.jpg");

            //*** print which piece was captured
            System.out.println("Captured " + legend[boardPlan[captured_i][captured_j]] +
                               " from position [" + captured_i + ", " + captured_j + "]");

            //*** the captured piece is removed from the board with a bang
            boardPlan[captured_i][captured_j] = 0;
            Applet.newAudioClip(getClass().getResource("images/hit.wav")).play();
         }

      //*** update the internal representation of the board by moving the old
      //*** piece into its new position and leaving a blank in its old position
      boardPlan[i2][j2] = boardPlan[i1][j1];
      boardPlan[i1][j1] = 0;

      //*** red single is kinged
      if ( (i2==boardPlan.length-1) && (boardPlan[i2][j2] == 3) )
         {
          boardPlan[i2][j2] = 4;
          putPiece(i2, j2, "rk");
         }

      //*** blue single is kinged
      if ( (i2==0) && (boardPlan[i2][j2] == 1) )
         {
          boardPlan[i2][j2] = 2;
          putPiece(i2, j2, "bk");
         }

      //*** now wait a bit
      pause(pauseDuration);
   }

   CState createPiece(int i1, int j1, int i2, int j2){
      CState new_state = new CState();
      return new_state;
   }
   /*
   Make a method that takes in the old position and the new position
   creates a cstate where the piece is moved to another plcace
    */
   //***********************************************************************
   //*** incorporate your MINIMAX algorithm in here
   //***********************************************************************
   ArrayList<CState> getChildren(int i, int j, String piece){
      ArrayList<CState> children = new ArrayList();

      //check move to top left of this blue piece
      if(legalPosition(i-1) && legalPosition(j-1) && (boardPlan[i][j]==1 || boardPlan[i][j]==2)){
         if (boardPlan[i-1][j-1]==0){
            children.add(createPiece(i,j, i-1, j-1));
         }
         //if space is not empty, then see if we can jump over it
         else if (legalPosition(i-2) && legalPosition(j-2) && boardPlan[i-2][j-2]==0){
            if(boardPlan[i-1][j-1]==1 || boardPlan[i-1][j-1]==2){
               children.add(createPiece((i,j,i-2,j-2));

            }
         }
      }
      //move to the top right of blue piece
      if(legalPosition(i-1) && legalPosition(j+1) && (boardPlan[i][j]==1 || boardPlan[i][j]==2)){
         if (boardPlan[i-1][j+1]==0){
            children.add(createPiece(i,j, i-1, j+1));
         }
         //if space is not empty, then see if we can jump over it
         else if (legalPosition(i-2) && legalPosition(j+2) && boardPlan[i-2][j+2]==0){
            if(boardPlan[i-1][j+1]==1 || boardPlan[i-1][j+1]==2){
               children.add(createPiece((i,j,i-2,j+)));

            }
         }
      }

      return children;
   }

   //find max value of two numbers
   static double max(double v1, double v2){
      if(v1 > v2){
         return v1;
      }
      return v2;
   }//end max

   //find the min of two numbers
   static double min(double v1, double v2){
      if(v1 > v2){
         return v2;
      }
      return v1;
   }//end min

   public static double minimax(Checkers game, int depth, boolean max){
      double best = 0;
      if( max == true) {
         CState boardNode = new CState(game.getState(), "MAX");
         if(depth == 0){// || ) //todo add or a winner
            currentBoard.evalState();
            game.getState();
            return currentBoard.getE(); //value of the boardstate
         }//end if
         best = Double.MIN_VALUE;
         ArrayList<CState> children = new ArrayList();
         children = getChildren(currentBoard);
         for(int i = 0; i < children.size(); i++){
            double current_val = minimax(children.get(i), depth-1, false);
            best = max(best, current_val);
         }
         return best;
      }//end MAX turn

      if( max == false){
         // CState boardNode = new CState(currentBoard, "MAX");
         if(depth == 0){// || ) //todo add or a winner
            currentBoard.evalState();
            return currentBoard.getE(); //value of the boardstate
         }//end if
         best = Double.MAX_VALUE;
         ArrayList<CState> children = new ArrayList();
         children = getChildren(currentBoard);
         for(int i = 0; i < children.size(); i++){
            double current_val = minimax(children.get(i), depth-1, true);
            best = min(best, current_val);
         }
         return best;
      }//end MIN turn

      return best;
   }

   public static void main(String [] args)
   {
        //*** create a new game and make it visible
        Checkers game = new Checkers();

      double move = 0;
      move = minimax(game, 3, true);
        //*** arbitrarily move a few pieces around
        //while (!game.done())
        {
            try
               {
               //*** move a bs piece from board[4][7] to board[3][6]
               game.movePiece(4, 7, 3, 6, "bs");

               //*** move the rk from board[5][2] to board[4][1]
               game.movePiece(5, 2, 4, 1, "rk");

               //*** move a bs piece from board[5][0] to board[3][2]
               //*** must capture rk in position board[4][1]
               game.movePiece(5, 0, 3, 2, "bs");

               //*** move the bk from board[7][4] to board[6]1]
               game.movePiece(7, 4, 6, 3, "bk");

               //*** move the rs from board[5][4] to board[7][2]
               game.movePiece(5, 4, 7, 2, "rs");

               //*** a few more moves just for fun
               game.movePiece(3, 2, 2, 3, "bs");
               game.movePiece(2, 3, 1, 4, "bs");
               game.movePiece(1, 4, 0, 5, "bs");
               game.movePiece(3, 6, 2, 5, "bs");
               game.movePiece(2, 5, 1, 4, "bs");
               game.movePiece(1, 4, 0, 3, "bs");
               }

           catch (IllegalMoveException e)
               {e.printStackTrace();}
        }

   }
}
