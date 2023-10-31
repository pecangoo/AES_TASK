package org.example;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriterFile {
    private final FileOutputStream fileOutputStream;

    public WriterFile(String outPath) throws IOException {
        File file = new File(outPath);
        if (file.exists())
            file.delete();
        file.createNewFile();
        fileOutputStream = new FileOutputStream(file, true);
    }


    public void write(ByteArrayObj byteArrayObj) {

        ByteArrayOutputStream byteArrayOutputStream
                = byteArrayObj.getByteArrayOutputStream();
        try {
            this.fileOutputStream.write(byteArrayOutputStream.toByteArray(),
                    0, byteArrayObj.getSizeStrm().intValue());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeStreams() {
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

}
