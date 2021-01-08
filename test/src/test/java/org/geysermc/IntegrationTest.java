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

package org.geysermc;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.auth.service.SessionService;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.data.game.MessageType;
import com.github.steveice10.mc.protocol.data.status.PlayerInfo;
import com.github.steveice10.mc.protocol.data.status.ServerStatusInfo;
import com.github.steveice10.mc.protocol.data.status.VersionInfo;
import com.github.steveice10.mc.protocol.data.status.handler.ServerInfoBuilder;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.packetlib.Server;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.tcp.TcpSessionFactory;
import com.nukkitx.protocol.bedrock.BedrockClient;
import com.nukkitx.protocol.bedrock.packet.ResourcePackClientResponsePacket;
import com.nukkitx.protocol.bedrock.packet.TextPacket;
import net.kyori.adventure.text.Component;
import org.geysermc.common.PlatformType;
import org.geysermc.connector.GeyserConnector;
import org.geysermc.connector.GeyserLogger;
import org.geysermc.connector.bootstrap.GeyserBootstrap;
import org.geysermc.connector.command.CommandManager;
import org.geysermc.connector.configuration.GeyserJacksonConfiguration;
import org.geysermc.connector.network.BedrockProtocol;
import org.geysermc.connector.network.session.GeyserSession;
import org.geysermc.connector.network.session.auth.AuthData;
import org.geysermc.connector.network.session.auth.BedrockClientData;
import org.geysermc.connector.ping.GeyserLegacyPingPassthrough;
import org.geysermc.connector.ping.IGeyserPingPassthrough;
import org.geysermc.mock.*;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IntegrationTest {
    private final ObjectMapper JSON_MAPPER = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    @Test
    public void passFromServerToClient() throws IOException, InterruptedException {
        Server javaServer = startJavaServer();

        javaServer.bind();

        AtomicReference<GeyserSession> session = new AtomicReference<>();

        GeyserConnector connector = startGeyser(session);

        BedrockClient client = startBedrockClient();

        InetSocketAddress connectionAddress = new InetSocketAddress("127.0.0.1", 19132);
        client.connect(connectionAddress).join().setPacketCodec(BedrockProtocol.DEFAULT_BEDROCK_CODEC);
        client.getSession().setLogging(false);

        Thread.sleep(200);

        session.get().setAuthData(new AuthData("TestSession", UUID.randomUUID(), "0"));
        session.get().setClientData(JSON_MAPPER.readValue("{\"LanguageCode\":\"en_us\"}", BedrockClientData.class));

        ResourcePackClientResponsePacket packet1 = new ResourcePackClientResponsePacket();
        packet1.setStatus(ResourcePackClientResponsePacket.Status.COMPLETED);
        client.getSession().sendPacketImmediately(packet1);

        Thread.sleep(200);

        session.get().authenticate("Test");

        Thread.sleep(1000);

        ServerChatPacket packet2 = new ServerChatPacket("Test", MessageType.CHAT, UUID.randomUUID());
        Session testSession = javaServer.getSessions().stream()
                .filter(s -> ((GameProfile) s.getFlag(MinecraftConstants.PROFILE_KEY)).getName().equals("Test"))
                .findFirst()
                .get();

        TestClientHandler testClientHandler = new TestClientHandler();
        client.getSession().setPacketHandler(testClientHandler);
        testSession.send(packet2);

        Thread.sleep(200);

        connector.shutdown();
        javaServer.close();
        client.close();

        assertEquals(testClientHandler.getChatMessage(), Collections.singletonList("Test"));
    }

    @Test
    public void passFromClientToServer() throws IOException, InterruptedException {
        Server javaServer = startJavaServer();

        TestServerAdapter serverAdapter = new TestServerAdapter();

        javaServer.addListener(serverAdapter);

        javaServer.bind();

        AtomicReference<GeyserSession> session = new AtomicReference<>();

        GeyserConnector connector = startGeyser(session);

        BedrockClient client = startBedrockClient();

        InetSocketAddress connectionAddress = new InetSocketAddress("127.0.0.1", 19132);
        client.connect(connectionAddress).join().setPacketCodec(BedrockProtocol.DEFAULT_BEDROCK_CODEC);
        client.getSession().setLogging(false);

        Thread.sleep(200);

        session.get().setAuthData(new AuthData("TestSession", UUID.randomUUID(), "0"));
        session.get().setClientData(JSON_MAPPER.readValue("{\"LanguageCode\":\"en_us\"}", BedrockClientData.class));

        ResourcePackClientResponsePacket packet1 = new ResourcePackClientResponsePacket();
        packet1.setStatus(ResourcePackClientResponsePacket.Status.COMPLETED);
        client.getSession().sendPacketImmediately(packet1);

        Thread.sleep(200);

        session.get().authenticate("Test");

        Thread.sleep(1000);

        TextPacket packet2 = new TextPacket();
        packet2.setMessage("Test");
        packet2.setType(TextPacket.Type.ANNOUNCEMENT);
        packet2.setNeedsTranslation(false);
        packet2.setSourceName("Test");
        packet2.setXuid("0");
        client.getSession().sendPacketImmediately(packet2);

        Thread.sleep(200);

        connector.shutdown();
        javaServer.close();
        client.close();

        assertEquals(serverAdapter.getChatMessage(), Collections.singletonList("Test: Test"));
    }

    @Test
    public void pingPassthrough() throws IOException, InterruptedException {
        Server javaServer = startJavaServer();

        javaServer.setGlobalFlag(MinecraftConstants.SERVER_INFO_BUILDER_KEY, (ServerInfoBuilder) session -> new ServerStatusInfo(
                new VersionInfo(MinecraftConstants.GAME_VERSION, MinecraftConstants.PROTOCOL_VERSION),
                new PlayerInfo(101, 1, new GameProfile[0]),
                Component.text("Test."),
                null
        ));

        javaServer.bind();

        AtomicReference<GeyserSession> session = new AtomicReference<>();

        GeyserConnector connector = startGeyser(session);

        Thread.sleep(1500);

        BedrockClient client = startBedrockClient();

        InetSocketAddress pingAddress = new InetSocketAddress("127.0.0.1", 19132);
        client.ping(pingAddress).whenComplete((bedrockPong, throwable) -> {
            if (bedrockPong == null) {
                throw new IllegalStateException();
            }
            assertEquals(bedrockPong.getMotd(), "Test.");
            assertEquals(bedrockPong.getSubMotd(), "");
            assertEquals(bedrockPong.getPlayerCount(), 1);
            assertEquals(bedrockPong.getMaximumPlayerCount(), 101);
            assertEquals(bedrockPong.getProtocolVersion(), BedrockProtocol.DEFAULT_BEDROCK_CODEC.getProtocolVersion());
        }).join();

        connector.shutdown();
        javaServer.close();
        client.close();
    }

    private Server startJavaServer() {
        SessionService sessionService = new SessionService();
        sessionService.setProxy(Proxy.NO_PROXY);

        Server server = new Server("0.0.0.0", 25565, MinecraftProtocol.class, new TcpSessionFactory());
        server.setGlobalFlag(MinecraftConstants.SESSION_SERVICE_KEY, sessionService);
        server.setGlobalFlag(MinecraftConstants.VERIFY_USERS_KEY, false);

        server.setGlobalFlag(MinecraftConstants.SERVER_COMPRESSION_THRESHOLD, 100);

        return server;
    }

    private GeyserConnector startGeyser(AtomicReference<GeyserSession> session) throws IOException, InterruptedException {
        GeyserJacksonConfiguration configuration = new TestConfiguration();
        configuration.getRemote().setAddress("127.0.0.1");

        CommandManager commandManager = mock(CommandManager.class);
        GeyserLogger logger = new TestLogger();

        GeyserBootstrap bootstrap = mock(GeyserBootstrap.class, CALLS_REAL_METHODS);
        when(bootstrap.getGeyserConfig()).thenReturn(configuration);
        when(bootstrap.getGeyserLogger()).thenReturn(logger);
        when(bootstrap.getGeyserCommandManager()).thenReturn(commandManager);

        Path testPath = Paths.get("testData");
        Files.createDirectories(testPath);
        when(bootstrap.getConfigFolder()).thenReturn(testPath);

        GeyserConnector connector = GeyserConnector.start(PlatformType.STANDALONE, bootstrap);

        while(connector.getMetrics() == null) {
            Thread.sleep(1000);
        }

        IGeyserPingPassthrough pingPassthrough = GeyserLegacyPingPassthrough.init(connector);
        when(connector.getBootstrap().getGeyserPingPassthrough()).thenReturn(pingPassthrough);

        TestServerEventHandler testServerEventHandler = new TestServerEventHandler(connector, session::set);
        connector.getBedrockServer().setHandler(testServerEventHandler);

        return connector;
    }

    private BedrockClient startBedrockClient() {
        InetSocketAddress address = new InetSocketAddress("0.0.0.0", ThreadLocalRandom.current().nextInt(20000, 60000));
        BedrockClient client = new BedrockClient(address);

        client.bind().join();

        return client;
    }
}
