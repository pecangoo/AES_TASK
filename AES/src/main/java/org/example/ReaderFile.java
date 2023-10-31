package org.example;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReaderFile {
    private FileInputStream fileInputStream = null;

    public ReaderFile() {

    }

    public Integer getSize() throws IOException {
        return fileInputStream.available();
    }

    public void init(String path) throws FileNotFoundException {
        fileInputStream = new FileInputStream(path);
    }

    public void closeStreams() throws IOException {
        if (fileInputStream != null)
            fileInputStream.close();
    }


    public ByteArrayInputStream readNextBlock() throws Exception {
        if (fileInputStream == null)
            throw new Exception("ReaderFile not init");

        byte[] bytes = fileInputStream.readNBytes(
                ThreadSettings.SIZE_OF_BLOCK);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                bytes);

        return byteArrayInputStream;

    }
}
