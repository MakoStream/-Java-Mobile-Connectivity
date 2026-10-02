package mobileoperator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JUnitSetupTest {

    @Test
    void shouldPassSimpleTest() {
        int result = 2 + 2;

        assertEquals(4, result);
    }
}