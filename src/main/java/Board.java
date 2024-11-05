public class Board {
    private final char[][] grid;
    private final int rows;
    private final int columns;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.grid = new char[rows][columns];
        initialize();
    }

    private void initialize() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = '.';
            }
        }
    }

    public boolean dropDisk(Player player, int column) {
        if (column < 0 || column >= columns) return false;

        for (int i = rows - 1; i >= 0; i--) {
            if (grid[i][column] == '.') {
                grid[i][column] = player.getSymbol();
                return true;
            }
        }
        return false;
    }

    public boolean checkWin(Player player) {
        char symbol = player.getSymbol();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (checkDirection(row, col, 1, 0, symbol) || // vízszintesen
                        checkDirection(row, col, 0, 1, symbol) || // függőlegesen
                        checkDirection(row, col, 1, 1, symbol) || // átlósan
                        checkDirection(row, col, 1, -1, symbol))  // fordított átlósan
                {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDirection(int row, int col, int rowDir, int colDir, char symbol) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            int r = row + i * rowDir;
            int c = col + i * colDir;
            if (r < 0 || r >= rows || c < 0 || c >= columns || grid[r][c] != symbol) {
                return false;
            }
            count++;
        }
        return count == 4;
    }

    public void display() {
        for (char[] row : grid) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int getColumns() {
        return columns;
    }
}
