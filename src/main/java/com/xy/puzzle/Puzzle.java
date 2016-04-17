package com.xy.puzzle;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;

public class Puzzle {

    private final Dim dimension;

    private int remain;

    private long combinationNumber = 1;

    private final List<List<BitSet>> masks = new ArrayList<>();

    private final List<List<Placement>> placements = new ArrayList<>();

    public Puzzle(Dim dimension) {
        this.dimension = dimension;
        remain = dimension.getSize();
    }

    public long addFigure(Figure figure) {
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

    public int getRemain() {
        return remain;
    }

    public int getSize() {
        return dimension.getSize();
    }

    public List<Placement> solve() {
        int d = dimension.getSize();
        BitSet spaceMask = new BitSet(d);
        spaceMask.set(0, d);

        int n = masks.size();
        int[] combination = new int[n];
        if (solveInternal(spaceMask, 0, combination)) {
            List<Placement> list = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                List<Placement> figurePlacements = placements.get(i);
                list.add(figurePlacements.get(combination[i]));
            }
            return ImmutableList.copyOf(list);
        }
        
        return Collections.emptyList();
    }

    private boolean solveInternal(BitSet spaceMask, int figureNumber, int[] combination) {
        if (figureNumber == masks.size()) {
            return true;
        }
        List<BitSet> figureMasks = masks.get(figureNumber);
        int n = figureMasks.size();
        int spaceSize = spaceMask.cardinality();
        for (int i = 0; i < n; i++) {
            BitSet figureMask = figureMasks.get(i);
            int figureSize = figureMask.cardinality();
            spaceMask.xor(figureMask);
            int remain = spaceMask.cardinality();
            if (remain == spaceSize - figureSize) {
                if (solveInternal(spaceMask, figureNumber + 1, combination)) {
                    combination[figureNumber] = i;
                    return true;
                }
            }
            spaceMask.xor(figureMask);
        }
        return false;
    }

}
