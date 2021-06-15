package ru.sber.npy.gameoflife;

public class Main {
    public static void main(String[] args){
        GameEngine gameEngane = new GameEngine(100, 50, 3);
        gameEngane.start();
    }
}
