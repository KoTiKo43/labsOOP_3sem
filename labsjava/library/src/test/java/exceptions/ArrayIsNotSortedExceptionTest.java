package exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayIsNotSortedExceptionTest {
    @Test
    public void testInconsistentFunctionsException() {
        ArrayIsNotSortedException arrayIsNotSortedException = new ArrayIsNotSortedException();
        ArrayIsNotSortedException arrayIsNotSortedExceptionMessage = new ArrayIsNotSortedException("ArrayIsNotSortedException is thrown");

        assertThrows(ArrayIsNotSortedException.class, ()->{throw arrayIsNotSortedException;});
        assertEquals("ArrayIsNotSortedException is thrown", arrayIsNotSortedExceptionMessage.getMessage());
    }
}