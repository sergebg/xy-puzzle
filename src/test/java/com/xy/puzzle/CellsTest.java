package com.xy.puzzle;

import static com.xy.puzzle.Cells.rotateXY;
import static com.xy.puzzle.Cells.rotateXZ;
import static com.xy.puzzle.Cells.rotateYZ;
import static com.xy.puzzle.Cells.rotations;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public class CellsTest {

    @Test
    public void testHighLow() {
        List<Cell> cells = ImmutableList.of(new Cell(0, 5, 2), new Cell(2, 0, 2), new Cell(3, 3, 1));
        assertEquals(new Cell(0, 0, 1), Cells.low(cells));
        assertEquals(new Cell(3, 5, 2), Cells.high(cells));
    }

    @Test
    public void testOffsets() {
        assertEquals(6, Cells.offsets(asList(new Cell(0, 0, 0)), new Cell(0, 1, 2)).size());
        assertEquals(0, Cells.offsets(asList(new Cell(1, 0, 0)), new Cell(0, 1, 2)).size());
        assertEquals(4, Cells.offsets(asList(new Cell(0, 0, 0), new Cell(0, 0, 1)), new Cell(0, 1, 2)).size());
        assertEquals(2, Cells.offsets(asList(new Cell(0, 0, 0), new Cell(0, 0, 2)), new Cell(0, 1, 2)).size());
        assertEquals(3, Cells.offsets(asList(new Cell(0, 0, 0), new Cell(0, 1, 0)), new Cell(0, 1, 2)).size());
    }

    @Test
    public void testPrint() {
        assertEquals("\n#  # \n## ##\n#  # \n     \n##   \n#    ", Cells.print(asList(
                new Cell(1, 1, 1), new Cell(1, 1, 2), new Cell(1, 2, 1), new Cell(2, 1, 1), new Cell(1, 1, 0))));
    }

    @Test
    public void testRotateXY() {
        assertEquals(asList(new Cell(0, 0, 0), new Cell(0, 1, 0)), rotateXY(asList(new Cell(0, 0, 0), new Cell(1, 0, 0))));
        assertEquals(asList(new Cell(1, 0, 0), new Cell(0, 0, 0)), rotateXY(asList(new Cell(0, 0, 0), new Cell(0, 1, 0))));
        assertEquals(asList(new Cell(0, 0, 0), new Cell(0, 0, 1)), rotateXY(asList(new Cell(0, 0, 0), new Cell(0, 0, 1))));
    }

    @Test
    public void testRotateXZ() {
        assertEquals(asList(new Cell(0, 0, 0), new Cell(0, 0, 1)), rotateXZ(asList(new Cell(0, 0, 0), new Cell(1, 0, 0))));
        assertEquals(asList(new Cell(0, 0, 0), new Cell(0, 1, 0)), rotateXZ(asList(new Cell(0, 0, 0), new Cell(0, 1, 0))));
        assertEquals(asList(new Cell(1, 0, 0), new Cell(0, 0, 0)), rotateXZ(asList(new Cell(0, 0, 0), new Cell(0, 0, 1))));
    }

    @Test
    public void testRotateYZ() {
        assertEquals(asList(new Cell(0, 0, 0), new Cell(1, 0, 0)), rotateYZ(asList(new Cell(0, 0, 0), new Cell(1, 0, 0))));
        assertEquals(asList(new Cell(0, 0, 0), new Cell(0, 0, 1)), rotateYZ(asList(new Cell(0, 0, 0), new Cell(0, 1, 0))));
        assertEquals(asList(new Cell(0, 1, 0), new Cell(0, 0, 0)), rotateYZ(asList(new Cell(0, 0, 0), new Cell(0, 0, 1))));
    }

    @Test
    public void testRotations() {
        assertEquals(ImmutableSet.of(
                ImmutableSet.of(new Cell(0, 0, 0), new Cell(0, 0, 1)),
                ImmutableSet.of(new Cell(0, 0, 0), new Cell(0, 1, 0)),
                ImmutableSet.of(new Cell(0, 0, 0), new Cell(1, 0, 0))),
                rotations(asList(new Cell(1, 2, 3), new Cell(1, 2, 4))));
    }

}
