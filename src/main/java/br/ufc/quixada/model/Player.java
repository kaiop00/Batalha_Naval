package br.ufc.quixada.model;

public class Player {
  private int name;
  private boolean ia;

  public Player(int name, boolean ia){
    this.name = name;
    this.ia = ia;
  }

  public int getName(){
    return name;
  }

  public void setName(int name){
    this.name = name;
  }

  public boolean getIa(){
    return ia;
  }

  public void setIa(boolean ia){
    this.ia = ia;
  }
}
