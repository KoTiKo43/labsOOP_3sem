package exceptions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class InterpolationExceptionTest {
    @Test
    public void testInterpolationException() {
        InterpolationException interpolationException = new InterpolationException();
        InterpolationException interpolationExceptionMessage = new InterpolationException("Interpolation exception is thrown");

        assertThrows(InterpolationException.class, ()->{throw interpolationException;});
        assertEquals("Interpolation exception is thrown", interpolationExceptionMessage.getMessage());
    }
}