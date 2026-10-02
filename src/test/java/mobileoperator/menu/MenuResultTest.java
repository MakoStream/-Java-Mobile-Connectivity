package mobileoperator.menu;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MenuResultTest {

    @Test
    void stayShouldCreateStayResult() {
        MenuResult result = MenuResult.stay();

        assertEquals(
                MenuResult.Type.STAY,
                result.getType()
        );

        assertNull(result.getNextMenu());
    }

    @Test
    void backShouldCreateBackResult() {
        MenuResult result = MenuResult.back();

        assertEquals(
                MenuResult.Type.BACK,
                result.getType()
        );

        assertNull(result.getNextMenu());
    }

    @Test
    void exitShouldCreateExitResult() {
        MenuResult result = MenuResult.exit();

        assertEquals(
                MenuResult.Type.EXIT,
                result.getType()
        );

        assertNull(result.getNextMenu());
    }
}