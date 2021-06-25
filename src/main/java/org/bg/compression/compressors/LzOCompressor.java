package org.bg.compression.compressors;

import org.anarres.lzo.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class LzOCompressor<T> extends AbstractCompressorDecompressor<T> {

    public LzOCompressor() {
        super(CompressorType.LZO);
    }

    @Override
    protected byte[] customCompression(byte[] objectToCompress) throws IOException {
        LzoCompressor compressor = LzoLibrary.getInstance().newCompressor(LzoAlgorithm.LZO1X, null);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        LzoOutputStream cs = new LzoOutputStream(os, compressor);
        cs.write(objectToCompress);
        cs.close();

        return os.toByteArray();
    }

    @Override
    protected byte[] customDeCompression(byte[] objectToCompress) throws IOException {
        LzoDecompressor decompressor = LzoLibrary.getInstance().newDecompressor(LzoAlgorithm.LZO1X, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteArrayInputStream is = new ByteArrayInputStream(objectToCompress);
        LzoInputStream us = new LzoInputStream(is, decompressor);
        int count;
        byte[] buffer = new byte[2048];
        while ((count = us.read(buffer)) != -1) {
            baos.write(buffer, 0, count);
        }
        return baos.toByteArray();
    }
}