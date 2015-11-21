package com.example.palys.datasender;

import java.nio.ByteBuffer;

/**
 * Created by Palys on 2015-11-21.
 */
public class DataFrame {

    private final long time;

    private final static int LONG_LENGTH = 8;

    private final Vector3 acceleration;

    private final Vector3 gyroscope;

    private final boolean gyroscopeAvailable;

    public DataFrame(long time, Vector3 acceleration, Vector3 gyroscope) {
        this.time = time;
        this.acceleration = acceleration;
        this.gyroscope = gyroscope;
        if (gyroscope != null) {
            gyroscopeAvailable = true;
        } else {
            gyroscopeAvailable = false;
        }
    }

    public DataFrame(long time, Vector3 acceleration) {
        this(time, acceleration, null);
    }

    public long getTime() {
        return time;
    }

    public Vector3 getAcceleration() {
        return acceleration;
    }

    public Vector3 getGyroscope() {
        return gyroscope;
    }

    public boolean isGyroscopeAvailable() {
        return gyroscopeAvailable;
    }

    public byte[] data() {

        int length = LONG_LENGTH + Vector3.dataLength();

        if (gyroscopeAvailable) {
            length += Vector3.dataLength();
        }

        byte[] bytes = new byte[length];

        byte[] timeBytes = ByteBuffer.allocate(LONG_LENGTH).putLong(time).array();
        int offset = 0;

        System.arraycopy(timeBytes, 0, bytes, offset, LONG_LENGTH);
        offset = LONG_LENGTH;

        System.arraycopy(acceleration.data(), 0, bytes, offset, acceleration.dataLength());
        offset += acceleration.dataLength();

        if (gyroscopeAvailable) {
            System.arraycopy(gyroscope.data(), 0, bytes, offset, gyroscope.dataLength());
        }

        return bytes;
    }

    public int dataLength() {
        if (gyroscopeAvailable) {
            return LONG_LENGTH + 2 * Vector3.dataLength();
        } else {
            return LONG_LENGTH + Vector3.dataLength();
        }
    }
}
