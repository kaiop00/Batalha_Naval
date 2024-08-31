package br.ufc.quixada.model;

import javafx.util.Pair;

import java.util.List;
import java.util.Random;

public class Match {
  private boolean finished;
  private List<Pair<Player, Board>> playerBoards;
  private Player currentPlayer;

  public Match( List<Pair<Player, Board>> playerBoards, Player currentPlayer ) {
      finished = false;
      this.playerBoards = playerBoards;
      this.currentPlayer = currentPlayer;
  }

  public boolean getFinished() {
      return finished;
  }

  public void setFinished(boolean finished) {
      this.finished = finished;
  }

  public List<Pair<Player, Board>> getPlayerBoards() {
      return playerBoards;
  }

  public void setPlayerBoards(List<Pair<Player, Board>> playerBoards) {
      this.playerBoards = playerBoards;
  }

  public Player getCurrentPlayer() {
      return currentPlayer;
  }

  public void setCurrentPlayer(Player currentPlayer) {
      this.currentPlayer = currentPlayer;
  }

  public void makePlay(Board board, int row, int column){
      if(!finished){

          Cell targetCell = board.getPositions()[row][column];

          targetCell.setRevealed(true);

          if(targetCell.getShip() != null) {
              targetCell.getShip().setRemainingPieces(targetCell.getShip().getRemainingPieces() - 1);

              if(targetCell.getShip().getRemainingPieces() == 0) {
                  for (int i = 0; i < 5; i++) {
                      if(targetCell.getShip().equals(board.getShips()[i])){
                          board.setShipsLeft(board.getShipsLeft() - 1);
                      }
                  }

                  if(board.getShipsLeft() == 0){
                      finished = true;
                  }
              }
          }

          board.getPositions()[row][column] = targetCell;

          for(Pair<Player, Board> pair : playerBoards){
              if(!pair.getKey().equals(currentPlayer)){
                  currentPlayer = pair.getKey();
              }
          }
      }
  }

  public void makeRandomPlay(Board board){
      Random random = new Random();
      boolean played = false;

      int order = board.getPositions().length;

      int randomRow = random.nextInt(order);
      int randomColumn = random.nextInt(order);

      while(!played){
          if (!board.getPositions()[randomRow][randomColumn].getRevealed()){
              makePlay(board, randomRow, randomColumn);
              played = true;
          }
          else {
              randomRow = random.nextInt(order);
              randomColumn = random.nextInt(order);
          }
      }
  }
}
