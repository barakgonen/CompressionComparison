package org.bg.compression.compressors;

import org.xerial.snappy.Snappy;

import java.io.IOException;

public class SnappyCompressor<T> extends AbstractCompressorDecompressor<T> {

    public SnappyCompressor() {
        super(CompressorType.SNAPPY);
    }

    @Override
    protected byte[] customCompression(byte[] objectToCompress) throws IOException {
        return Snappy.compress(objectToCompress);
    }

    @Override
    protected byte[] customDeCompression(byte[] objectToCompress) throws IOException {
        return Snappy.uncompress(objectToCompress);
    }
}
