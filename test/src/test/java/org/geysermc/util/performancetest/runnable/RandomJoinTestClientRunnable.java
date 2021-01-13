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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nukkitx.protocol.bedrock.BedrockClient;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.ResourcePackClientResponsePacket;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.geysermc.connector.network.BedrockProtocol;
import org.geysermc.connector.network.session.GeyserSession;
import org.geysermc.connector.network.session.auth.AuthData;
import org.geysermc.connector.network.session.auth.BedrockClientData;
import org.geysermc.util.common.helper.BigDecimalResult;

import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.UUID;

import static org.geysermc.util.common.helper.TestHelper.randomJoinTime;
import static org.geysermc.util.common.helper.TestHelper.startBedrockClient;

@AllArgsConstructor
public class RandomJoinTestClientRunnable implements Runnable {
    private final ObjectMapper JSON_MAPPER = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    private final BigDecimalResult threadTime;
    private final Map<BedrockPacket, Long> clientPackets;
    private final Map<Integer, GeyserSession> sessions;

    @SneakyThrows
    @Override
    public void run() {
        Thread.sleep(randomJoinTime());

        BedrockClient client = startBedrockClient();

        InetSocketAddress connectionAddress = new InetSocketAddress("127.0.0.1", 19132);
        client.connect(connectionAddress).join().setPacketCodec(BedrockProtocol.DEFAULT_BEDROCK_CODEC);

        int port = client.getBindAddress().getPort();

        while (!sessions.containsKey(port)) {
            Thread.sleep(10);
        }

        GeyserSession session = sessions.get(port);

        session.setAuthData(new AuthData("TestSession", UUID.randomUUID(), String.valueOf(port)));
        session.setClientData(JSON_MAPPER.readValue("{\"LanguageCode\":\"en_us\", \"DeviceOS\": \"ANDROID\"}", BedrockClientData.class));

        ResourcePackClientResponsePacket packet1 = new ResourcePackClientResponsePacket();
        packet1.setStatus(ResourcePackClientResponsePacket.Status.COMPLETED);
        client.getSession().sendPacket(packet1);

        while (session.getRemoteServer() == null) {
            Thread.sleep(10);
        }

        session.authenticate("Test" + port);

        Thread.sleep(100);

        long start = System.nanoTime();

        for (Map.Entry<BedrockPacket, Long> entry : clientPackets.entrySet()) {
            client.getSession().sendPacket(entry.getKey());

            Thread.sleep(entry.getValue());
        }

        while (!session.isClosed()) {
            Thread.sleep(0, 100);
        }

        long end = System.nanoTime();

        client.close();

        while (!client.getRakNet().isClosed()) {
            Thread.sleep(10);
        }

        this.threadTime.setResultTime(BigDecimal.valueOf(end).subtract(BigDecimal.valueOf(start)));
    }
}
