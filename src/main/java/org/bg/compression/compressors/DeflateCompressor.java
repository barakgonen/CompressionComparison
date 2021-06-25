package org.bg.compression.compressors;

import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * DEFLATE is a lossless data compression algorithm that uses both the LZ77 algorithm and Huffman Coding. The source code for DEFLATE compression and decompression can be on the free, general-purpose compression library zlib Found, zlib official website:http://www.zlib.net/
 * jdk provides support for the zlib compression library, the compression class Deflater and the decompression class Inflater, both Deflater and Inflater provide native methods
 */
public class DeflateCompressor<T> extends AbstractCompressorDecompressor<T> {
    private int compressionLevel;

    public DeflateCompressor(int compressionLevel) {
        super(CompressorType.DEFLATE);
        this.compressionLevel = compressionLevel;
    }

    @Override
    protected byte[] customCompression(byte[] input) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Deflater compressor = new Deflater(this.compressionLevel);
        try {
            compressor.setInput(input);
            compressor.finish();
            final byte[] buf = new byte[2048];
            while (!compressor.finished()) {
                int count = compressor.deflate(buf);
                bos.write(buf, 0, count);
            }
        } finally {
            compressor.end();
        }
        return bos.toByteArray();
    }

    @Override
    protected byte[] customDeCompression(byte[] input) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Inflater decompressor = new Inflater();
        try {
            decompressor.setInput(input);
            final byte[] buf = new byte[2048];
            while (!decompressor.finished()) {
                int count = decompressor.inflate(buf);
                bos.write(buf, 0, count);
            }
        } catch (DataFormatException e) {
            e.printStackTrace();
        } finally {
            decompressor.end();
        }
        return bos.toByteArray();
    }
}
