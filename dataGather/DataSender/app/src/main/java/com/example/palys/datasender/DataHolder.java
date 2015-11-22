package com.example.palys.datasender;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Palys on 2015-11-21.
 */
public class DataHolder extends ArrayList<DataFrame> {

    private final static int BOOL_LENGTH = 1;

    public int dataLength() {
        if (size() == 0) {
            return 0;
        }

        return size() * get(0).dataLength() + BOOL_LENGTH;
    }

    public byte[] data() {

        byte[] bytes = new byte[dataLength()];

        boolean gyroPresent = size() > 0 && get(0).isGyroscopeAvailable();

        if (dataLength() > 0) {
            bytes[0] = (byte) (gyroPresent ? 1 : 0);

            for (int i = 0; i < size(); i++) {
                System.arraycopy(get(i).data(), 0, bytes, i * get(0).dataLength() + BOOL_LENGTH, get(0).dataLength());
            }
        }

        return bytes;
    }
}
