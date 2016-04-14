package com.xy.puzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class FigureTest {

    @Test
    public void test() {
        List<Cell> cells = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int x = random.nextInt(100);
            int y = random.nextInt(100);
            int z = random.nextInt(100);
            cells.add(Cell.newCell(x, y, z));
        }
        Set<Figure> figures = new HashSet<>();
        Figure figure = Figure.newFigure(cells);
        figures.add(figure);
        for (int i = 0; i < 10; i++) {
            Collections.shuffle(cells);
            Figure clone = Figure.newFigure(cells);
            Assert.assertEquals(figure.hashCode(), clone.hashCode());
            Assert.assertEquals(figure, clone);
            Assert.assertTrue(figures.contains(clone));
        }
    }

}
