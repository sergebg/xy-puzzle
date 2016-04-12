package com.xy.puzzle;

public class Cell {

	private final int x;

	private final int y;

	private final int z;

	public Cell(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
		return result;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}

	public void updateHigh(int[] high) {
		if (high[0] < x) high[0] = x;
		if (high[1] < y) high[1] = y;
		if (high[2] < z) high[2] = z;
	}

	public void updateLow(int[] low) {
		if (low[0] > x) low[0] = x;
		if (low[1] > y) low[1] = y;
		if (low[2] > z) low[2] = z;
	}

	public Cell relative(Cell base) {
		return new Cell(x - base.x, y - base.y, z - base.z);
	}

}
