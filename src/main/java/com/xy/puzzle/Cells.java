package com.xy.puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

public class Cells {

    private static Cell available(Cell cell1, Cell cell2) {
        int x = Math.max(1 + cell1.getX() - cell2.getX(), 0);
        int y = Math.max(1 + cell1.getY() - cell2.getY(), 0);
        int z = Math.max(1 + cell1.getZ() - cell2.getZ(), 0);
        return new Cell(x, y, z);
    }

    public static Cell high(Collection<Cell> figure) {
        int[] high = { Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE };
        for (Cell cell : figure) {
            cell.updateHigh(high);
        }
        return new Cell(high[0], high[1], high[2]);
    }

    public static Cell low(Collection<Cell> figure) {
        int[] low = { Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE };
        for (Cell cell : figure) {
            cell.updateLow(low);
        }
        return new Cell(low[0], low[1], low[2]);
    }

    public static Collection<Cell> normalize(Collection<Cell> figure) {
        Cell base = low(figure);
        if (0 == base.getX() && 0 == base.getY() && 0 == base.getZ()) {
            return figure;
        }
        List<Cell> list = new ArrayList<>(figure.size());
        for (Cell cell : figure) {
            list.add(cell.relative(base));
        }
        return list;
    }

    public static List<Cell> offsets(List<Cell> figure, Cell space) {
        Cell figureDim = high(figure);
        Cell dim = available(space, figureDim);
        int n = dim.getX() * dim.getY() * dim.getZ();
        if (n == 0) {
            return Collections.emptyList();
        }
        List<Cell> offsets = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            int x = i % dim.getX();
            int y = i / dim.getX() % dim.getY();
            int z = i / dim.getX() / dim.getY();
            offsets.add(new Cell(x, y, z));
        }
        return offsets;
    }

    public static String print(Collection<Cell> figure) {
        Cell low = low(figure);
        Cell high = high(figure);

        int dX = high.getX() - low.getX() + 1;
        int dY = high.getY() - low.getY() + 1;
        int dZ = high.getZ() - low.getZ() + 1;

        int height = dZ + dY + 1;
        int width = dX + dY + 1;
        char[] chars = new char[width * height];
        Arrays.fill(chars, ' ');

        for (Cell c : figure) {
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

    public static Collection<Cell> rotateXY(Collection<Cell> figure) {
        List<Cell> rotation = new ArrayList<>(figure.size());
        for (Cell cell : figure) {
            rotation.add(new Cell(-cell.getY(), cell.getX(), cell.getZ()));
        }
        return normalize(rotation);
    }

    public static Collection<Cell> rotateXZ(Collection<Cell> figure) {
        List<Cell> rotation = new ArrayList<>(figure.size());
        for (Cell cell : figure) {
            rotation.add(new Cell(-cell.getZ(), cell.getY(), cell.getX()));
        }
        return normalize(rotation);
    }

    public static Collection<Cell> rotateYZ(Collection<Cell> figure) {
        List<Cell> rotation = new ArrayList<>(figure.size());
        for (Cell cell : figure) {
            rotation.add(new Cell(cell.getX(), -cell.getZ(), cell.getY()));
        }
        return normalize(rotation);
    }

    public static Set<Set<Cell>> rotations(Collection<Cell> figure) {

        List<Collection<Cell>> states = new ArrayList<>(24);
        states.add(normalize(figure));
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
        for (Collection<Cell> state : states) {
            uniqueRotations.add(ImmutableSet.copyOf(state));
        }
        return ImmutableSet.copyOf(uniqueRotations);
    }

    private Cells() {
        throw new AssertionError();
    }

}
