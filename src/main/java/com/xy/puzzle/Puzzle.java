package com.xy.puzzle;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

public class Puzzle {

    private final Dim dimension;

    private int remain;

    private int combinationNumber = 1;

    private final List<List<Cell>> figures = new ArrayList<>();

    private final List<List<BitSet>> masks = new ArrayList<>();

    private final List<List<Placement>> placements = new ArrayList<>();

    public Puzzle(Dim dimension) {
        this.dimension = dimension;
        remain = dimension.getSize();
    }

    public int addFigure(Figure figure) {
        Preconditions.checkNotNull(figure);
        Preconditions.checkArgument(remain >= figure.size());

        // generate all rotations,
        // for each rotation find possible positions,
        // for each position generate mask
        Set<Set<Cell>> rotations = figure.getRotations();
        List<Placement> figurePlacements = new ArrayList<>();
        List<BitSet> figureMasks = new ArrayList<>();
        for (Set<Cell> rotation : rotations) {
            List<Position> rotationPositions = Cells.offsets(rotation, dimension);
            for (Position position : rotationPositions) {
                figureMasks.add(Cells.mask(dimension, rotation, position));
                figurePlacements.add(new Placement(figure, rotation, position));
            }
        }
        if (figureMasks.isEmpty()) {
            throw new IllegalArgumentException();
        }

        masks.add(ImmutableList.copyOf(figureMasks));
        placements.add(ImmutableList.copyOf(figurePlacements));
        remain -= figure.size();
        combinationNumber *= figureMasks.size();

        return combinationNumber;
    }

    public List<Placement> decodePositions(int i) {
        List<Placement> list = new ArrayList<>(figures.size());
        for (List<Placement> figurePlacements : placements) {
            int n = figurePlacements.size();
            list.add(figurePlacements.get(i % n));
            i /= n;
        }
        return ImmutableList.copyOf(list);
    }

    public int getSize() {
        return dimension.getSize();
    }

    public int solve() {
        final int d = dimension.getSize();
        for (int i = 0; i < combinationNumber; i++) {
            BitSet spaceMask = new BitSet(d);
            spaceMask.set(0, d);
            int n = 1;
            boolean solved = true;
            for (List<BitSet> figureMasks : masks) {
                int m = figureMasks.size();
                int k = i / n % m;
                n *= m;

                BitSet figureMask = figureMasks.get(k);
                int figureSize = figureMask.cardinality();
                int spaceSize = spaceMask.cardinality();

                spaceMask.xor(figureMask);
                if (spaceMask.cardinality() != spaceSize - figureSize) {
                    spaceMask.xor(figureMask);
                    solved = false;
                    break;
                }
            }
            if (solved) {
                return i;
            }
        }
        return -1;
    }

}
