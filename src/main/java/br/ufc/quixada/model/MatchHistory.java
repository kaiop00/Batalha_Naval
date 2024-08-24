package br.ufc.quixada.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class MatchHistory {
  private Integer id;
  private LocalDateTime dateTime;
  private List<Player> players;
  private Player winner;

  public MatchHistory(Integer id, LocalDateTime date, List<Player> players, Player winner) {
    this.id = id;
    this.dateTime = date;
    this.players = players;
    this.winner = winner;
  }

  public MatchHistory(LocalDateTime date, List<Player> players, Player winner) {
    this.dateTime = date;
    this.players = players;
    this.winner = winner;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setLocalDateTime(LocalDateTime date) {
    this.dateTime = date;
  }

  public List<Player> getPlayers() {
    return players;
  }

  public void setPlayers(List<Player> players) {
    this.players = players;
  }

  public Player getWinner() {
    return winner;
  }

  public void setWinner(Player winner) {
    if (players.contains(winner)) {
      this.winner = winner;
    } else {
      throw new IllegalArgumentException("Winner must be one of the players");
    }
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    MatchHistory other = (MatchHistory) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (dateTime == null) {
      if (other.dateTime != null)
        return false;
    } else if (!dateTime.withNano(0).equals(other.dateTime.withNano(0)))
      return false;
    if (players == null) {
      if (other.players != null)
        return false;
    } else if (!players.equals(other.players))
      return false;
    if (winner == null) {
      if (other.winner != null)
        return false;
    } else if (!winner.equals(other.winner))
      return false;
    return true;
  }

}
