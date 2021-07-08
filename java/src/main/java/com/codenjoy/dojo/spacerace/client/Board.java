package com.codenjoy.dojo.spacerace.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.codenjoy.dojo.spacerace.model.Element;

import lombok.Getter;

public class Board {
    private Map<Point, Element> boardAsDictionary = null;
    @Getter
    private String boardString;
    @Getter
    private int size;

    public Board(String boardString) {
        this.boardString = boardString.replace("\n", "");
        size = (int) Math.sqrt(boardString.length());
    }

    /// Transform game board to dictionary Point -> Element. Useable to LINQ and
    public Map<Point, Element> getAllExtend() {
        if (boardAsDictionary == null) {
            boardAsDictionary = new HashMap<Point, Element>();
            for (int j = 0; j < boardString.length(); j++) {
                boardAsDictionary.put(getPointByShift(j), Element.valueOf(boardString.charAt(j)));
            }
        }

        return boardAsDictionary;
    }

    /// Returns the list of points for the given element type.
    public List<Point> findAll(Element... elements) {
        return enumeratePoints(new HashSet<Element>(Arrays.asList(elements)));
    }

    /// Returns the point where your hero is.
    public Point getMyPosition() {
        List<Point> list = enumeratePoints(Element.HERO);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /// Returns a Element object at coordinates x,y.
    public Element getAt(int x, int y) {
        return Element.valueOf(boardString.charAt(getShiftByPoint(x, y)));
    }

    /// Returns True if Element is at x,y coordinates.
    public boolean hasElementAt(int x, int y, Element element) {
        if (isOutOfBoard(x, y)) {
            return false;
        }

        return getAt(x, y) == element;
    }

    public boolean hasElementAt(Point p, Element element) {
        return hasElementAt(p.getX(), p.getY(), element);
    }

    /// Returns true if barrier is at x,y.
    public boolean isBarrierAt(int x, int y) {
        return hasElementAt(x, y, Element.WALL);
    }

    /// Returns False if your hero still alive.
    public boolean isHeroDead() {
        return boardString.contains(Element.DEAD_HERO.toString());
    }

    /// Return the list of points for other heroes.
    public List<Point> GetEnemyPositions() {
        return enumeratePoints(Element.OTHER_HERO);
    }

    /// Returns the list of points for other heroes.
    public List<Point> GetOtherHeroPositions() {
        return enumeratePoints(Element.OTHER_HERO);
    }

    /// <summary>
    /// Returns the list of walls element Points.
    /// </summary>
    /// <returns></returns>
    public List<Point> getWallPositions() {
        return enumeratePoints(Element.WALL);
    }

    /// <summary>
    /// Returns the list of barriers Points.
    /// </summary>
    /// <returns></returns>
    public List<Point> GetBarriers() {
        return enumeratePoints(Element.WALL);
    }

    /// Check if near exists element of chosen type.
    public boolean IsNearToElement(int x, int y, Element element) {
        if (isOutOfBoard(x, y))
            return false;

        return EnumerateNeighbors(new Point(x, y)).stream()
                .anyMatch(neighbor -> hasElementAt(neighbor, element));
    }

    /// Returns True if enemy exists in current point.
    public boolean HasEnemyAt(int x, int y) {
        return hasElementAt(x, y, Element.OTHER_HERO);
    }

    /// Returns True if other hero exists in current point.
    public boolean HasOtherHeroAt(int x, int y) {
        return hasElementAt(x, y, Element.OTHER_HERO);
    }

    /// Returns True if wall exists in current point.
    public boolean HasWallAt(int x, int y) {
        return hasElementAt(x, y, Element.WALL);
    }

    /// Counts the number of occurrences of element nearby.
    public long GetCountElementsNearToPoint(int x, int y, Element element) {
        if (isOutOfBoard(x, y))
            return 0;

        return EnumerateNeighbors(new Point(x, y)).stream()
                .filter(neighbor -> hasElementAt(neighbor, element)).count();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Board:\n");
        for (int i = 0; i < size; i++) {
            sb.append(boardString.substring(i * size, i * size + size));
            sb.append("\n");
        }

        return sb.toString();
    }

    private List<Point> EnumerateNeighbors(Point point) {

        List<Point> list = new ArrayList<>();
        list.add(point.shiftLeft());
        return list;
    }

    private List<Point> enumeratePoints(Element element) {
        return IntStream
                .range(0, boardString.length())
                .filter(i -> element == Element.valueOf(boardString.charAt(i)))
                .mapToObj(e -> getPointByShift(e))
                .collect(Collectors.toList());
    }

    private List<Point> enumeratePoints(HashSet<Element> elements) {
        return IntStream
                .range(0, boardString.length())
                .filter(i -> elements.contains(Element.valueOf(boardString.charAt(i))))
                .mapToObj(e -> getPointByShift(e))
                .collect(Collectors.toList());
    }

    protected boolean hasElementAt(int x, int y, List<Element> elements) {
        return elements.stream().anyMatch(elem -> hasElementAt(x, y, elem));
    }

    protected boolean hasElementAt(Point point, List<Element> elements) {
        return elements.stream().anyMatch(elem -> hasElementAt(point.getX(), point.getY(), elem));
    }

    private int getShiftByPoint(int x, int y) {
        return y * size + x;
    }

    private Point getPointByShift(int shift) {
        return new Point(shift % size, shift / size);
    }

    private boolean isOutOfBoard(int x, int y) {
        return (x >= size || y >= size || x < 0 || y < 0);
    }

}
