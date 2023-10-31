import org.example.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class Tests {


    @Test
    public void metaPutTest() {
        String path = "/Users/svetislavdobromirov/EDU___CATION/Java/Factory/AES/src/main/resources/File1out";
        Long size = 112313L;
        MetaDatas.putMetaSize(path, size);
        Assert.assertEquals(MetaDatas.getMetaSize(path), size);
    }

    @Test
    public void testPicture(){
        String pathOrig = "/Users/svetislavdobromirov/Documents/видеоТест3.mp4";
        String pathCrypt = "/Users/svetislavdobromirov/Documents/видеоТест3E.mp4";
        String pathDecrypt = "/Users/svetislavdobromirov/Documents/видеоТест3Ee.mp4";
for (int t_n = 2; t_n < 10; t_n++){
    ThreadSettings.THREAD_NUMBERS = t_n; // From 2 to 30 Step 1
    for(int n_b = 100; n_b < 10000; n_b += 500) {
        ThreadSettings.NUM_BLOCkS = n_b; // From 100 to 30000 Step 100
        for(int s_c_b = 1; s_c_b < 17;  s_c_b +=2 ) {
            long time = System.currentTimeMillis();
            ThreadSettings.SizeCipherBlocks = s_c_b; // From 1 to 16

            byte[] key = KeyGenerator_Test.generateKey();
            try {
                Crypter crypter = new AES_256_CTR();
                crypter.begin(pathOrig, pathCrypt, key, AES_256_CTR.TYPE.ENCODE);
                crypter.begin(pathCrypt, pathDecrypt, key, AES_256_CTR.TYPE.DECODE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.print((System.currentTimeMillis() - time) + ";");
            long usedBytes = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();

            System.out.printf("%.2f; ", usedBytes/1048576.0);
            System.out.println("\"THREAD_NUMBERS = " + t_n + " NUM_BLOCKS = " + n_b + " SizeCipherBlocks = " + s_c_b + "\" ");


        }
    }
}
    }

}
