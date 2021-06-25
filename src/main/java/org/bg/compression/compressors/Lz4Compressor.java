package org.bg.compression.compressors;

import net.jpountz.lz4.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Lz4Compressor<T> extends AbstractCompressorDecompressor<T> {

    public Lz4Compressor() {
        super(CompressorType.LZ4);
    }

    @Override
    protected byte[] customCompression(byte[] objectToCompress) throws IOException {
        LZ4Factory factory = LZ4Factory.fastestInstance();
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        LZ4Compressor compressor = factory.fastCompressor();
        LZ4BlockOutputStream compressedOutput = new LZ4BlockOutputStream(byteOutput, 2048, compressor);
        compressedOutput.write(objectToCompress);
        compressedOutput.close();
        return byteOutput.toByteArray();
    }

    @Override
    protected byte[] customDeCompression(byte[] objectToCompress) throws IOException {
        LZ4Factory factory = LZ4Factory.fastestInstance();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        LZ4FastDecompressor decompresser = factory.fastDecompressor();
        LZ4BlockInputStream lzis = new LZ4BlockInputStream(new ByteArrayInputStream(objectToCompress), decompresser);
        int count;
        byte[] buffer = new byte[2048];
        while ((count = lzis.read(buffer)) != -1) {
            baos.write(buffer, 0, count);
        }
        lzis.close();
        return baos.toByteArray();
    }
}
