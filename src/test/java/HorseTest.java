import com.sun.source.tree.ModuleTree;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HorseTest {
    Horse horse = Mockito.mock(Horse.class);


    private static String[] nullEmptyBlankSource() {
        return new String[] { null, "", " " };
    }

    @ParameterizedTest
    //@MethodSource("nullEmptyBlankSource") // option 1
    //@CsvSource({",", "''", "' '"})        // option 2
    @NullAndEmptySource                     // option 3
    @ValueSource(strings = " ")
    public void shouldReturnRightExceptionIfFirstNullOrBlankInConstructor(String name){
        assertThrows(IllegalArgumentException.class,()->new Horse(name,1,1));
    }
    @Test
    public void shouldReturnRightMessageIfFirstNullInConstructor(){
        Throwable throwable =
                assertThrows(IllegalArgumentException.class,()->new Horse(null,1,1));
        assertEquals("Name cannot be null.",throwable.getMessage());
    }
    @ParameterizedTest
    @CsvSource({"''", "' '"})
    public void shouldReturnRightMessageIfFirstBlankInConstructor(String name){
        Throwable throwable =
                assertThrows(IllegalArgumentException.class,()->new Horse(name,1,1));
        assertEquals("Name cannot be blank.",throwable.getMessage());
    }
    @Test
    public void shouldReturnIllegalArgumentExceptionIfSecondIsNegative(){
        assertThrows(IllegalArgumentException.class,()->new Horse("name",-1,1));
    }
    @Test
    public void shouldReturnRightMessageIfSecondIsNegative(){
        Throwable throwable =
                assertThrows(IllegalArgumentException.class,()->new Horse("name",-1,1));
        assertEquals("Speed cannot be negative.",throwable.getMessage());
    }
    @Test
    public void shouldReturnIllegalArgumentExceptionIfThirdIsNegative(){
        assertThrows(IllegalArgumentException.class,()->new Horse("name",1,-1));
    }
    @Test
    public void shouldReturnRightMessageIfThirdIsNegative(){
        Throwable throwable =
                assertThrows(IllegalArgumentException.class,()->new Horse("name",1,-1));
        assertEquals("Distance cannot be negative.",throwable.getMessage());
    }

    @Test
    void getNameTest() {
        String name = "name";
        Horse horse = new Horse(name,1);
        assertEquals(name,horse.getName());
    }

    @Test
    void getSpeedTest() {
        int speed = 1;
        Horse horse = new Horse("name",speed);
        assertEquals(speed,horse.getSpeed());
    }

    @Test
    void getDistanceTest() {
        int distance = 1;
        Horse horse = new Horse("name",1,1);
        Horse horseNoDistance = new Horse("name",1);
        assertEquals(distance,horse.getDistance());
        assertEquals(0,horseNoDistance.getDistance());

    }

    @Test
    void move() {
        try(MockedStatic<Horse> mockStatic = Mockito.mockStatic(Horse.class)){
            Horse horse = new Horse("name",1,1);
            horse.move();
            mockStatic.verify(()->Horse.getRandomDouble(0.2,0.9),times(1));
        }

    }

    @ParameterizedTest
    @CsvSource({"1,4,0.5,3"})
    void moveTestDistance(double dist, double speed,double rand, double res) {
        try (MockedStatic mockedStatic = mockStatic(Horse.class)){
            mockedStatic.when(() -> Horse.getRandomDouble(0.2,0.9)).thenReturn(rand);
            Horse horse = new Horse("name", speed,dist);
            horse.move();
            assertEquals(res,horse.getDistance());
        }
    }
}