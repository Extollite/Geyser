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

package org.geysermc.util.performancetest.runnable;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
public class SpigotRunnable implements Runnable {
    private BufferedWriter writer;
    private boolean working = true;

    @Override
    public void run() {
        try {
            Path spigotPath = Paths.get("spigot\\");
            Process proc = Runtime.getRuntime().exec("java -jar spigot-1.16.4.jar nogui", null, spigotPath.toAbsolutePath().toFile());
            working = true;
            writer = new BufferedWriter(new OutputStreamWriter(proc.getOutputStream()));
            new BufferedReader(new InputStreamReader(proc.getInputStream())).lines().forEach(s -> System.out.println("[SPIGOT] " + s));
            proc.waitFor();
            working = false;
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
