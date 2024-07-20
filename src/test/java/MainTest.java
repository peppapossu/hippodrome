import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
@Timeout(22)
class MainTest {

    @Disabled
    @Test
    void mainTest() {
        assertTimeout(Duration.ofSeconds(22),()->Main.main(null));
    }
}