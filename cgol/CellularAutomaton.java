import java.util.Random;

public class CellularAutomaton {
    private static final int COLS = 87; // 158 // 119 // 87
    private static final int ROWS = 42; // 70  // 59  // 42
    private static final double FPS = 30; // Frames per second
    private static Random random = new Random();
    private static boolean[][] grid = new boolean[ROWS][COLS];

    public static void main(String[] args) {
        // Initialize grid with sparse live cells (1)
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (random.nextDouble() < 0.3) {
                    grid[i][j] = true;
                }
            }
        }

        // run simulation
        while (true) {
            runSimulation();
        }
    }

    private static void runSimulation() {
        long startTime = System.currentTimeMillis(); // get start time

        // Simulation step
        // balanceCells();
        updateGrid();
        printGrid();

        // sleep for the remaining time to maintain FPS
        long sleepTime = (long) (1000 / FPS) - ( System.currentTimeMillis() - startTime);
        if (sleepTime > 0) {
            try { Thread.sleep(sleepTime); } 
            catch (InterruptedException e) { System.out.println(e);}
        }
    }

    private static void balanceCells() {
        int black = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (grid[i][j]) black++;
            }
        }
        int white = ROWS * COLS - black;

        int toAdd = (int) Math.abs(0.01 * (black - white));
        if (black > white && toAdd > 0) {
            // Add white cells
            for (int i = 0; i < toAdd; i++) {
                int ri = random.nextInt(ROWS);
                int rj = random.nextInt(COLS);
                grid[ri][rj] = false;
            }
        } else if (white > black && toAdd > 0) {
            // Add black cells
            for (int i = 0; i < toAdd; i++) {
                int ri = random.nextInt(ROWS);
                int rj = random.nextInt(COLS);
                grid[ri][rj] = true;
            }
        }
    }

    private static void updateGrid() {
        boolean[][] newGrid = new boolean[ROWS][COLS];

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                int neighbors = countNeighbors(i, j);

                if (grid[i][j]) {
                    newGrid[i][j] = (neighbors == 2 || neighbors == 3);
                } else {
                    newGrid[i][j] = (neighbors == 3);
                }
            }
        }

        grid = newGrid;
    }

    private static int countNeighbors(int i, int j) {
        int count = 0;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0) continue;
                int ni = (i + x + ROWS) % ROWS; // wrap around
                int nj = (j + y + COLS) % COLS; 
                count += grid[ni][nj] ? 1 : 0; // count live neighbors
            }
        }
        return count;
    }

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
        // try { Thread.sleep(20); } catch (InterruptedException e) 
        // { System.out.println(e);}
    }
}