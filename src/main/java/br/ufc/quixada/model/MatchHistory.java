package br.ufc.quixada.model;

import java.util.Date;

public class MatchHistory {
  private Date date;
  private Player[] players;
  private Player winner;
  
  public MatchHistory(Date date, Player[] players, Player winner){
    this.date = date;
    this.players = players;
    this.winner = winner;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Player[] getPlayers() {
    return players;
  }

  public void setPlayers(Player[] players) {
    this.players = players;
  }

  public Player getWinner() {
    return winner;
  }

  public void setWinner(Player winner) {
    this.winner = winner;
  }
}
