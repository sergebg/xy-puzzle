package com.xy.puzzle;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

public class Figure {

    public static Figure newFigure(Cell... array) {
        Preconditions.checkNotNull(array);
        Preconditions.checkArgument(array.length > 0);
        Cell[] copy = new Cell[array.length];
        for (int i = 0; i < array.length; i++) {
            copy[i] = array[i];
        }
        Arrays.sort(copy, new Comparator<Cell>() {

            @Override
            public int compare(Cell c1, Cell c2) {
                int result = Integer.compare(c1.getZ(), c2.getZ());
                if (result == 0) {
                    result = Integer.compare(c1.getY(), c2.getY());
                    if (result == 0) {
                        result = Integer.compare(c1.getX(), c2.getX());
                    }
                }
                return result;
            }

        });
        return new Figure(copy);
    }

    public static Figure newFigure(Collection<Cell> cells) {
        Preconditions.checkNotNull(cells);
        Preconditions.checkArgument(!cells.isEmpty());
        return newFigure(cells.toArray(new Cell[cells.size()]));
    }

    private final int hashCode;

    private final Cell[] array;

    private Figure(Cell[] array) {
        this.array = array;
        this.hashCode = calculateHashCode(array);
    }

    private int calculateHashCode(Cell[] array) {
        int hashCode = array[0].hashCode();
        for (int i = 1; i < array.length; i++) {
            hashCode ^= array[i].hashCode();
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Figure other = (Figure) obj;
        if (hashCode != other.hashCode) {
            return false;
        }
        if (!Arrays.equals(array, other.array)) {
            return false;
        }
        return true;
    }

    public Set<Set<Cell>> getRotations() {
        return Cells.rotations(ImmutableSet.copyOf(array));
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    public int size() {
        return array.length;
    }

}
