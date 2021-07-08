package com.codenjoy.dojo.spacerace.client;


/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Каждый объект на поле имеет свои координаты. Этот класс обычно используется
 * дял указания координат или как родитель. Может использоваться в коллекциях.
 */
@Getter
@Setter
@AllArgsConstructor
public class Point implements Comparable<Point> {

    protected final int x;
    protected final int y;

    public Point() {
        this(-1, -1);
    }

    public Point(Point point) {
        this(point.getX(), point.getY());
    }

    public boolean itsMe(Point pt) {
        return itsMe(pt.getX(), pt.getY());
    }

    public boolean itsMe(int x, int y) {
        return this.x == x && this.y == y;
    }

    public boolean isOutOf(int size) {
        return isOutOf(0, 0, size);
    }

    public boolean isOutOf(int dw, int dh, int size) {
        return x < dw || y < dh || y > size - 1 - dh || x > size - 1 - dw;
    }

    public double distance(Point other) {
        int dx = x - other.getX();
        int dy = y - other.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public int hashCode() {
        return x * 1000 + y;
    }

    public int parentHashCode() {
        return super.hashCode();
    }

    public boolean parentEquals(Object o) {
        return super.equals(o);
    }

    public String toString() {
        return String.format("[%s,%s]", x, y);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        try {
            return ((Point) o).itsMe(x, y);
        } catch (Exception e) {
            return false;
        }
    }

    public Point copy() {
        return new Point(this);
    }

    public static Point pt(int x, int y) {
        return new Point(x, y);
    }

    @Override
    public int compareTo(Point o) {
        if (o == null) {
            return -1;
        }
        return Integer.compare(this.hashCode(), o.hashCode());
    }

    public Point relative(Point offset) {
        return pt(x - offset.getX(), y - offset.getY());
    }

    /// Returns new Point object shifted left to "delta" points
    public Point shiftLeft() {
        return new Point(x - 1, y);
    }

    /// Returns new Point object shifted right to "delta" points
    public Point shiftRight() {
        return new Point(x + 1, y);
    }

    /// Returns new Point object shifted top "delta" points
    public Point shiftTop() {
        return new Point(x, y - 1);
    }

    /// Returns new Point object shifted bottom "delta" points
    public Point shiftBottom() {
        return new Point(x, y + 1);
    }
}
