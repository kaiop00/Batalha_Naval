package br.ufc.quixada.model;

import java.util.Arrays;
import java.util.Random;

public class Board {
    private int shipsLeft;
    private Cell[][] positions;
    private Ship[] ships;

    public Board() {
        this.shipsLeft = 5;
        this.positions = new Cell[10][10];
        this.ships = new Ship[0];

        for(int i = 0; i < 10; i ++){
            for (int j = 0; j < 10; j++) {
                positions[i][j] = new Cell(null, false);
            }
        }
    }

    public Board(int size){
        this.shipsLeft = 5;
        this.positions = new Cell[size][size];
        this.ships = new Ship[0];

        for(int i = 0; i < size; i ++){
            for (int j = 0; j < size; j++) {
                positions[i][j] = new Cell(null, false);
            }
        }
    }

    public int getShipsLeft() {
        return shipsLeft;
    }

    public void setShipsLeft(int shipsLeft) {
        this.shipsLeft = shipsLeft;
    }

    public Cell[][] getPositions() {
        return positions;
    }

    public void setPositions(Cell[][] positions) {
        this.positions = positions;
    }

    public Ship[] getShips() {
        return ships;
    }

    public void setShips(Ship[] ships) {
        this.ships = ships;
    }

    public void positionShip(Ship ship, int row, int column) throws Exception {
        if(!placeValidation(ship, row, column)){
            throw new Exception("Invalid Position");
        }
        for(int i = 0; i < ship.getSize(); i++){
            if (ship.getVertical()){
                positions[row - 1 + i][column - 1].setShip(ship);
            }
                else{
                positions[row - 1][column - 1 + i].setShip(ship);
            }
        }

        Ship[] newArray = Arrays.copyOf(ships, ships.length + 1);
        newArray[newArray.length - 1] = ship;
        setShips(newArray);
    }

    public void shuffle() throws Exception {
        for (Cell[] position : positions) {
            for (Cell cell : position) {
                cell.setShip(null);
            }
        }

        Ship[] newShipsOrder = ships;
        ships = new Ship[0];

        Random random = new Random();

        for(Ship ship : newShipsOrder){
            boolean placed = false;

            while(!placed){

                int order = positions.length;

                int row = random.nextInt(order) + 1;
                int column = random.nextInt(order) + 1;

                if(placeValidation(ship, row, column)){
                    positionShip(ship, row, column);
                    placed = true;
                }
            }
        }
    }

    private boolean placeValidation(Ship ship, int row, int column){
        int size = positions.length;

        if(ship.getVertical()){
            if(row + ship.getSize() > size){
                return false;
            }
        }
        else{
            if(column + ship.getSize() > size){
                return false;
            }
        }

        for(int i = 0; i < ship.getSize(); i++){
            if(ship.getVertical()){
                if(positions[row - 1 + i][column - 1].getShip() != null){
                    return false;
                }
            }
            else{
                if(positions[row - 1][column - 1 + i].getShip() != null ){
                    return false;
                }
            }
        }
        return true;
    }
}
