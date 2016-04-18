## Overview

The original problem is fitting of set of wooden figures in 3x3 cube space. 
The library provides means to configure and solve the puzzle.


## Example

Runnable example can be found in `com.xy.puzzle.Demo`.

Available figures configuration:
```java
Figure a = newFigure(newCell(0, 2, 0), newCell(0, 1, 0), newCell(0, 0, 0), newCell(1, 0, 0));
Figure b = newFigure(newCell(0, 1, 0), newCell(0, 0, 0), newCell(0, 0, 1), newCell(1, 0, 1));
Figure c = newFigure(newCell(-1, 0, 0), newCell(0, 0, 0), newCell(1, 0, 0), newCell(0, 1, 0));
Figure d = newFigure(newCell(-1, 0, 0), newCell(0, 0, 0), newCell(1, 0, 0), newCell(0, 0, 1), newCell(0, 1, 1));
Figure e = newFigure(newCell(-1, 0, 0), newCell(0, 0, 0), newCell(1, 0, 0), newCell(0, 1, 0), newCell(1, 0, 1));
Figure f = newFigure(newCell(0, 0, 0), newCell(1, 0, 0), newCell(1, 1, 0), newCell(1, 0, 1), newCell(2, 0, 1));
```

Puzzle definition:
```java
Puzzle puzzle = new Puzzle(Dim.newDim(3));
puzzle.addFigure(a);
puzzle.addFigure(b);
puzzle.addFigure(c);
puzzle.addFigure(d);
puzzle.addFigure(e);
puzzle.addFigure(f);
```

Solution printing:
```java
System.out.println(Cells.print(Dim.newDim(3), puzzle.solve()));
```

