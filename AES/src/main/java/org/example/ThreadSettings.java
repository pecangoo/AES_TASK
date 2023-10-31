package org.example;

import javax.crypto.spec.IvParameterSpec;

public class ThreadSettings {
    public static final IvParameterSpec ivParameterSpec =
            new IvParameterSpec(
                    new byte[]
                            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 11, 12, 13, 14, 15});
    public static Integer THREAD_NUMBERS = 4;
    public static Integer NUM_BLOCkS = 4096;
    public static final Integer SIZE_OF_BLOCK = 16 * NUM_BLOCkS;
    public static Integer SizeCipherBlocks = 8;
    public static final Integer SIZE_16 = 16 * SizeCipherBlocks;


}
