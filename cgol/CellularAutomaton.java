import java.util.Random;

public class CellularAutomaton {
    private static final int COLS = 119; // 158 // 119 // 87
    private static final int ROWS = 66; // 70  // 59  // 42
    private static final double FPS = 30; // Frames per second
    private static Random random = new Random();
    private static boolean[][] grid = new boolean[ROWS][COLS];

    public static void main(String[] args) {
        // Initialize grid with sparse live cells (1)
        for (int i = 0; i < ROWS; i++) 
            for (int j = 0; j < COLS; j++) 
                grid[i][j] = random.nextDouble() < 0.3;

        // simulate the cellular automaton
        int iter=0; 
        while (true) { 
            long startTime = System.currentTimeMillis(); // get loop start time

            // Simulation step
            if (iter%(int)(FPS/3) == 0) random_flip(); // flip a random cell 3 times per second
            updateGrid();
            printGrid();
    
            // sleep for the remaining time to maintain FPS
            iter++;
            long sleepTime = (long) (1000 / FPS) - ( System.currentTimeMillis() - startTime);
            if (sleepTime > 0) {
                try { Thread.sleep(sleepTime); } 
                catch (InterruptedException e) { System.out.println(e);}
            }
        }
    }

    private static void random_flip() {
        int ri = random.nextInt(ROWS);
        int rj = random.nextInt(COLS);
        grid[ri][rj] = !grid[ri][rj]; // flip the cell
    }

    private static void updateGrid() {
        boolean[][] newGrid = new boolean[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                int n = countNeighbors(i, j);
                newGrid[i][j] = grid[i][j] ? (n == 2 || n == 3) : (n == 3);
            }
        }
        grid = newGrid;
    }

    private static int countNeighbors(int i, int j) {
        int count = 0; // initialize count
        int[][] directions = { {-1,-1}, {-1,0}, {-1,1}, {0,-1}, {0,+1}, {+1,-1}, {+1,0}, {+1,1} };
        for (int[] d : directions) {
            int ni = (i + d[0] + ROWS) % ROWS; // wrap around
            int nj = (j + d[1] + COLS) % COLS; // wrap around
            if (grid[ni][nj]) count++; // increment count if neighbor is alive
        }
        return count;
    }

    // private static int countNeighbors(int i, int j) {
    //     int count = 0; // initialize count
    //     for (int x = -1; x <= 1; x++) { // [-1, 0, 1]
    //         for (int y = -1; y <= 1; y++) { // [-1, 0, 1]
    //             if (x == 0 && y == 0) continue; // skip the cell itself
    //             int ni = (i + x + ROWS) % ROWS; // wrap around
    //             int nj = (j + y + COLS) % COLS; 
    //             count += grid[ni][nj] ? 1 : 0; // count live neighbors
    //         }
    //     }
    //     return count;
    // }

    private static void printGrid() {
        String to_ret = "";
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                to_ret += grid[i][j] ? "██" : "  ";
            }
            to_ret += "\n";
        }
        to_ret = to_ret.substring(0, to_ret.length() - 1); //remove the last \n
        System.out.print(to_ret);
        System.out.flush();  
    }
}