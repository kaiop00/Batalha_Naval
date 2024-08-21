package br.ufc.quixada.model;

public class Ship {
  private int size;
  private boolean vertical;
  private int remainingPieces;

  public Ship(int size, boolean vertical){
    this.size = size;
    this.vertical = vertical;
    this.remainingPieces = size;
  }

  public int getSize(){
    return size;
  }

  public void setSize(int size){
    this.size = size;
  }

  public boolean getVertical(){
    return vertical;
  }

  public void setVertical(boolean vertical){
    this.vertical = vertical;
  }

  public int getRemainingPieces(){
    return remainingPieces;
  }

  public void setRemainingPieces(int remainingPieces){
    this.remainingPieces = remainingPieces;
  }

  public void rotate(){
    this.vertical = !vertical;
  }

  @Override
  public boolean equals(Object obj) {
    if(getClass() != obj.getClass()){
      return false;
    }

    Ship ship = (Ship) obj;

    return this.size == ship.size;
  }
}
