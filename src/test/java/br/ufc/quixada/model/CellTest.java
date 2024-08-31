package br.ufc.quixada.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CellTest {

    Cell cell;
    Ship ship;

    @BeforeEach
    void setUp(){
        ship = mock(Ship.class);
        when(ship.getSize()).thenReturn(5);

        cell = new Cell(ship, false);
    }

    @Test
    void testGetShip(){
        assertEquals(cell.getShip(), ship);
    }

    @Test
    void testSetShip(){
        Ship otherShip = mock(Ship.class);
        when(otherShip.getSize()).thenReturn(4);
        cell.setShip(otherShip);
        assertEquals(cell.getShip(), otherShip);
    }

    @Test
    void testGetRevealed(){
       assertFalse(cell.getRevealed());
    }

    @Test
    void testSetRevealed(){
        cell.setRevealed(true);
        assertTrue(cell.getRevealed());
    }
}