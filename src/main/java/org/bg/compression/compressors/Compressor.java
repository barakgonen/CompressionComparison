package org.bg.compression.compressors;

import java.io.IOException;

public interface Compressor<T> {
    byte[] compress(T objectToCompress) throws IOException;

    T uncompress(byte[] compressedBytes) throws IOException, ClassNotFoundException;
}
