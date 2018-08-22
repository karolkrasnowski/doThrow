package io.github.karolkrasnowski.dothrow;

import org.testng.annotations.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ThrowerTest {

    @Test
    void shouldThrowIOException() {
        // when
        Throwable thrown = catchThrowable(() -> ThrowableObject.ofType(IOException.class).doThrow());

        // then
        assertThat(thrown).isInstanceOf(IOException.class);
    }

    @Test
    void shouldThrowRuntimeExceptionWithGivenMessage() {
        // when
        Throwable thrown = catchThrowable(() ->
                ThrowableObject.ofType(RuntimeException.class).withMessage("boom!").doThrow());

        // then
        assertThat(thrown).isInstanceOf(RuntimeException.class).hasMessage("boom!");
    }

    @Test
    void shouldThrowExceptionWithGivenCause() {
        // given
        Throwable cause = new Throwable("boom!");

        // when
        Throwable thrown = catchThrowable(() ->
                ThrowableObject.ofType(Exception.class).withCause(cause).doThrow());

        // then
        assertThat(thrown).isInstanceOf(Exception.class).hasCause(cause);
    }

    @Test
    void shouldThrowClassNotFoundExceptionWithGivenMessageAndCause() {
        // given
        Throwable cause = new Throwable("boom!");

        // when
        Throwable thrown = catchThrowable(() ->
                ThrowableObject.ofType(ClassNotFoundException.class).withMessage("ouch!").and().withCause(cause).doThrow());

        // then
        assertThat(thrown).isInstanceOf(ClassNotFoundException.class).hasMessage("ouch!").hasCause(cause);
    }
}