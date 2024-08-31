package br.ufc.quixada.model;

import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatchTest {

    Match match;

    List<Pair<Player, Board>> playersBoards;

    Player player1;
    Player player2;

    Board board1;
    Board board2;

    @BeforeEach
    void setUp(){
        player1 = mock(Player.class);
        player2 = mock(Player.class);

        board1 = mock(Board.class);
        board2 = mock(Board.class);

        playersBoards = new ArrayList<>();
        playersBoards.add(new Pair<>(player1, board1));
        playersBoards.add(new Pair<>(player2, board2));

        match = new Match(playersBoards, player1);
    }

    @Test
    void testGetFinished(){
        assertFalse(match.getFinished());
    }

    @Test
    void testSetFinished(){
        match.setFinished(true);
        assertTrue(match.getFinished());
    }

    @Test
    void testGetPlayerBoards(){
        assertEquals(match.getPlayerBoards(), playersBoards);
    }

    @Test
    void testSetPlayerBoards(){
        List<Pair<Player, Board>> newPlayerBoards = new ArrayList<>();
        match.setPlayerBoards(newPlayerBoards);
        assertEquals(match.getPlayerBoards(), newPlayerBoards);
    }

    @Test
    void testGetCurrentPlayer(){
        assertEquals(match.getCurrentPlayer(), player1);
    }

    @Test
    void testSetCurrentPlayer(){
        match.setCurrentPlayer(player2);
        assertEquals(match.getCurrentPlayer(), player2);
    }

    @Test
    void testMakePlayWithGameEnded(){
        match.setFinished(true);
        match.makePlay(board1, 2, 2);
        verifyNoInteractions(board1);
    }

    @Test
    void testMakePlayWhereThereNoShip(){
        Cell targetCell = mock(Cell.class);
        when(targetCell.getShip()).thenReturn(null);

        Cell[][] positions = new Cell[10][10];
        positions[2][2] = targetCell;

        when(board1.getPositions()).thenReturn(positions);

        match.makePlay(board1, 2, 2);

        assertFalse(match.getFinished());
        verify(targetCell).setRevealed(true);
        assertEquals(match.getCurrentPlayer(), player2);
    }

    @Test
    void testMakePlayWithoutSinkTheShip(){
        Cell targetCell = mock(Cell.class);
        Ship ship = mock(Ship.class);

        when(targetCell.getShip()).thenReturn(ship);
        when(ship.getRemainingPieces()).thenReturn(5);

        Cell[][] positions = new Cell[10][10];
        positions[2][2] = targetCell;

        when(board1.getPositions()).thenReturn(positions);

        match.makePlay(board1, 2, 2);

        assertFalse(match.getFinished());
        verify(targetCell).setRevealed(true);
        verify(ship, times(2)).getRemainingPieces();
        assertEquals(match.getCurrentPlayer(), player2);
    }

    @Test
    void testMakePlayAndSinkShipWithoutEndTheGame(){
        Cell targetCell = mock(Cell.class);
        Ship ship = mock(Ship.class);

        when(targetCell.getShip()).thenReturn(ship);
        when(ship.getRemainingPieces()).thenReturn(1);

        Cell[][] positions = new Cell[10][10];
        positions[2][2] = targetCell;
        Ship[] ships = new Ship[5];
        ships[0] = ship;

        when(board1.getPositions()).thenReturn(positions);
        when(board1.getShips()).thenReturn(ships);
        when(board1.getShipsLeft()).thenReturn(1);

        final int[] remainingPieces = {1};
        when(ship.getRemainingPieces()).thenAnswer(invoke -> remainingPieces[0]);

        doAnswer(invoke -> {
            remainingPieces[0] = invoke.getArgument(0);
            return null;
        }).when(ship).setRemainingPieces(anyInt());

        match.makePlay(board1, 2, 2);

        assertFalse(match.getFinished());
        verify(targetCell).setRevealed(true);
        assertEquals(0, ship.getRemainingPieces());
        assertEquals(match.getCurrentPlayer(), player2);
    }

    @Test
    void testMakePlayAndEndTheGame(){
        Cell targetCell = mock(Cell.class);
        Ship ship = mock(Ship.class);

        when(targetCell.getShip()).thenReturn(ship);
        when(ship.getRemainingPieces()).thenReturn(1);

        Cell[][] positions = new Cell[10][10];
        positions[2][2] = targetCell;
        Ship[] ships = new Ship[5];
        ships[0] = ship;

        when(board1.getPositions()).thenReturn(positions);
        when(board1.getShips()).thenReturn(ships);

        final int[] remainingPieces = {1};
        when(ship.getRemainingPieces()).thenAnswer(invoke -> remainingPieces[0]);

        doAnswer(invoke -> {
            remainingPieces[0] = invoke.getArgument(0);
            return null;
        }).when(ship).setRemainingPieces(anyInt());

        match.makePlay(board1, 2, 2);

        assertTrue(match.getFinished());
        verify(targetCell).setRevealed(true);
        assertEquals(0, ship.getRemainingPieces());
        assertEquals(0, board1.getShipsLeft());
    }

    @Test
    void testMakeRandomPlay(){
        Cell cell = mock(Cell.class);
        when(cell.getShip()).thenReturn(null);

        Cell[][] positions = new Cell[10][10];

        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++) {
                positions[i][j] = cell;
                when(positions[i][j].getRevealed()).thenReturn(false);
            }
        }

        when(board1.getPositions()).thenReturn(positions);

        match.makeRandomPlay(board1);

        verify(board1.getPositions()[1][1]).getRevealed();
    }
}