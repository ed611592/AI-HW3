/* ********************************************************************
   CState
   Forouraghi
***********************************************************************/

import java.util.*; 

//********************************************************************
//*** this aux class represents an ADT for checkers states
class CState
{

      //*** the evaluation function e(n)
      private double e;

      //*** node type: MAX or MIN
      private String type;

      //*** some board configuration
      private int [][] state;

      //**************************************************************
      CState(int [][] state, String type)
      {
          this.state = state;
          this.type  = type;
      }

      //**************************************************************
      //*** evaluate a state based on the evaluation function we
      //*** discussed in class
      void evalState()
      {
          //*** add your own necessary logic here to properly evaluate a state
          //*** I am just assigning some random numbers for demonstration purposes
          int bs, bk, rs, rk;
          bs = bk = rs = rk = 0;
          for(int i = 0; i <= state.length; i++){
              for(int j = 0; j <= state.length; j++){
                  if( state[i][j] == 1){
                      bs += 1;
                  }//end bs
                  if( state[i][j] == 2){
                      bk += 1;
                  }//end bk
                  if( state[i][j] == 3){
                      rs += 1;
                  }//end rs
                  if( state[i][j] == 4){
                      rk += 1;
                  }//end rk
              }//end inner loop
          }//end outter loop
          e = ((5*bk + bs)-(5*rk + rs));//max evaluation function
      }//end evalState


    //**************************************************************
      //*** get a node's E() value
      double getE()
      {
         return e;
      }

      //**************************************************************
      //*** get a node's type
      String getType()
      {
         return type;
      }

      //**************************************************************
      //*** get a state
      String getState()
      {
         String result = "";

         for (int i=0; i<state.length; i++)
           {
            for (int j=0; j<state.length; j++)
                result = result + state[i][j] + " ";

            result += "\n";
           }

         return result;
      }
}
