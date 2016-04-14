package com.xy.puzzle;

import static com.xy.puzzle.Cell.newCell;
import static com.xy.puzzle.Figure.newFigure;
import static com.xy.puzzle.Position.newPosition;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
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
        List<Placement> placements = puzzle.solve();

        assertEquals(asList(newPosition(0, 1, 0), newPosition(0, 0, 0), newPosition(0, 0, 0)),
                ImmutableList.copyOf(placements.stream().map(Placement::getPosition).iterator()));
    }

    @Test
    public void testSample() {
        Dim dim = Dim.newDim(3);

        Figure cube2x2x2 = newFigure(
                newCell(0, 0, 0), newCell(0, 1, 0),
                newCell(1, 0, 0), newCell(1, 1, 0),
                newCell(0, 0, 1), newCell(0, 1, 1),
                newCell(1, 0, 1), newCell(1, 1, 1));
        Figure plate3x3 = newFigure(
                newCell(0, 0, 0), newCell(1, 0, 0), newCell(2, 0, 0),
                newCell(0, 1, 0), newCell(1, 1, 0), newCell(2, 1, 0),
                newCell(0, 2, 0), newCell(1, 2, 0), newCell(2, 2, 0));
        Figure stick3 = newFigure(newCell(0, 0, 0), newCell(1, 0, 0), newCell(2, 0, 0));
        Figure stick2 = newFigure(newCell(0, 0, 0), newCell(1, 0, 0));

        Puzzle puzzle = new Puzzle(dim);
        puzzle.addFigure(cube2x2x2);
        puzzle.addFigure(plate3x3);
        puzzle.addFigure(stick3);
        puzzle.addFigure(stick3);
        puzzle.addFigure(stick2);
        puzzle.addFigure(stick2);
        assertEquals(0, puzzle.getRemain());
        List<Placement> placements = puzzle.solve();

        assertEquals(6, placements.size());
        assertArrayEquals(new Object[] {
                newPosition(0, 0, 1), newPosition(0, 2, 0), newPosition(2, 1, 0),
                newPosition(2, 0, 0), newPosition(1, 0, 0), newPosition(0, 0, 0) },
                placements.stream().map(Placement::getPosition).toArray());

        List<Cell> cells = new ArrayList<>();
        for (Placement p : placements) {
            for (Cell c : p.getRotation()) {
                cells.add(p.getPosition().move(c));
            }
        }
        assertEquals(27, cells.size());
        assertEquals(27, new HashSet<>(cells).size());
        assertArrayEquals(new Object[] { true }, cells.stream().map(c -> dim.isValid(c)).distinct().toArray());
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
        List<Placement> placements = puzzle.solve();

        assertEquals(8, placements.stream().map(Placement::getPosition).distinct().count());
        assertArrayEquals(new Object[] { true }, placements.stream()
                .map(Placement::getPosition).map(p -> dim.isValid(p)).distinct().toArray());
    }

}
