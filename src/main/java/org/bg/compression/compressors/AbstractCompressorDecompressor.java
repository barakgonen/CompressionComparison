package org.bg.compression.compressors;

import java.io.*;

public abstract class AbstractCompressorDecompressor<T> implements Compressor<T> {
    protected CompressorType compressorType;

    protected AbstractCompressorDecompressor(CompressorType compressorType) {
        this.compressorType = compressorType;
    }

    @Override
    public byte[] compress(T objectToCompress) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(outputStream);
        oos.writeObject(objectToCompress);
        oos.flush();
        byte[] compressedB1 = outputStream.toByteArray();
        return customCompression(compressedB1);
    }

    @Override
    public T uncompress(byte[] compressedBytes) throws IOException, ClassNotFoundException {
        byte[] uncompressedBytes = customDeCompression(compressedBytes);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(uncompressedBytes);

        ObjectInputStream ois = new ObjectInputStream(inputStream);
        Object o = ois.readObject();
        return (T) o;
    }

    protected abstract byte[] customCompression(byte[] objectToCompress) throws IOException;

    protected abstract byte[] customDeCompression(byte[] objectToCompress) throws IOException;

    public CompressorType getCompressorType() {
        return this.compressorType;
    }
}
