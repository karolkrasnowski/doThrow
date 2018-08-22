/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 Karol Krasnowski
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.github.karolkrasnowski.dothrow;

final class Thrower<T extends Throwable> {

    private Class<T> clazz;
    private String message;
    private Throwable cause;
    private ThrowerType throwerType;

    Thrower(Class<T> clazz) {
        this.clazz = clazz;
        this.throwerType = ThrowerType.WITH_NO_CAUSE_AND_NO_MESSAGE;
    }

    Thrower(Class<T> clazz, String message) {
        this.clazz = clazz;
        this.message = message;
        this.throwerType = ThrowerType.ONLY_WITH_MESSAGE;
    }

    Thrower(Class<T> clazz, Throwable cause) {
        this.clazz = clazz;
        this.cause = cause;
        this.throwerType = ThrowerType.ONLY_WITH_CAUSE;
    }

    Thrower(Class<T> clazz, String message, Throwable cause) {
        this.clazz = clazz;
        this.message = message;
        this.cause = cause;
        this.throwerType = ThrowerType.WITH_MESSAGE_AND_CAUSE;
    }

    void doThrow() {
        try {
            throwDependingOnThrowerType();
        } catch (Exception e) {
            sneakyThrow(e);
        }
    }

    private void throwDependingOnThrowerType() throws Exception {
        if (throwerType == ThrowerType.WITH_MESSAGE_AND_CAUSE) {
            T t = clazz.getConstructor(String.class, Throwable.class).newInstance(message, cause);
            sneakyThrow(t);
        } else if (throwerType == ThrowerType.ONLY_WITH_MESSAGE) {
            T t = clazz.getConstructor(String.class).newInstance(message);
            sneakyThrow(t);
        } else if (throwerType == ThrowerType.ONLY_WITH_CAUSE) {
            T t = clazz.getConstructor(Throwable.class).newInstance(cause);
            sneakyThrow(t);
        } else if (throwerType == ThrowerType.WITH_NO_CAUSE_AND_NO_MESSAGE) {
            T t = clazz.getConstructor().newInstance();
            sneakyThrow(t);
        }
    }

    private <E extends Throwable> void sneakyThrow(Throwable e) throws E {
        throw (E) e;
    }

    enum ThrowerType {
        ONLY_WITH_MESSAGE, ONLY_WITH_CAUSE, WITH_MESSAGE_AND_CAUSE, WITH_NO_CAUSE_AND_NO_MESSAGE
    }
}