package com.example.palys.datasender;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Palys on 2015-11-21.
 */
public class DataHolder extends ArrayList<DataFrame> {

    public int dataLength() {
        if (size() == 0) {
            return 0;
        }

        return size() * get(0).dataLength();
    }

    public byte[] data() {

        byte[] bytes = new byte[dataLength()];

        for (int i = 0; i < size(); i++) {
            System.arraycopy(get(i).data(), 0, bytes, i * get(0).dataLength(), get(1).dataLength());
        }

        return bytes;
    }
}
