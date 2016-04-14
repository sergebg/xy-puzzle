package com.xy.puzzle;

import static com.xy.puzzle.Cell.newCell;
import static com.xy.puzzle.Figure.newFigure;
import static com.xy.puzzle.Position.newPosition;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class PuzzleTest {

    @Test
    public void testRotations() {
        Dim dim = Dim.newDim(2, 2, 2);

        Figure angle1 = newFigure(newCell(0, 0, 0), newCell(0, 0, 1), newCell(0, 1, 1));
        Figure angle2 = newFigure(newCell(1, 1, 1), newCell(1, 0, 1), newCell(1, 0, 0));
        Figure stick = newFigure(newCell(1, 1, 0), newCell(1, 1, 1));

        Puzzle puzzle = new Puzzle(dim);
        assertEquals(24, puzzle.addFigure(angle1));
        assertEquals(24 * 24, puzzle.addFigure(angle2));
        assertEquals(24 * 24 * 12, puzzle.addFigure(stick));
        int x = puzzle.solve();

        List<Placement> pp = puzzle.decodePositions(x);
        assertEquals(asList(newPosition(0, 1, 0), newPosition(0, 0, 0), newPosition(0, 0, 0)),
                ImmutableList.copyOf(pp.stream().map(Placement::getPosition).iterator()));
    }

    @Test
    public void testSimpleFigures() {
        Dim dim = Dim.newDim(2, 2, 2);
        Puzzle puzzle = new Puzzle(dim);
        Figure figure = newFigure(newCell(0, 0, 0));
        assertEquals(8, puzzle.addFigure(figure));
        assertEquals(64, puzzle.addFigure(figure));
        assertEquals(64 * 8, puzzle.addFigure(figure));
        assertEquals(64 * 64, puzzle.addFigure(figure));
        assertEquals(64 * 64 * 8, puzzle.addFigure(figure));
        assertEquals(64 * 64 * 64, puzzle.addFigure(figure));
        assertEquals(64 * 64 * 64 * 8, puzzle.addFigure(figure));
        assertEquals(64 * 64 * 64 * 64, puzzle.addFigure(figure));
        int x = puzzle.solve();

        List<Placement> placements = puzzle.decodePositions(x);
        assertEquals(8, placements.stream().map(Placement::getPosition).distinct().count());
    }

}
