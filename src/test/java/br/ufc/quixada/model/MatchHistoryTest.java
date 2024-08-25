package br.ufc.quixada.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class MatchHistoryTest {

    MatchHistory matchHistory;
    List<Player> players;
    LocalDateTime date;

    Player player1;
    Player player2;

    @BeforeEach
    void setUp(){
        date = LocalDateTime.of(2024, Month.AUGUST, 25, 15, 30);

        player1 = mock(Player.class);
        player2 = mock(Player.class);

        players = List.of(player1, player2);

        matchHistory = new MatchHistory(1, date, players, player1);
    }

    @Test
    void testGetId(){
        assertEquals(matchHistory.getId(), 1);
    }

    @Test
    void testSetId(){
        matchHistory.setId(2);
        assertEquals(matchHistory.getId(), 2);
    }

    @Test
    void testGetDate(){
        assertEquals(matchHistory.getDateTime(), date);
    }

    @Test
    void testSetDate(){
        LocalDateTime newDate = LocalDateTime.of(2000, Month.JANUARY, 20, 20, 0);
        matchHistory.setLocalDateTime(newDate);
        assertEquals(matchHistory.getDateTime(), newDate);
    }

    @Test
    void testGetPlayers(){
        assertEquals(matchHistory.getPlayers(), players);
    }

    @Test
    void testSetPlayers(){
        Player player3 = mock(Player.class);
        Player player4 = mock(Player.class);
        List<Player> newPlayers = List.of(player3, player4);
        matchHistory.setPlayers(newPlayers);
        assertEquals(matchHistory.getPlayers(), newPlayers);
    }

    @Test
    void testGetWinner(){
        assertEquals(matchHistory.getWinner(), player1);
    }

    @Test
    void testSetWinnerWithInvalidPlayer(){
        Player player3 = mock(Player.class);
        assertThrows(IllegalArgumentException.class, () -> matchHistory.setWinner(player3));
    }

    @Test
    void testSetWinnerWithValidPlayer(){
        matchHistory.setWinner(player2);
        assertEquals(matchHistory.getWinner(), player2);
    }

    @Test
    void compareTwoDifferentMatchHistories(){
        LocalDateTime newDate = LocalDateTime.now();
        MatchHistory newMatchHistory = new MatchHistory(2, newDate, players, player2);
        assertNotEquals(matchHistory, newMatchHistory);
    }

    @Test
    void compareTwoIdenticalMatchHistories() {
        MatchHistory newMatchHistory = new MatchHistory(1, date, players, player1);
        assertEquals(matchHistory, newMatchHistory);
    }

}