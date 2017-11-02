/* ********************************************************************
   CState
   Forouraghi
***********************************************************************/

import java.util.*;

//********************************************************************
//*** this aux class represents an ADT for checkers states
class CState {

    //*** the evaluation function e(n)
    private double e;

    //*** some board configuration
    private int[][] state;

    //**************************************************************
    CState(int[][] state) {
        this.state = state;
    }

    //**************************************************************
    //*** evaluate a state based on the evaluation function we
    //*** discussed in class
    void evalState() {
        //*** add your own necessary logic here to properly evaluate a state
        //*** I am just assigning some random numbers for demonstration purposes
        int bs, bk, rs, rk;
        bs = bk = rs = rk = 0;
        for (int i = 0; i <= state.length; i++) {
            for (int j = 0; j <= state.length; j++) {
                if (state[i][j] == 1) {
                    bs += 1;
                }//end bs
                if (state[i][j] == 2) {
                    bk += 1;
                }//end bk
                if (state[i][j] == 3) {
                    rs += 1;
                }//end rs
                if (state[i][j] == 4) {
                    rk += 1;
                }//end rk
            }//end inner loop
        }//end outter loop
        e = ((5 * bk + bs) - (5 * rk + rs));//max evaluation function
    }//end evalState


    //**************************************************************
    //*** get a node's E() value
    double getE() {
        return e;
    }

    //**************************************************************
    //*** get a state
    String getState() {
        String result = "";

        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state.length; j++)
                result = result + state[i][j] + " ";

            result += "\n";
        }

        return result;
    }

    private static int[][] makeCopy(int[][] array) {
        int[][] b = new int[array.length][];

        for (int row = 0; row < array.length; ++row) {
            b[row] = new int[array[row].length];
            for (int col = 0; col < b[row].length; ++col) {
                b[row][col] = array[row][col];
            }
        }
        return b;
    }

    public CState clone() {
        return new CState(makeCopy(state));
    }

    //***********************************************************************
    boolean legalPosition(int i) {
        //*** can't go outside board boundaries
        return ((i >= 0) && (i < state.length));
    }

    private CState createState(int i1, int j1, int i2, int j2) {
        int[][] boardPlanCopy = makeCopy(state);
        boardPlanCopy[i2][j2] = boardPlanCopy[i1][j1];
        boardPlanCopy[i1][j1] = 0;

        //*** red single is kinged
        if ((i2 == boardPlanCopy.length - 1) && (boardPlanCopy[i2][j2] == 3)) {
            boardPlanCopy[i2][j2] = 4;
        }

        //*** blue single is kinged
        if ((i2 == 0) && (boardPlanCopy[i2][j2] == 1)) {
            boardPlanCopy[i2][j2] = 2;
        }

        return new CState(boardPlanCopy);
    }

    /*
 Make a method that takes in the old position and the new position
 creates a cstate where the piece is moved to another plcace
  */
    //***********************************************************************
    //*** incorporate your MINIMAX algorithm in here
    //***********************************************************************
    public ArrayList<CState> getChildren(int i, int j, int piece) {
        ArrayList<CState> children = new ArrayList();

        //if blue, check move to top left
        if (legalPosition(i - 1) && legalPosition(j - 1) && (state[i][j] == 1 || state[i][j] == 2)) {
            if (state[i - 1][j - 1] == 0) {
                children.add(createState(i, j, i - 1, j - 1));
            }
            //if space is not empty, then see if we can jump over it
            else if (legalPosition(i - 2) && legalPosition(j - 2) && state[i - 2][j - 2] == 0) {
                if (state[i - 1][j - 1] == 3 || state[i - 1][j - 1] == 4) {
                    children.add(createState(i, j, i - 2, j - 2));

                }
            }
        }
        //if blue, check move to the top right
        if (legalPosition(i - 1) && legalPosition(j + 1) && (state[i][j] == 1 || state[i][j] == 2)) {
            if (state[i - 1][j + 1] == 0) {
                children.add(createState(i, j, i - 1, j + 1));
            }
            //if space is not empty, then see if we can jump over it
            else if (legalPosition(i - 2) && legalPosition(j + 2) && state[i - 2][j + 2] == 0) {
                if (state[i - 1][j + 1] == 3 || state[i - 1][j + 1] == 4) {
                    children.add(createState(i, j, i - 2, j + 2));

                }
            }
        }

        //if red, check move to bottom left
        if (legalPosition(i - 1) && legalPosition(j + 1) && (state[i][j] == 3 || state[i][j] == 4)) {
            if (state[i - 1][j + 1] == 0) {
                children.add(createState(i, j, i + 1, j - 1));
            }
            //if space is not empty, then see if we can jump over it
            else if (legalPosition(i - 2) && legalPosition(j + 2) && state[i - 2][j + 2] == 0) {
                if (state[i - 1][j + 1] == 1 || state[i - 1][j + 1] == 2) {
                    children.add(createState(i, j, i - 2, j + 2));

                }
            }
        }

        //if red, check move to bottom right
        if (legalPosition(i + 1) && legalPosition(j + 1) && (state[i][j] == 3 || state[i][j] == 4)) {
            if (state[i + 1][j + 1] == 0) {
                children.add(createState(i, j, i + 1, j + 1));
            }
            //if space is not empty, then see if we can jump over it
            else if (legalPosition(i + 2) && legalPosition(j + 2) && state[i + 2][j + 2] == 0) {
                if (state[i + 1][j + 1] == 1 || state[i + 1][j + 1] == 2) {
                    children.add(createState(i, j, i + 2, j + 2));

                }
            }
        }

        //if a blue king, check move to bottom left
        if (legalPosition(i - 1) && legalPosition(j + 1) && (state[i][j] == 2)) {
            if (state[i - 1][j + 1] == 0) {
                children.add(createState(i, j, i + 1, j - 1));
            }
            //if space is not empty, then see if we can jump over it
            else if (legalPosition(i - 2) && legalPosition(j + 2) && state[i - 2][j + 2] == 0) {
                if (state[i - 1][j + 1] == 3 || state[i - 1][j + 1] == 4) {
                    children.add(createState(i, j, i - 2, j + 2));

                }
            }
        }

        //if a blue king, check move to bottom right
        if (legalPosition(i + 1) && legalPosition(j + 1) && (state[i][j] == 2)) {
            if (state[i + 1][j + 1] == 0) {
                children.add(createState(i, j, i + 1, j + 1));
            }
            //if space is not empty, then see if we can jump over it
            else if (legalPosition(i + 2) && legalPosition(j + 2) && state[i + 2][j + 2] == 0) {
                if (state[i + 1][j + 1] == 3 || state[i + 1][j + 1] == 4) {
                    children.add(createState(i, j, i + 2, j + 2));

                }
            }
        }

        //if red king, check move to top left
        if (legalPosition(i - 1) && legalPosition(j - 1) && (state[i][j] == 4)) {
            if (state[i - 1][j - 1] == 0) {
                children.add(createState(i, j, i - 1, j - 1));
            }
            //if space is not empty, then see if we can jump over it
            else if (legalPosition(i - 2) && legalPosition(j - 2) && state[i - 2][j - 2] == 0) {
                if (state[i - 1][j - 1] == 1 || state[i - 1][j - 1] == 2) {
                    children.add(createState(i, j, i - 2, j - 2));

                }
            }
        }

        //if red king, check move to top right
        if (legalPosition(i - 1) && legalPosition(j + 1) && (state[i][j] == 4)) {
            if (state[i - 1][j + 1] == 0) {
                children.add(createState(i, j, i - 1, j + 1));
            }
            //if space is not empty, then see if we can jump over it
            else if (legalPosition(i - 2) && legalPosition(j + 2) && state[i - 2][j + 2] == 0) {
                if (state[i - 1][j + 1] == 1 || state[i - 1][j + 1] == 2) {
                    children.add(createState(i, j, i - 2, j + 2));

                }
            }
        }
        return children;
    }
}
