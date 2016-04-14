package com.xy.puzzle;

import java.util.Collection;
import java.util.Set;

import com.google.common.base.Verify;
import com.google.common.collect.ImmutableSet;

public class Placement {

    private final Figure figure;

    private final Set<Cell> rotation;

    private final Position position;

    public Placement(Figure figure, Collection<Cell> rotation, Position position) {
        this.figure = Verify.verifyNotNull(figure);
        this.rotation = ImmutableSet.copyOf(rotation);
        this.position = Verify.verifyNotNull(position);
    }

    public Figure getFigure() {
        return figure;
    }

    public Position getPosition() {
        return position;
    }

    public Set<Cell> getRotation() {
        return rotation;
    }

}
