package br.ufc.quixada.model;

public class Cell {
  private Ship ship;
  private boolean revealed;

  public Cell(Ship ship, boolean revealed){
    this.ship = ship;
    this.revealed = revealed;
  }

  public Ship getShip(){
    return ship;
  }

  public void setShip(Ship ship){
    this.ship = ship;
  }

  public boolean getRevealed(){
    return revealed;
  }

  public void setRevealed(boolean revealed){
    this.revealed = revealed;
  }
}
