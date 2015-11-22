package com.example.palys.datasender;

import java.nio.ByteBuffer;

/**
 * Created by Palys on 2015-11-21.
 */
public class Vector3 {

    private final float x;

    private final float y;

    private final float z;

    private final static int FLOAT_LENGTH = 4;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(float[] values) {
        this(values[0], values[1], values[2]);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }


    public byte[] data() {

        ByteBuffer b = ByteBuffer.allocate(3 * FLOAT_LENGTH);

        b.putFloat(0, x);
        b.putFloat(FLOAT_LENGTH, y);
        b.putFloat(2 * FLOAT_LENGTH, z);

        return b.array();
    }

    public static int dataLength() {
        return 3 * FLOAT_LENGTH;
    }
}
