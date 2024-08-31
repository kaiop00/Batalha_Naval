package br.ufc.quixada.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Board {
    private int shipsLeft;
    private Cell[][] positions;
    private Ship[] ships;

    public Board() {
        this.shipsLeft = 0;
        this.positions = new Cell[10][10];
        this.ships = new Ship[5];
    }

    public Board(int size){
        this.shipsLeft = 0;
        this.positions = new Cell[size][size];
        this.ships = new Ship[5];
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

    public void positionShip(Ship ship, int row, int column){
        if(placeValidation(ship, row, column)){
            for(int i = 0; i < ship.getSize(); i++){
                if (ship.getVertical()){
                    positions[row + i][column].setShip(ship);
                }
                else{
                    positions[row][column + i].setShip(ship);
                }
            }

            Ship[] newArray = Arrays.copyOf(ships, ships.length + 1);
            newArray[newArray.length - 1] = ship;
            setShips(newArray);
        }
        else{
            System.out.println("Invalid Position");
        }
    }

    public void shuffle(){
        for(int i = 0; i < positions.length; i++){
            for(int j = 0; j < positions[i].length; j++){
                positions[i][j].setShip(null);
            }
        }

        Collections.shuffle(Arrays.asList(ships));

        Random random = new Random();

        for(Ship ship : ships){
            boolean placed = false;

            while(!placed){

                int order = positions.length;

                int row = random.nextInt(order);
                int column = random.nextInt(order);

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

        for(int i = 0; i < size; i++){
            if(ship.getVertical()){
                if(positions[row + i][column].getShip() != null){
                    return false;
                }
            }
            else{
                if(positions[row][column + i].getShip() != null ){
                    return false;
                }
            }
        }
        return true;
    }

    public void clear() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clear'");
    }
}
