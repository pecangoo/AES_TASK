package org.example;

import java.io.ByteArrayOutputStream;

/**
 * Contains ByteArray and expected size of Block
 */
public class ByteArrayObj {
    private ByteArrayOutputStream byteArrayOutputStream;
    private Long sizeStrm;

    public ByteArrayOutputStream getByteArrayOutputStream() {
        return byteArrayOutputStream;
    }

    public void setByteArrayOutputStream(ByteArrayOutputStream byteArrayOutputStream) {
        this.byteArrayOutputStream = byteArrayOutputStream;
    }

    public Long getSizeStrm() {
        return sizeStrm;
    }

    public void setSizeStrm(Long sizeStrm) {
        this.sizeStrm = sizeStrm;
    }
}
