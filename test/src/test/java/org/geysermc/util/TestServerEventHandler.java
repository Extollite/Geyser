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

package org.geysermc.util;

import com.nukkitx.protocol.bedrock.BedrockServerSession;
import org.geysermc.connector.GeyserConnector;
import org.geysermc.connector.network.BedrockProtocol;
import org.geysermc.connector.network.ConnectorServerEventHandler;
import org.geysermc.connector.network.UpstreamPacketHandler;
import org.geysermc.connector.network.session.GeyserSession;

import java.util.function.Consumer;

public class TestServerEventHandler extends ConnectorServerEventHandler {

    private final Consumer<GeyserSession> sessionConsumer;
    private final GeyserConnector connector;

    public TestServerEventHandler(GeyserConnector connector, Consumer<GeyserSession> sessionConsumer) {
        super(connector);
        this.sessionConsumer = sessionConsumer;
        this.connector = connector;
    }

    @Override
    public void onSessionCreation(BedrockServerSession bedrockServerSession) {
        bedrockServerSession.setLogging(true);
        GeyserSession session = new GeyserSession(connector, bedrockServerSession);
        bedrockServerSession.setPacketHandler(new UpstreamPacketHandler(this. connector, session));
        sessionConsumer.accept(session);
        // Set the packet codec to default just in case we need to send disconnect packets.
        bedrockServerSession.setPacketCodec(BedrockProtocol.DEFAULT_BEDROCK_CODEC);
    }

}
