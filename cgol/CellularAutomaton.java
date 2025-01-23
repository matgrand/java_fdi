import java.util.Random;

public class CellularAutomaton {
    private static int rows;
    private static int cols;
    private static Random random = new Random();
    private static boolean[][] grid;

    public static void main(String[] args) {
        // rows = 80;
        // cols = 120; 
        rows = 100;
        cols = 200;

        // Initialize grid with sparse live cells (1)
        grid = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (random.nextDouble() < 0.3) {
                    grid[i][j] = true;
                }
            }
        }

        System.out.println("Press Ctrl+C to stop the simulation");
        while (true) {
            runSimulation();
        }
    }

    private static void runSimulation() {
        balanceCells();
        updateGrid();
        printGrid();
    }

    private static void balanceCells() {
        int black = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j]) black++;
            }
        }
        int white = rows * cols - black;

        int toAdd = (int) Math.abs(0.01 * (black - white));
        if (black > white && toAdd > 0) {
            // Add white cells
            for (int i = 0; i < toAdd; i++) {
                int ri = random.nextInt(rows);
                int rj = random.nextInt(cols);
                grid[ri][rj] = false;
            }
        } else if (white > black && toAdd > 0) {
            // Add black cells
            for (int i = 0; i < toAdd; i++) {
                int ri = random.nextInt(rows);
                int rj = random.nextInt(cols);
                grid[ri][rj] = true;
            }
        }
    }

    private static void updateGrid() {
        boolean[][] newGrid = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
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
                int ni = i + x;
                int nj = j + y;

                if (ni >= 0 && ni < rows && nj >= 0 && nj < cols) {
                    count += grid[ni][nj] ? 1 : 0;
                } else {
                    // Wrap around
                    ni = (ni + rows) % rows;
                    nj = (nj + cols) % cols;
                    count += grid[ni][nj] ? 1 : 0;
                }
            }
        }
        return count;
    }

    private static void printGrid() {
        String to_ret = "";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                to_ret += grid[i][j] ? "██" : "  ";
            }
            to_ret += "\n";
        }
        System.out.print(to_ret);

        System.out.flush();  
        // try { Thread.sleep(20); } catch (InterruptedException e) 
        // { 
        //     System.out.println(e);
        // }
    }
}