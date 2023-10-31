package org.example;


import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        byte[] key = KeyGenerator_Test.generateKey();
        String pathOrig = "/Users/svetislavdobromirov/EDU___CATION/Java/Factory/AES/src/main/resources/File1.txt";
        String pathCrypt = "/Users/svetislavdobromirov/EDU___CATION/Java/Factory/AES/src/main/resources/File1out";
        String pathDecrypt = "/Users/svetislavdobromirov/EDU___CATION/Java/Factory/AES/src/main/resources/FileDecrypt";
        try {
            Crypter crypter = new AES_256_CTR();
            crypter.begin(pathOrig, pathCrypt, key, AES_256_CTR.TYPE.ENCODE);
            crypter.begin(pathCrypt, pathDecrypt, key, AES_256_CTR.TYPE.DECODE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}