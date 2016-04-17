package com.xy.puzzle;

import static com.xy.puzzle.Cell.newCell;
import static com.xy.puzzle.Figure.newFigure;

import java.util.List;

public class Demo {

    public static void main(String[] args) {
        
        Figure a = newFigure(newCell(0, 2, 0), newCell(0, 1, 0), newCell(0, 0, 0), newCell(1, 0, 0));
        Figure b = newFigure(newCell(0, 1, 0), newCell(0, 0, 0), newCell(0, 0, 1), newCell(1, 0, 1));
        Figure c = newFigure(newCell(-1, 0, 0), newCell(0, 0, 0), newCell(1, 0, 0), newCell(0, 1, 0));
        Figure d = newFigure(newCell(-1, 0, 0), newCell(0, 0, 0), newCell(1, 0, 0), newCell(0, 0, 1), newCell(0, 1, 1));
        Figure e = newFigure(newCell(-1, 0, 0), newCell(0, 0, 0), newCell(1, 0, 0), newCell(0, 1, 0), newCell(1, 0, 1));
        Figure f = newFigure(newCell(0, 0, 0), newCell(1, 0, 0), newCell(1, 1, 0), newCell(1, 0, 1), newCell(2, 0, 1));
        
        Puzzle puzzle = new Puzzle(Dim.newDim(3));
        puzzle.addFigure(a);
        puzzle.addFigure(b);
        puzzle.addFigure(c);
        puzzle.addFigure(d);
        puzzle.addFigure(e);
        puzzle.addFigure(f);
        
        System.out.println("found combination:\n" + Cells.print(Dim.newDim(3), puzzle.solve()));
    }

}
