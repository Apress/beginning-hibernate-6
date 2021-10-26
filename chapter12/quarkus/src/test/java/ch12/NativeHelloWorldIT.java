package ch12;

import io.quarkus.test.junit.NativeImageTest;

@NativeImageTest
public class NativeHelloWorldIT extends HelloWorldTest {

    // Execute the same tests but in native mode.
}