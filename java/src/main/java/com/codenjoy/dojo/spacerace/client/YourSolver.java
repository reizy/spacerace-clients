package com.codenjoy.dojo.spacerace.client;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 - 2021 Codenjoy
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

import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.client.WebSocketRunner;
import com.codenjoy.dojo.spacerace.model.Element;

/**
 * User: your name Это твой алгоритм AI для игры. Реализуй его на свое
 * усмотрение. Обрати внимание на {@see YourSolverTest} - там приготовлен
 * тестовый фреймворк для тебя.
 */
public class YourSolver implements Solver {

    private Logger logger = new Logger();

    @Override
    public String get(Board board) {
        // TODO: Implement your logic here

        // board examples
        logger.log("Board size: " + board.getSize());
        Map<Point, Element> allExtend = board.getAllExtend();
        logger.log("Other heroes on coordinates: ");

        String otherHeroesString = allExtend.entrySet().stream()
                .filter(extendedPoint -> extendedPoint.getValue() == Element.OTHER_HERO)
                .map(Entry::getKey)
                .map(p -> "{X:" + p.x + ", Y:" + p.y + "}")
                .collect(Collectors.joining(","));

        logger.log("Other heroes on coordinates: " + otherHeroesString);

        List<Point> bombsAndStones = board.findAll(Element.BOMB, Element.STONE);
        logger.log("Bombs and stones number: " + bombsAndStones.size());

        List<Point> me = board.findAll(Element.HERO);
        if (me.size() > 0) {
            Point mePoint = me.get(0);
            logger.log("Me on coordinates: {X:" + mePoint.getX() + ", Y:" + mePoint.getY() + " }");
            Point meIfMoveLeft = Direction.LEFT.change(mePoint); // point left to the hero
            logger.log("Left of Me on coordinates: {X:" + meIfMoveLeft.getX() + ", Y:" + meIfMoveLeft.getY() + " }");
        }

        // direction examples
        Random random = new Random();
        Direction[] movements = new Direction[] { Direction.LEFT, Direction.RIGHT, Direction.DOWN, Direction.UP };
        Direction action = movements[random.nextInt(4)];

        if (random.nextInt(100) > 50) {
            return action.ACT(false);
        } else {
            return action.toString();
        }

    }

    public static void main(String[] args) {
        WebSocketRunner.runClient(args,
                // paste here board page url from browser after registration
                // or put it as command line parameter
                "https://dojorena.io/codenjoy-contest/board/player/dojorena99?code=1234567890",
                new YourSolver());
    }
}
