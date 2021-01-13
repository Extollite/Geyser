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

package org.geysermc.util.adapter;

import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.packetlib.event.server.ServerAdapter;
import com.github.steveice10.packetlib.event.server.SessionAddedEvent;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerformanceServerAdapter extends ServerAdapter {
    private boolean lastReceived = false;

    @Override
    public void sessionAdded(SessionAddedEvent event) {
        event.getSession().addListener(new SessionAdapter() {
            @Override
            public void packetReceived(PacketReceivedEvent event) {
                if (event.getPacket() instanceof ClientChatPacket) {
                    switch (((ClientChatPacket) event.getPacket()).getMessage()) {
                        case "End":
                            lastReceived = true;
                            break;
                        case "Start":
                            lastReceived = false;
                        default:
                            break;
                    }
                }
            }
        });
    }
}
