package com.xy.puzzle;

import com.google.common.base.Preconditions;

public class Dim {

    private static final int MAX_SIZE = Integer.MAX_VALUE;

    public static Dim newDim(int x, int y, int z) {
        Preconditions.checkArgument(x >= 0 && y >= 0 && z >= 0);
        Preconditions.checkArgument(x <= MAX_SIZE && y <= MAX_SIZE && z <= MAX_SIZE);
        Preconditions.checkArgument(x * y * z == 0 || x * y * z / x / y / z == 1);
        Preconditions.checkArgument(x * y * z <= MAX_SIZE);
        return new Dim(x, y, z);
    }

    private final int x;

    private final int y;

    private final int z;

    private Dim(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getSize() {
        return x * y * z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    private boolean isUnderLimit(int value, int limit) {
        return value >= 0 && value < limit;
    }

    public boolean isValid(Position p) {
        return isUnderLimit(p.getX(), x) && isUnderLimit(p.getY(), y) && isUnderLimit(p.getZ(), z);
    }

    @Override
    public String toString() {
        return "{" + x + ", " + y + ", " + z + "}";
    }

}
