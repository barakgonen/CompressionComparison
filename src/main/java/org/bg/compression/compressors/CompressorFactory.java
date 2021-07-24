package org.bg.compression.compressors;

public class CompressorFactory {
    public static <T> AbstractCompressorDecompressor getCompressor(CompressorType type) {
        switch (type) {
            case DEFLATE:
                return new DeflateCompressor<T>(1);
            case GZIP:
                return new GzipCompressor<T>();
            case BZIP2:
                return new Bzip2Compressor<T>();
            case LZO:
                return new LzOCompressor<T>();
            case LZ4:
                return new Lz4Compressor<T>();
            case SNAPPY:
                return new SnappyCompressor<T>();
            default:
                return null;
        }
    }
}
