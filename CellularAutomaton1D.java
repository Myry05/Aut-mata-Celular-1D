import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CellularAutomaton1D extends JFrame {
    private final int CELL_SIZE = 10;
    private final int GRID_WIDTH = 80;
    private final int GRID_HEIGHT = 40;
    private int[] cells;
    private int[] newCells;
    private JPanel gridPanel;
    private Timer timer;

    public CellularAutomaton1D() {
        setTitle("Autómata Celular 1D");
        setSize(GRID_WIDTH * CELL_SIZE, GRID_HEIGHT * CELL_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        cells = new int[GRID_WIDTH];
        newCells = new int[GRID_WIDTH];
        cells[GRID_WIDTH / 2] = 1; // Inicializa la celda central
        
        gridPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int y = 0; y < GRID_HEIGHT; y++) {
                    for (int x = 0; x < GRID_WIDTH; x++) {
                        if (cells[x] == 1) {
                            g.setColor(Color.BLACK);
                        } else {
                            g.setColor(Color.WHITE);
                        }
                        g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    }
                    nextGeneration();
                }
            }
        };
        
        gridPanel.setPreferredSize(new Dimension(GRID_WIDTH * CELL_SIZE, GRID_HEIGHT * CELL_SIZE));
        add(gridPanel);
        
        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gridPanel.repaint();
            }
        });
        timer.start();
    }

    private void nextGeneration() {
        for (int i = 0; i < GRID_WIDTH; i++) {
            int left = (i == 0) ? 0 : cells[i - 1];
            int center = cells[i];
            int right = (i == GRID_WIDTH - 1) ? 0 : cells[i + 1];
            newCells[i] = rule30(left, center, right);
        }
        System.arraycopy(newCells, 0, cells, 0, GRID_WIDTH);
    }

    private int rule30(int left, int center, int right) {
        // Implementa la regla 30
        if (left == 1 && center == 1 && right == 1) return 0;
        if (left == 1 && center == 1 && right == 0) return 0;
        if (left == 1 && center == 0 && right == 1) return 0;
        if (left == 1 && center == 0 && right == 0) return 1;
        if (left == 0 && center == 1 && right == 1) return 1;
        if (left == 0 && center == 1 && right == 0) return 1;
        if (left == 0 && center == 0 && right == 1) return 1;
        if (left == 0 && center == 0 && right == 0) return 0;
        return 0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CellularAutomaton1D().setVisible(true);
            }
        });
    }
}
