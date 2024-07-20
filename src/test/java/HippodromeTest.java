import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

class HippodromeTest {
    @Test
    void shouldThrowExceptionAndMessageIfNullInConstructor(){
        Throwable throwable=
                assertThrows(IllegalArgumentException.class,()->new Hippodrome(null));
        assertEquals("Horses cannot be null.",throwable.getMessage());
    }
    @Test
    public void shouldThrowExceptionAndMessageIfBlankInConstructor(){
        Throwable throwable=
                assertThrows(IllegalArgumentException.class,()->new Hippodrome(new ArrayList<>()));
        assertEquals("Horses cannot be empty.",throwable.getMessage());
    }

    @Test
    void getHorsesTest() {
        List<Horse> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
        list.add(new Horse(String.valueOf(i),Double.valueOf(i)));
        }
        Hippodrome hippodrome = new Hippodrome(list);
        assertEquals(list,hippodrome.getHorses());
    }

    @Test
    void moveTest() {
        Horse horseMock = Mockito.mock(Horse.class);
        List<Horse> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(horseMock);
        }
        Hippodrome hippodrome = new Hippodrome(list);
        hippodrome.move();
        Mockito.verify(horseMock,times(50)).move();
    }

    @Test
    void getWinnerTest() {
        List horses = new ArrayList<>();
        horses.add(new Horse("1",1,1));
        horses.add(new Horse("2",1,2));
        Hippodrome hippodrome = new Hippodrome(horses);
        int expected = 2;
        assertEquals(expected,hippodrome.getWinner().getDistance());
    }
}