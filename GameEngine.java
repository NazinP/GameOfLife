package ru.sber.npy.gameoflife;

import java.io.IOException;
import java.util.Random;

public class GameEngine {
    private boolean field[][];
    private final int rows;
    private final int cols;
    private Random random = new Random();

    public GameEngine(int rows, int cols, int dencity) {
        this.rows = rows;
        this.cols = cols;
        this.field = new boolean[cols][rows];

        // Заселяем поле живыми клетками в рандомном порядке
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                field[col][row] = random.nextInt(dencity) == 0;
            }
        }
    }

    public void start(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                getCurrentGeneration();
                while (true){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    printUniverse();
                    getNextGeneration();
                }
            }
        }).start();
    }

    // Создаём следующее поколение
    public void getNextGeneration(){
        boolean[][] newField = new boolean[cols][rows];

        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                int neighbors = countNeighbors(col, row);
                boolean hasLife = field[col][row];

                if(!hasLife && neighbors == 3){
                    newField[col][row] = true;
                }else if(hasLife && (neighbors < 2 || neighbors >3)){
                    newField[col][row] = false;
                }else{
                    newField[col][row] = field[col][row];
                }
            }
        }
        field = newField;
    }

    // Подсчёт живых клеток
    private int countNeighbors(int x, int y){
        int count = 0;

        for (int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++){
                int col = (x + i + cols) % cols;
                int row = (y + j + rows) % rows;

                boolean selfChecking = col == x && row == y;
                boolean hasLife = field[col][row];

                if(hasLife && !selfChecking){
                    count++;
                }
            }
        }

        return count;
    }

    // Создаём копию поля текущего поколения
    public boolean[][] getCurrentGeneration(){
        boolean[][] currField = new boolean[cols][rows];

        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                currField[col][row] = field[col][row];
            }
        }
        return currField;
    }

    void printUniverse(){
        for(boolean[] stringArray: field){
            StringBuilder row = new StringBuilder();
            for(boolean cell: stringArray){
                if (cell)
                    row.append("#");
                else
                    row.append(" ");
            }
            System.out.println(row);
        }
    }
}
