package exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DifferentLengthOfArraysExceptionTest {
    @Test
    public void testInconsistentFunctionsException() {
        DifferentLengthOfArraysException differentLengthOfArraysException = new DifferentLengthOfArraysException();
        DifferentLengthOfArraysException differentLengthOfArraysExceptionMessage = new DifferentLengthOfArraysException("DifferentLengthOfArraysException is thrown");

        assertThrows(DifferentLengthOfArraysException.class, ()->{throw differentLengthOfArraysException;});
        assertEquals("DifferentLengthOfArraysException is thrown", differentLengthOfArraysExceptionMessage.getMessage());
    }
}