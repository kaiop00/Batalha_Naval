package br.ufc.quixada.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BoardTest {

    Board board;

    Cell[][] positions;

    Ship[] ships;

    @BeforeEach
    void setUp(){
        board = new Board();
        positions = board.getPositions();
        ships = board.getShips();
    }

    @Test
    void testGetShipsLeft(){
        assertEquals(5, board.getShipsLeft());
    }

    @Test
    void testSetShipsLeft(){
        board.setShipsLeft(4);
        assertEquals(4, board.getShipsLeft());
    }

    @Test
    void testGetPositions(){
        assertEquals(positions, board.getPositions());
    }

    @Test
    void testSetPosition(){
        Cell[][] newPositions = new Cell[5][5];
        board.setPositions(newPositions);
        assertEquals(newPositions, board.getPositions());
    }

    @Test
    void testGetShips(){
        assertEquals(ships, board.getShips());
    }

    @Test
    void testSetShips(){
        Ship[] newShips = new Ship[10];
        board.setShips(newShips);
        assertEquals(newShips, board.getShips());
    }

    @Test
    void testPositionShipOutOfBoard() throws IllegalArgumentException{
        Ship ship = mock(Ship.class);

        assertThrows(IllegalArgumentException.class, () -> board.positionShip(ship, 11, 11));
    }

    @Test
    void testPositionShipInValidPosition() throws IllegalArgumentException{
        Ship ship = mock(Ship.class);
        when(ship.getSize()).thenReturn(3);
        when(ship.getVertical()).thenReturn(true);

        board.positionShip(ship, 1, 1);
        assertEquals(board.getShips()[0], ship);
    }

    @Test
    void testPositionShipInOtherShip() throws IllegalArgumentException {
        Ship ship = mock(Ship.class);
        when(ship.getSize()).thenReturn(3);
        when(ship.getVertical()).thenReturn(true);

        board.positionShip(ship, 1, 1);
        assertEquals(board.getShips()[0], ship);

        Ship otherShip = mock(Ship.class);
        when(otherShip.getSize()).thenReturn(2);
        when(otherShip.getVertical()).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> board.positionShip(otherShip, 1, 1));
    }

    @Test
    void testsPositionAllShipsInValidPositions() throws IllegalArgumentException {
        Ship ship1 = mock(Ship.class);
        Ship ship2 = mock(Ship.class);
        Ship ship3 = mock(Ship.class);
        Ship ship4 = mock(Ship.class);
        Ship ship5 = mock(Ship.class);

        when(ship1.getSize()).thenReturn(1);
        when(ship2.getSize()).thenReturn(2);
        when(ship3.getSize()).thenReturn(3);
        when(ship4.getSize()).thenReturn(4);
        when(ship5.getSize()).thenReturn(5);

        when(ship1.getVertical()).thenReturn(true);
        when(ship2.getVertical()).thenReturn(false);
        when(ship3.getVertical()).thenReturn(true);
        when(ship4.getVertical()).thenReturn(true);
        when(ship5.getVertical()).thenReturn(false);

        board.positionShip(ship1, 0, 0);
        board.positionShip(ship2, 9, 0);
        board.positionShip(ship3, 2, 5);
        board.positionShip(ship4, 0, 9);
        board.positionShip(ship5, 8, 0);

        assertEquals(5, board.getShips().length);
    }

    @Test
    void testShuffle() throws IllegalArgumentException {
        Ship ship1 = mock(Ship.class);
        Ship ship2 = mock(Ship.class);
        Ship ship3 = mock(Ship.class);
        Ship ship4 = mock(Ship.class);
        Ship ship5 = mock(Ship.class);

        when(ship1.getSize()).thenReturn(1);
        when(ship2.getSize()).thenReturn(2);
        when(ship3.getSize()).thenReturn(3);
        when(ship4.getSize()).thenReturn(4);
        when(ship5.getSize()).thenReturn(5);

        when(ship1.getVertical()).thenReturn(true);
        when(ship2.getVertical()).thenReturn(false);
        when(ship3.getVertical()).thenReturn(true);
        when(ship4.getVertical()).thenReturn(true);
        when(ship5.getVertical()).thenReturn(false);

        board.positionShip(ship1, 0, 0);
        board.positionShip(ship2, 9, 0);
        board.positionShip(ship3, 2, 5);
        board.positionShip(ship4, 0, 9);
        board.positionShip(ship5, 8, 0);

        assertEquals(5, board.getShips().length);

        board.shuffle();

        assertEquals(5, board.getShips().length);

    }
}