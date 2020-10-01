import java.util.ArrayList;
import java.util.Random;

public class Game {
    public int[][] tiles;
    public int[][] tempTiles;
    private Random r = new Random();
    public int Score ;
    public int tileMoves = 0 ;
    public boolean dontAdd = false;

    public Game() {
        tempTiles = new int[4][4] ;
        tiles = new int[4][4];
        addNumber();
        addNumber();
// tiles = new int[][]{
//                {1,2,3,4},
//                {5,6,7,8},
//                {9,10,10,1},
//                {2,3,4,5}
//        };
    }

    public void printer() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                System.out.print(tiles[x][y] + "\t");
            }
            System.out.println();
        }
    }

    public void makeThemSame(int a[][] , int b[][]) {
        for (int i = 0 ; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                a[i][j] = b[i][j];
            }
        }
    }

    public boolean areEquals(int a[][] , int b[][]){
        for(int i=0 ; i<4 ; i++){
            for(int j=0 ; j<4 ; j++){
                if(a[i][j] != b[i][j]){
                    return false ;
                }
            }
        }
        return true ;
    }

    public boolean isFull() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (tiles[x][y] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void res() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                tiles[x][y] = 0;
            }
        }
        tileMoves = 0 ;
        Score = 0;
        addNumber();
        addNumber();
    }

    public void addNumber() {
        if (isFull() || dontAdd) {
            return;
        } else {
            ArrayList<Integer> emptyX = new ArrayList<Integer>();
            ArrayList<Integer> emptyY = new ArrayList<Integer>();
            //Coordinate of empty spaces will store in these two array lists .
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    if (tiles[x][y] == 0) {
                        emptyX.add(new Integer(x));
                        emptyY.add(new Integer(y));
                    }
                }
            }
            int choice = r.nextInt(emptyX.size()); //Randomly choose an empty space to add new number in .
            int numberChooser = r.nextInt(10); //Values changes between 0 to 9
            int newNumber = 1; //2^1 with 90% chance
            if (numberChooser == 0) {
                newNumber = 2; //2^2 with 10% chance
            }
            int X = emptyX.get(choice);
            int Y = emptyY.get(choice);
            tiles[X][Y] = newNumber;
            tileMoves++ ;
        }
    }

    public void Up() {
        makeThemSame(tempTiles, tiles);
        System.out.println("Moving up ...");
        for (int y = 0; y < 4; y++) {
            for (int x = 1; x < 4; x++) {
                if (tiles[x][y] != 0) {
                    int temp = tiles[x][y];
                    int X = x - 1;
                    while ((X >= 0) && (tiles[X][y] == 0)) {
                        X--;
                    }
                    if (X == -1) { //There's no numbers above in the column.
                        tiles[0][y] = temp ;
                        tiles[x][y] = 0 ;
                    } else if (tiles[X][y] != temp) { //Above number is not equal to current number.
                        tiles[x][y] = 0;
                        tiles[X + 1][y] = temp;
                    } else { //Above number is equal to current number.
                        tiles[X][y] += 1;
                        tiles[x][y] = 0;
                        Score += Math.pow(2, tiles[X][y]);
                    }
                }
            }
        }
        if(areEquals(tempTiles,tiles)) dontAdd = true;
    }

    public void Down() {
        makeThemSame(tempTiles, tiles);
        System.out.println("Moving down ...");
        for (int y = 0; y < 4; y++) {
            for (int x = 2; x >= 0; x--) {
                if (tiles[x][y] != 0) {
                    int temp = tiles[x][y];
                    int X = x + 1;
                    while ((X <= 3) && (tiles[X][y] == 0)) {
                        X++;
                    }
                    if (X == 4) { //There's no numbers below in the column.
                        tiles[3][y] = temp;
                        tiles[x][y] = 0;
                    } else if (tiles[X][y] != temp) { //Below number is not equal to current number.
                        tiles[x][y] = 0;
                        tiles[X - 1][y] = temp;
                    } else { //Below number is equal to current number.
                        tiles[X][y] += 1;
                        tiles[x][y] = 0;
                        Score += Math.pow(2, tiles[X][y]);
                    }
                }
            }
        }
        if(areEquals(tempTiles,tiles)) dontAdd = true;
    }

    public void Left() {
        makeThemSame(tempTiles, tiles);
        System.out.println("Moving left ...");
        for (int x = 0; x < 4; x++) {
            for (int y = 1; y < 4; y++) {
                if (tiles[x][y] != 0) {
                    int temp = tiles[x][y];
                    int Y = y - 1;
                    while ((Y >= 0) && (tiles[x][Y] == 0)) {
                        Y--;
                    }
                    if (Y == -1) { //There's no numbers left in the row.
                        tiles[x][0] = temp;
                        tiles[x][y] = 0;
                    } else if (tiles[x][Y] != temp) { //Left number is not equal to current number.
                        tiles[x][y] = 0;
                        tiles[x][Y + 1] = temp;
                    } else { //Left number is equal to current number.
                        tiles[x][Y] += 1;
                        tiles[x][y] = 0;
                        Score += Math.pow(2, tiles[x][Y]);

                    }
                }
            }
        }
        if(areEquals(tempTiles,tiles)) dontAdd = true;
    }

    public void Right() {
        makeThemSame (tempTiles, tiles);
        System.out.println("Moving right  ...");
        for (int x = 0; x < 4; x++) {
            for (int y = 2; y >= 0; y--) {
                if (tiles[x][y] != 0) {
                    int temp = tiles[x][y];
                    int Y = y + 1;
                    while ((Y <= 3) && (tiles[x][Y] == 0)) {
                        Y++;
                    }
                    if (Y == 4) { //There's no numbers right in the row.
                        tiles[x][3] = temp;
                        tiles[x][y] = 0;
                    } else if (tiles[x][Y] != temp) { //Right number is not equal to current number.
                        tiles[x][y] = 0;
                        tiles[x][Y - 1] = temp;
                    } else { //Right number is equal to current number.
                        tiles[x][Y] += 1;
                        tiles[x][y] = 0;
                        Score += Math.pow(2, tiles[x][Y]);
                    }
                }
            }
        }
        if(areEquals(tempTiles,tiles)) dontAdd = true;
    }

    public boolean win() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (tiles[i][j] == 11) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canMove() {
        if (isFull()) {
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    if (x == 0 && y != 0) {
                        if (tiles[x][y] == tiles[x][y - 1]) {
                            return true;
                        }
                    } else if (x != 0 && y !=0) {
                        //if (y != 0)
                            if (tiles[x][y] == tiles[x][y - 1]) return true;
                        if (tiles[x][y] == tiles[x - 1][y]) return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

}