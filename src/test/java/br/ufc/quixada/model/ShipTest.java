package br.ufc.quixada.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {

    Ship ship;
    Ship otherShip;

    @BeforeEach
    void setUp(){
        ship = new Ship(5, true);
        otherShip = new Ship(3, false);
    }

    @Test
    void testGetSize(){
        assertEquals(ship.getSize(), 5);
    }

    @Test
    void testSetSize(){
        ship.setSize(3);
        assertEquals(ship.getSize(), 3);
    }

    @Test
    void testGetVertical(){
        assertTrue(ship.getVertical());
    }

    @Test
    void testSetVertical(){
        ship.setVertical(false);
        assertFalse(ship.getVertical());
    }

    @Test
    void testGetRemainingPieces(){
        assertEquals(ship.getRemainingPieces(), 5);
    }

    @Test
    void testSetRemainingPieces(){
        ship.setRemainingPieces(4);
        assertEquals(ship.getRemainingPieces(), 4);
    }

    @Test
    void testRotate(){
        ship.rotate();
        assertFalse(ship.getVertical());
    }

    @Test
    void testEqualsShouldReturnFaLse(){
        assertNotEquals(ship, otherShip);
    }

    @Test
    void testEqualsShouldReturnTrue(){
        otherShip.setSize(5);
        assertEquals(ship, otherShip);
    }
}