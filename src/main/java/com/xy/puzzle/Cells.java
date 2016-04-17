package com.xy.puzzle;

import static com.xy.puzzle.Cell.newCell;
import static com.xy.puzzle.Position.newPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

public class Cells {

    private static Dim available(Collection<Cell> figure, Dim dim) {
        Cell high = high(figure);
        int x = Math.max(dim.getX() - high.getX(), 0);
        int y = Math.max(dim.getY() - high.getY(), 0);
        int z = Math.max(dim.getZ() - high.getZ(), 0);
        return Dim.newDim(x, y, z);
    }

    public static Cell high(Collection<Cell> figure) {
        int[] high = { Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE };
        for (Cell cell : figure) {
            cell.updateHigh(high);
        }
        return newCell(high[0], high[1], high[2]);
    }

    public static Cell low(Collection<Cell> figure) {
        int[] low = { Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE };
        for (Cell cell : figure) {
            cell.updateLow(low);
        }
        return newCell(low[0], low[1], low[2]);
    }

    public static BitSet mask(Dim dim, Collection<Cell> figure, Position position) {
        int n = dim.getX() * dim.getY() * dim.getZ();
        BitSet mask = new BitSet(n);
        mask.clear(0, n);
        for (Cell cell : figure) {
            int x = cell.getX() + position.getX();
            int y = cell.getY() + position.getY();
            int z = cell.getZ() + position.getZ();
            int i = x + y * dim.getX() + z * dim.getX() * dim.getY();
            mask.set(i);
        }
        return mask;
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

    public static List<Position> offsets(Collection<Cell> figure, Dim dim) {
        Dim available = available(figure, dim);
        int n = available.getSize();
        if (n == 0) {
            return Collections.emptyList();
        }
        List<Position> offsets = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            int x = i % available.getX();
            int y = i / available.getX() % available.getY();
            int z = i / available.getX() / available.getY();
            offsets.add(newPosition(x, y, z));
        }
        return offsets;
    }

    public static String print(Dim dim, Collection<Cell> cells, Position pos) {
        int dX = dim.getX();
        int dY = dim.getY();
        int dZ = dim.getZ();

        int height = dZ + dY + 1;
        int width = dX + dY + 1;
        char[] chars = new char[width * height];
        Arrays.fill(chars, ' ');
        for (int i = 0; i < dX + dY + 1; i++) {
            chars[(dX + dY + 1) * dZ + i] = '-';
        }
        for (int i = 0; i < dY + dZ + 1; i++) {
            chars[dX + i * (dX + dY + 1)] = '|';
        }
        chars[dZ * (dX + dY + 1) + dY] = '+';

        for (Cell cell : cells) {
            int xzX = cell.getX() + pos.getX();
            int xzY = dZ - cell.getZ() - pos.getZ() - 1;
            chars[xzX + width * xzY] = '#';

            int yzX = dX + 1 + cell.getY() + pos.getY();
            int yzY = xzY;
            chars[yzX + width * yzY] = '#';

            int xyX = xzX;
            int xyY = dZ + dY - cell.getY() - pos.getY();
            chars[xyX + width * xyY] = '#';
        }

        StringBuilder image = new StringBuilder();
        for (int p = 0; p < chars.length; p += width) {
            image.append('\n').append(chars, p, width);
        }
        return image.toString();
    }

    public static String print(Dim dim, List<Placement> placements) {
        StringBuilder chars = new StringBuilder();
        for (Placement p : placements) {
            chars.append(Cells.print(dim, p.getRotation(), p.getPosition())).append('\n');
        }
        return chars.toString();
    }

    public static Collection<Cell> rotateXY(Collection<Cell> figure) {
        List<Cell> rotation = new ArrayList<>(figure.size());
        for (Cell cell : figure) {
            rotation.add(newCell(-cell.getY(), cell.getX(), cell.getZ()));
        }
        return normalize(rotation);
    }

    public static Collection<Cell> rotateXZ(Collection<Cell> figure) {
        List<Cell> rotation = new ArrayList<>(figure.size());
        for (Cell cell : figure) {
            rotation.add(newCell(-cell.getZ(), cell.getY(), cell.getX()));
        }
        return normalize(rotation);
    }

    public static Collection<Cell> rotateYZ(Collection<Cell> figure) {
        List<Cell> rotation = new ArrayList<>(figure.size());
        for (Cell cell : figure) {
            rotation.add(newCell(cell.getX(), -cell.getZ(), cell.getY()));
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
