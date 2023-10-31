package org.example;

import java.io.IOException;

public interface Crypter {
    void begin(String inPath, String outPath, byte[] key, TYPE type) throws IOException;

    default void begin(byte[] key, TYPE type) {
        throw new RuntimeException("Begin not Init");
    }

    enum TYPE {
        ENCODE,
        DECODE
    }
}
