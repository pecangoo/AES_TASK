package org.example;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class AES_256_CTR implements Crypter {
    private ReaderFile readerFile = null;
    private WriterFile writerFile = null;
    private String outPath = null;
    private String inPath = null;
    private Integer numThread = 1;


    public void begin(String inPath, String outPath, byte[] key,
                      Crypter.TYPE type) throws IOException {
        readerFile = new ReaderFile();
        writerFile = new WriterFile(outPath);
        this.outPath = outPath;
        this.inPath = inPath;
        begin(key, type);

        try {
            readerFile.closeStreams();
            writerFile.closeStreams();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void begin(byte[] key,
                      Crypter.TYPE type) {
        try {
            switch (type) {
                case ENCODE:
                    crypt(key, Cipher.ENCRYPT_MODE);
                    break;
                case DECODE:
                    crypt(key, Cipher.DECRYPT_MODE);
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void crypt(byte[] key, Integer cryptMode)
            throws IOException {

        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(ThreadSettings.THREAD_NUMBERS);
        executor.setMaximumPoolSize(ThreadSettings.THREAD_NUMBERS);

        ArrayList<CryptTH> listCalls = new ArrayList<>();
        readerFile.init(this.inPath);

        // Если режим ENCODE, то узнаем размер исходника и ставим мету в конечным файл
        if (cryptMode == 1/*mode*/) {
            MetaDatas.putMetaSize(outPath, readerFile.getSize().longValue());
        } else {
            MetaDatas.getMetaSize(inPath);
        }

        try {
            while (true) {
                // Считывается исходный файл в одном потоке циклично по блокам
                ByteArrayInputStream byteArrayInputStream =
                        readerFile.readNextBlock();

                if (byteArrayInputStream.available() != 0) {
                    listCalls.add(getNewThread(
                            byteArrayInputStream,
                            key,
                            cryptMode));
                }

                if ((listCalls.size() == ThreadSettings.THREAD_NUMBERS ||
                        byteArrayInputStream.available() == 0)
                        && listCalls.size() != 0) {
                    try {
                        List<Future<ByteArrayObj>> answer;
                        answer = executor.invokeAll(listCalls);
                        for (Future<ByteArrayObj> future : answer) {
                            while (!future.isDone())
                                Thread.sleep(10);
                            writerFile.write(future.get());
                        }

                    } catch (InterruptedException e) {

                    }
                    listCalls.clear();

                } else if (listCalls.size() == 0) {
                    break;
                }
            }
            executor.shutdown();
        } catch (IOException | ExecutionException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        numThread = 1; // Обнуляем потоки
    }

    private CryptTH getNewThread(
            ByteArrayInputStream byteArrayInputStream,
            byte[] key,
            Integer cipherMode)
            throws InvalidAlgorithmParameterException,
            InvalidKeyException, NoSuchPaddingException,
            NoSuchAlgorithmException {

        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");

        cipher.init(cipherMode,
                keySpec,
                ThreadSettings.ivParameterSpec);

        return new CryptTH(cipher,
                byteArrayInputStream,
                numThread++);

    }
}
