/*
 * Copyright (c) 2019-2021 GeyserMC. http://geysermc.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @author GeyserMC
 * @link https://github.com/GeyserMC/Geyser
 */

package org.geysermc.util.common.mock;

import org.geysermc.connector.GeyserLogger;
import org.geysermc.connector.command.CommandSender;
import org.geysermc.connector.common.ChatColor;

public class TestLogger implements GeyserLogger, CommandSender {
    private final boolean colored = true;

    public static String printConsole(String message, boolean colors) {
        return colors ? ChatColor.toANSI(message + ChatColor.RESET) : ChatColor.stripColors(message + ChatColor.RESET);
    }

    @Override
    public void severe(String message) {
        System.out.println(printConsole(ChatColor.DARK_RED + message, colored));
    }

    @Override
    public void severe(String message, Throwable error) {
        System.out.printf(printConsole(ChatColor.DARK_RED + message, colored), error);
    }

    @Override
    public void error(String message) {
        System.err.println(printConsole(ChatColor.RED + message, colored));
    }

    @Override
    public void error(String message, Throwable error) {
        System.err.printf(printConsole(ChatColor.RED + message, colored), error);
    }

    @Override
    public void warning(String message) {
        System.err.println(printConsole(ChatColor.YELLOW + message, colored));
    }

    @Override
    public void info(String message) {
        //System.err.println(printConsole(ChatColor.RESET + ChatColor.BOLD + message, colored));
    }

    @Override
    public void debug(String message) {
        if (isDebug())
            System.err.println(printConsole(ChatColor.GRAY + message, colored));
    }

    public boolean isDebug() {
        return false;
    }

    @Override
    public void setDebug(boolean debug) {
        //NOOP
    }

    @Override
    public String getName() {
        return "CONSOLE";
    }

    @Override
    public void sendMessage(String message) {
        info(message);
    }

    @Override
    public boolean isConsole() {
        return true;
    }
}