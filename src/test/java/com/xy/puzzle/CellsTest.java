package com.xy.puzzle;

import static com.xy.puzzle.Cell.newCell;
import static com.xy.puzzle.Cells.mask;
import static com.xy.puzzle.Cells.rotateXY;
import static com.xy.puzzle.Cells.rotateXZ;
import static com.xy.puzzle.Cells.rotateYZ;
import static com.xy.puzzle.Cells.rotations;
import static com.xy.puzzle.Dim.newDim;
import static com.xy.puzzle.Position.newPosition;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public class CellsTest {

    @Test
    public void testHighLow() {
        List<Cell> cells = ImmutableList.of(newCell(0, 5, 2), newCell(2, 0, 2), newCell(3, 3, 1));
        assertEquals(newCell(0, 0, 1), Cells.low(cells));
        assertEquals(newCell(3, 5, 2), Cells.high(cells));
    }

    @Test
    public void testMask() {
        assertEquals(1, Cells.mask(newDim(1, 1, 1), asList(newCell(0, 0, 0)), newPosition(0, 0, 0)).cardinality());

        assertEquals(1, mask(newDim(2, 2, 2), asList(newCell(0, 0, 0)), newPosition(1, 1, 1)).cardinality());
        assertArrayEquals(new long[] { 128 }, mask(newDim(2, 2, 2), asList(newCell(0, 0, 0)), newPosition(1, 1, 1)).toLongArray());
        assertArrayEquals(new long[] { 2 }, mask(newDim(2, 2, 2), asList(newCell(1, 0, 0)), newPosition(0, 0, 0)).toLongArray());
        assertArrayEquals(new long[] { 4 }, mask(newDim(2, 2, 2), asList(newCell(0, 1, 0)), newPosition(0, 0, 0)).toLongArray());
        assertArrayEquals(new long[] { 16 }, mask(newDim(2, 2, 2), asList(newCell(0, 0, 1)), newPosition(0, 0, 0)).toLongArray());

        assertEquals(3, mask(newDim(2, 2, 2), asList(newCell(1, 0, 0), newCell(0, 1, 0), newCell(0, 0, 1)), newPosition(0, 0, 0)).cardinality());
        assertArrayEquals(new long[] { 2 + 4 + 16 }, mask(newDim(2, 2, 2), asList(newCell(1, 0, 0), newCell(0, 1, 0), newCell(0, 0, 1)), newPosition(0, 0, 0)).toLongArray());
    }

    @Test
    public void testOffsets() {
        assertEquals(6, Cells.offsets(asList(newCell(0, 0, 0)), newDim(1, 2, 3)).size());
        assertEquals(0, Cells.offsets(asList(newCell(1, 0, 0)), newDim(1, 2, 3)).size());
        assertEquals(4, Cells.offsets(asList(newCell(0, 0, 0), newCell(0, 0, 1)), newDim(1, 2, 3)).size());
        assertEquals(2, Cells.offsets(asList(newCell(0, 0, 0), newCell(0, 0, 2)), newDim(1, 2, 3)).size());
        assertEquals(3, Cells.offsets(asList(newCell(0, 0, 0), newCell(0, 1, 0)), newDim(1, 2, 3)).size());
    }

    @Test
    public void testPrint() {
        assertEquals("\n#  # \n## ##\n#  # \n     \n##   \n#    ", Cells.print(asList(
                newCell(1, 1, 1), newCell(1, 1, 2), newCell(1, 2, 1), newCell(2, 1, 1), newCell(1, 1, 0))));
    }

    @Test
    public void testRotateXY() {
        assertEquals(asList(newCell(0, 0, 0), newCell(0, 1, 0)), rotateXY(asList(newCell(0, 0, 0), newCell(1, 0, 0))));
        assertEquals(asList(newCell(1, 0, 0), newCell(0, 0, 0)), rotateXY(asList(newCell(0, 0, 0), newCell(0, 1, 0))));
        assertEquals(asList(newCell(0, 0, 0), newCell(0, 0, 1)), rotateXY(asList(newCell(0, 0, 0), newCell(0, 0, 1))));
    }

    @Test
    public void testRotateXZ() {
        assertEquals(asList(newCell(0, 0, 0), newCell(0, 0, 1)), rotateXZ(asList(newCell(0, 0, 0), newCell(1, 0, 0))));
        assertEquals(asList(newCell(0, 0, 0), newCell(0, 1, 0)), rotateXZ(asList(newCell(0, 0, 0), newCell(0, 1, 0))));
        assertEquals(asList(newCell(1, 0, 0), newCell(0, 0, 0)), rotateXZ(asList(newCell(0, 0, 0), newCell(0, 0, 1))));
    }

    @Test
    public void testRotateYZ() {
        assertEquals(asList(newCell(0, 0, 0), newCell(1, 0, 0)), rotateYZ(asList(newCell(0, 0, 0), newCell(1, 0, 0))));
        assertEquals(asList(newCell(0, 0, 0), newCell(0, 0, 1)), rotateYZ(asList(newCell(0, 0, 0), newCell(0, 1, 0))));
        assertEquals(asList(newCell(0, 1, 0), newCell(0, 0, 0)), rotateYZ(asList(newCell(0, 0, 0), newCell(0, 0, 1))));
    }

    @Test
    public void testRotations() {
        assertEquals(ImmutableSet.of(
                ImmutableSet.of(newCell(0, 0, 0), newCell(0, 0, 1)),
                ImmutableSet.of(newCell(0, 0, 0), newCell(0, 1, 0)),
                ImmutableSet.of(newCell(0, 0, 0), newCell(1, 0, 0))),
                rotations(asList(newCell(1, 2, 3), newCell(1, 2, 4))));
    }

}
