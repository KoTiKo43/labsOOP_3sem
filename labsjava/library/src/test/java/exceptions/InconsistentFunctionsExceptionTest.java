package exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InconsistentFunctionsExceptionTest {
    @Test
    public void testInconsistentFunctionsException() {
        InconsistentFunctionsException inconsistentFunctionsException = new InconsistentFunctionsException();
        InconsistentFunctionsException inconsistentFunctionsExceptionMessage = new InconsistentFunctionsException("InconsistentFunctionsException is thrown");

        assertThrows(InconsistentFunctionsException.class, ()->{throw inconsistentFunctionsException;});
        assertEquals("InconsistentFunctionsException is thrown", inconsistentFunctionsExceptionMessage.getMessage());
    }
}