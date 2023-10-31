package org.example;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.Callable;

public class CryptTH implements Callable<ByteArrayObj> {
    private final Cipher cipher;
    private final ByteArrayInputStream byteArrayInputStream;
    private final Integer numThread;
    private ByteArrayOutputStream byteArrayOutputStream = null;

    public CryptTH(Cipher cipher,
                   ByteArrayInputStream byteArrayInputStream,

                   Integer numThread) {
        this.cipher = cipher;
        this.byteArrayInputStream = byteArrayInputStream;
        this.numThread = numThread;

        this.byteArrayOutputStream = new ByteArrayOutputStream();
    }

    @Override
    public ByteArrayObj call() {
        ByteArrayObj byteArrayObj = new ByteArrayObj();
        byteArrayObj.setSizeStrm(Integer.valueOf(
                byteArrayInputStream.available()).longValue());

        byte[] buffer = new byte[ThreadSettings.SIZE_16];
        try {
            while (true) {
                if (!(byteArrayInputStream.read(buffer) > 0)) break;
                byte[] encryptedData = cipher.update(buffer);
                byteArrayOutputStream.write(encryptedData);
            }
            byte[] finalEncryptedData = cipher.doFinal();
            byteArrayOutputStream.write(finalEncryptedData);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        }

        byteArrayObj.setByteArrayOutputStream(byteArrayOutputStream);
        return byteArrayObj;
    }
}
