package br.ufc.quixada.model;

public class Player {
  private String name;
  private boolean ia;

  public Player(String name, boolean ia){
    this.name = name;
    this.ia = ia;
  }

  public String getName(){
    return name;
  }

  public void setName(String name){
    this.name = name;
  }

  public boolean getIa(){
    return ia;
  }

  public void setIa(boolean ia){
    this.ia = ia;
  }
}
