/*
 * MIT License
 *
 * Copyright (c) 2024 Artem Getmanskii
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.ArtemGet.teleroute.route;

import com.github.ArtemGet.teleroute.command.FkCmd;
import com.github.ArtemGet.teleroute.match.FkMatch;
import com.github.ArtemGet.teleroute.send.FkSend;
import com.github.ArtemGet.teleroute.update.FkUpdWrap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ForkRouteTest {

    @Test
    void route_shouldRouteCmd_whenMatchAndNoSpareCmd() {
        Assertions.assertEquals(
                new FkCmd(new FkSend()),
                new ForkRoute<>(
                        new FkMatch(),
                        new FkCmd(new FkSend())
                )
                .route(new FkUpdWrap())
                .get()
        );
    }

    @Test
    void route_shouldRouteEmpty_whenNotMatchAndNoSpareCmd() {
        Assertions.assertTrue(
                new ForkRoute<>(
                        new FkMatch(false),
                        new FkCmd(new FkSend())
                )
                .route(new FkUpdWrap())
                .isEmpty()
        );
    }

    @Test
    void route_shouldRouteCmd_whenMatchAndSpareCmd() {
        Assertions.assertEquals(
                new FkCmd(new FkSend()),
                new ForkRoute<>(
                        new FkMatch(),
                        new FkCmd(new FkSend()),
                        new FkCmd()
                )
                .route(new FkUpdWrap())
                .get()
        );
    }

    @Test
    void route_shouldRouteSpareCmd_whenNotMatchAndSpareCmd() {
        Assertions.assertEquals(
                new FkCmd(),
                new ForkRoute<>(
                        new FkMatch(false),
                        new FkCmd(new FkSend()),
                        new FkCmd()
                )
                .route(new FkUpdWrap())
                .get()
        );
    }

    //routes

    @Test
    void route_shouldRoute_whenMatchAndNoSpareRoute() {
        Assertions.assertEquals(
                new FkCmd(new FkSend()),
                new ForkRoute<>(
                        new FkMatch(),
                        new EndRoute<>(new FkCmd(new FkSend()))
                )
                .route(new FkUpdWrap())
                .get()
        );
    }

    @Test
    void route_shouldRouteEmpty_whenNotMatchAndNoSpareRoute() {
        Assertions.assertTrue(
                new ForkRoute<>(
                        new FkMatch(false),
                        new EndRoute<>(new FkCmd(new FkSend()))
                )
                .route(new FkUpdWrap())
                .isEmpty()
        );
    }

    @Test
    void route_shouldRoute_whenMatchAndSpareRoute() {
        Assertions.assertEquals(
                new FkCmd(new FkSend()),
                new ForkRoute<>(
                        new FkMatch(),
                        new EndRoute<>(new FkCmd(new FkSend())),
                        new EndRoute<>(new FkCmd())
                )
                .route(new FkUpdWrap())
                .get()
        );
    }

    @Test
    void route_shouldRouteSpareRoute_whenNotMatchAndSpareRoute() {
        Assertions.assertEquals(
                new FkCmd(),
                new ForkRoute<>(
                        new FkMatch(false),
                        new EndRoute<>(new FkCmd(new FkSend())),
                        new EndRoute<>(new FkCmd())
                )
                .route(new FkUpdWrap())
                .get()
        );
    }
}
