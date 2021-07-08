package com.codenjoy.dojo.spacerace.client;

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

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.codenjoy.dojo.client.Direction;

public class SolverTest {

    private YourSolver ai;

    @Before
    public void setup() {
        ai = new YourSolver();
    }

    private Board board(String board) {
        return (Board) new Board().forString(board);
    }

    @Test
    public void shouldUP() {
        assertA("☼   ☼" +
                "☼ 7 ☼" +
                "☼   ☼" +
                "☼ ☺ ☼" +
                "☼   ☼"
                , Direction.STOP);
    }

    @Test
    public void shouldRight() {
        assertA("☼   ☼" +
                "☼☺ 7☼" +
                "☼   ☼" +
                "☼   ☼" +
                "☼   ☼"
                , Direction.STOP);
    }

    @Test
    public void shouldLeft() {
        assertA("☼   ☼" +
                "☼7 ☺☼" +
                "☼   ☼" +
                "☼   ☼" +
                "☼   ☼"
                , Direction.STOP);
    }

    @Test
    public void shouldDown() {
        assertA("☼   ☼" +
                "☼  ☺☼" +
                "☼   ☼" +
                "☼  7☼" +
                "☼   ☼"
                , Direction.STOP);
    }

    private void assertA(String board, Direction expected) {
        String actual = ai.get(board(board));
        assertEquals(expected.toString(), actual);
    }

}
