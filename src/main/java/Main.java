/*
 * WHAT TO ADD -- ERROR HANDLING AND COMMENTS BEFORE SUBMITTING TO GITHUB
 *             --MOVE ALL MAIN STUFF TO ANOTHER CLASS
 */
import java.util.*; 
       
/**
 *
 * @author 
 */ 
        
public class Main {
    int row;
    int col;
    int numBomb;
    char[][] viewBoard = new char[100][100]; //Mx board size
    int[][] bgBoard = new int[100][100];
    int revCnt = 0;

    public Main(int row, int col, int numBomb) 
    { 
        this.row = row; 
        this.col = col; 
        this.numBomb = numBomb; 
    } 
    public void buildBoard(){
        Random rand = new Random();
        int cnt = 0;
        int[] rmax = {-1, 0, 1};
        int[] cmax = {-1, 0, 1};
        //initialize background board
        while(cnt < numBomb){
            int rowRand = rand.nextInt(this.row);
            int colRand = rand.nextInt(this.row);
            if(this.bgBoard[rowRand][colRand] != -1){
                this.bgBoard[rowRand][colRand] = -1;
                //increment cells around bomb
                for(int i : rmax){
                    for(int j: cmax){
                        if((rowRand + i>=0 && rowRand+i < this.row && colRand + j>=0 && colRand+j<this.col) && this.bgBoard[rowRand+i][colRand+j] != -1){
                            this.bgBoard[rowRand+i][colRand+j]++;
                        }
                    }
                }
                
                cnt++;
            }
        }
        
        //initialzie board that gets viewed
        for(int i =0; i<this.row; i++){
            for(int j =0; j <this.col; j++) {
                this.viewBoard[i][j] = '-';
            }
        }
        
    }
    
    boolean checkCoordiante(int X, int Y){
        if(this.bgBoard[X][Y]  > 0){
            this.viewBoard[X][Y] = (char) (this.bgBoard[X][Y]+ '0');
            this.revCnt++;
            return false;
        }else if (this.bgBoard[X][Y] == -1){
            this.viewBoard[X][Y] = 'X';
            return true;
        }else if (this.bgBoard[X][Y] == 0){
            uncoverZero(X, Y);
            return false;
        }
        
        return false;
    }
    
    void uncoverZero(int X, int Y){
        this.revCnt++;
        this.viewBoard[X][Y] = '0';
        int[] rmax = {-1, 0, 1};
        int[] cmax = {-1, 0, 1};
        for(int i : rmax){
            for(int j: cmax){
                if(i!= 0 || j!=0){
                    if(X+i >=0 && X+i < this.row && Y+j>=0 && Y+j<this.col){
                        if(this.bgBoard[X+i][Y+j]==0 && this.viewBoard[X+i][Y+j] == '-'){
                            uncoverZero(X+i, Y+j);
                        }else if(this.bgBoard[X+i][Y+j]> 0 && this.viewBoard[X+i][Y+j] == '-'){
                            this.viewBoard[X+i][Y+j] = (char) (this.bgBoard[X+i][Y+j]+ '0');
                            this.revCnt++;
                        }
                    }
                }
            }
        }
    }
    
    void displayBoard(){
        System.out.print("   ");
        for(int i =0; i < this.col; i++){
            System.out.print(Integer.toString(i)+"  ");
        }
        System.out.print("\n");
        for(int i =0; i < this.row; i++){
            //System.out.println(Integer.toString(i)+" " +Arrays.toString(Arrays.copyOfRange(arr, 0viewBoard[i]));
            System.out.println(Integer.toString(i)+" " +Arrays.toString(Arrays.copyOfRange(this.viewBoard[i],0 ,col)));
        }
    }
    
    public static void main(String[] args) {
        Main Game = new Main(5, 5, 5); //construct game(row, col, number of bombs)
        Scanner getInput = new Scanner(System.in); //for the inputed character
        Game.buildBoard();
        Game.displayBoard();
        while(true){
            System.out.println("Enter row: ");
            int inX = getInput.nextInt();
            System.out.println("Enter column ");
            int inY = getInput.nextInt();

            if(Game.checkCoordiante(inX, inY)){
                System.out.println("You Lose!");
                break;
            }
            if(Game.revCnt == Game.row*Game.col - Game.numBomb){
                System.out.println("You Win!");
                break;
            }
            
            Game.displayBoard();
        }
        
        Game.displayBoard();

        
    }
    
}
