# doThrow is a fluent API for sneakily throwing exceptions

This tiny utility enables throwing *any* exception without the need to catch or propagate it.
API was designed to be read as plain English.

## Why?
Let's assume we want to throw `FileNotFoundException`.
In vanilla Java we would have to declare it in `throws` clause of the method.
With **doThrow** we can do something like this:
```java
ThrowableObject.ofType(FileNotFoundException.class)
                    .withMessage("Cannot read file!")
                    .doThrow();
```                        
Because of *sneaky throw* mechanism this exception neither needs to be declared in `throws` clause nor
`try-catch` block has to be provided.
Thanks to this we don't have to create unchecked types for well-known checked exceptions, like `UncheckedIOException`.
Also wrapping checked exception with `RuntimeException` becomes unnecessary. We can just rethrow original exception.
```java
try {
    throw new IOException("Something bad happened!");
} catch (IOException e) {
    ThrowableObject.ofType(IOException.class)
            .withMessage("Now IOException does not need to be catched")
            .and()
            .withCause(e)
            .doThrow();
}
```   
## Usage
Just type `ThrowableObject.ofType`, hit `Ctrl` + `Space` and let code completion do the job.
