package br.ufc.quixada.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player;
    Player bot;

    @BeforeEach
    void setup() {
        player = new Player(1, "Tester", false);
        bot = null;
    }

    @Test
    void testGetId() {
        assertEquals(player.getId(), 1);
    }

    @Test
    void testSetId() {
        player.setId(3);
        assertEquals(player.getId(), 3);
    }

    @Test
    void testGetName(){
        assertEquals(player.getName(), "Tester");
    }

    @Test
    void testSetName(){
        player.setName("Tester2");
        assertEquals(player.getName(), "Tester2");
    }

    @Test
    void testGetIa(){
        assertFalse(player.getIa());
    }

    @Test
    void testSetIa(){
        player.setIa(true);
        assertTrue(player.getIa());
    }

    @Test
    void testCompareTwoDifferentPlayers(){
        assertNotEquals(player, bot);

        bot = new Player(2, "Bot", true);

        assertNotEquals(player, bot);

        bot.setId(1);

        assertNotEquals(player, bot);

        bot.setName("Tester");

        assertNotEquals(player, bot);
    }

    @Test
    void testCompareTwoIdenticalPlayers(){
        bot = new Player(1, "Tester", false);
        assertEquals(player, bot);
    }
}