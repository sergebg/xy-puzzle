package com.xy.puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

public class Cells {

    public static Cell high(List<Cell> cells) {
        int[] high = { Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE };
        for (Cell cell : cells) {
            cell.updateHigh(high);
        }
        return new Cell(high[0], high[1], high[2]);
    }

    public static Cell low(List<Cell> cells) {
        int[] low = { Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE };
        for (Cell cell : cells) {
            cell.updateLow(low);
        }
        return new Cell(low[0], low[1], low[2]);
    }

    public static List<Cell> normalize(List<Cell> cells) {
        List<Cell> list = new ArrayList<>();
        Cell base = low(cells);
        for (Cell cell : cells) {
            list.add(cell.relative(base));
        }
        return list;
    }

    public static String print(List<Cell> cells) {
        Cell low = low(cells);
        Cell high = high(cells);

        int dX = high.getX() - low.getX() + 1;
        int dY = high.getY() - low.getY() + 1;
        int dZ = high.getZ() - low.getZ() + 1;

        int height = dZ + dY + 1;
        int width = dX + dY + 1;
        char[] chars = new char[width * height];
        Arrays.fill(chars, ' ');

        for (Cell c : cells) {
            Cell r = c.relative(low);
            int xzX = r.getX();
            int xzY = dZ - r.getZ() - 1;
            chars[xzX + width * xzY] = '#';

            int yzX = dX + 1 + r.getY();
            int yzY = xzY;
            chars[yzX + width * yzY] = '#';

            int xyX = xzX;
            int xyY = dZ + 1 + r.getY();
            chars[xyX + width * xyY] = '#';
        }

        StringBuilder image = new StringBuilder();
        for (int p = 0; p < chars.length; p += width) {
            image.append('\n').append(chars, p, width);
        }
        return image.toString();
    }

    public static List<Cell> rotateXY(List<Cell> cells) {
        List<Cell> rotation = new ArrayList<>(cells.size());
        for (Cell cell : cells) {
            rotation.add(new Cell(-cell.getY(), cell.getX(), cell.getZ()));
        }
        return normalize(rotation);
    }

    public static List<Cell> rotateXZ(List<Cell> cells) {
        List<Cell> rotation = new ArrayList<>(cells.size());
        for (Cell cell : cells) {
            rotation.add(new Cell(-cell.getZ(), cell.getY(), cell.getX()));
        }
        return normalize(rotation);
    }

    public static List<Cell> rotateYZ(List<Cell> cells) {
        List<Cell> rotation = new ArrayList<>(cells.size());
        for (Cell cell : cells) {
            rotation.add(new Cell(cell.getX(), -cell.getZ(), cell.getY()));
        }
        return normalize(rotation);
    }

    private Cells() {
        throw new AssertionError();
    }

    public static Set<Set<Cell>> rotations(List<Cell> cells) {
        
        List<List<Cell>> states = new ArrayList<>(24);
        states.add(normalize(cells));
        states.add(rotateXY(states.get(states.size() - 1)));
        states.add(rotateXY(states.get(states.size() - 1)));
        states.add(rotateXY(states.get(states.size() - 1)));

        states.add(rotateXZ(states.get(0)));
        states.add(rotateXY(states.get(states.size() - 1)));
        states.add(rotateXY(states.get(states.size() - 1)));
        states.add(rotateXY(states.get(states.size() - 1)));

        states.add(rotateXZ(states.get(4)));
        states.add(rotateXY(states.get(states.size() - 1)));
        states.add(rotateXY(states.get(states.size() - 1)));
        states.add(rotateXY(states.get(states.size() - 1)));
        
        states.add(rotateXZ(states.get(8)));
        states.add(rotateXY(states.get(states.size() - 1)));
        states.add(rotateXY(states.get(states.size() - 1)));
        states.add(rotateXY(states.get(states.size() - 1)));
        
        states.add(rotateXZ(states.get(1)));
        states.add(rotateXY(states.get(states.size() - 1)));
        states.add(rotateXY(states.get(states.size() - 1)));
        states.add(rotateXY(states.get(states.size() - 1)));
        
        states.add(rotateXZ(states.get(3)));
        states.add(rotateXY(states.get(states.size() - 1)));
        states.add(rotateXY(states.get(states.size() - 1)));
        states.add(rotateXY(states.get(states.size() - 1)));
        
        Set<Set<Cell>> uniqueRotations = new HashSet<>(24);
        for (List<Cell> state : states) {
            uniqueRotations.add(ImmutableSet.copyOf(state));
        }
        return ImmutableSet.copyOf(uniqueRotations);
    }

}
