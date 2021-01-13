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

package org.geysermc.util.handler;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.bedrock.packet.AddBehaviorTreePacket;
import com.nukkitx.protocol.bedrock.packet.AddEntityPacket;
import com.nukkitx.protocol.bedrock.packet.AddHangingEntityPacket;
import com.nukkitx.protocol.bedrock.packet.AddItemEntityPacket;
import com.nukkitx.protocol.bedrock.packet.AddPaintingPacket;
import com.nukkitx.protocol.bedrock.packet.AddPlayerPacket;
import com.nukkitx.protocol.bedrock.packet.AdventureSettingsPacket;
import com.nukkitx.protocol.bedrock.packet.AnimateEntityPacket;
import com.nukkitx.protocol.bedrock.packet.AnimatePacket;
import com.nukkitx.protocol.bedrock.packet.AnvilDamagePacket;
import com.nukkitx.protocol.bedrock.packet.AutomationClientConnectPacket;
import com.nukkitx.protocol.bedrock.packet.AvailableCommandsPacket;
import com.nukkitx.protocol.bedrock.packet.AvailableEntityIdentifiersPacket;
import com.nukkitx.protocol.bedrock.packet.BiomeDefinitionListPacket;
import com.nukkitx.protocol.bedrock.packet.BlockEntityDataPacket;
import com.nukkitx.protocol.bedrock.packet.BlockEventPacket;
import com.nukkitx.protocol.bedrock.packet.BlockPickRequestPacket;
import com.nukkitx.protocol.bedrock.packet.BookEditPacket;
import com.nukkitx.protocol.bedrock.packet.BossEventPacket;
import com.nukkitx.protocol.bedrock.packet.CameraPacket;
import com.nukkitx.protocol.bedrock.packet.CameraShakePacket;
import com.nukkitx.protocol.bedrock.packet.ChangeDimensionPacket;
import com.nukkitx.protocol.bedrock.packet.ChunkRadiusUpdatedPacket;
import com.nukkitx.protocol.bedrock.packet.ClientCacheBlobStatusPacket;
import com.nukkitx.protocol.bedrock.packet.ClientCacheMissResponsePacket;
import com.nukkitx.protocol.bedrock.packet.ClientCacheStatusPacket;
import com.nukkitx.protocol.bedrock.packet.ClientToServerHandshakePacket;
import com.nukkitx.protocol.bedrock.packet.ClientboundMapItemDataPacket;
import com.nukkitx.protocol.bedrock.packet.CodeBuilderPacket;
import com.nukkitx.protocol.bedrock.packet.CommandBlockUpdatePacket;
import com.nukkitx.protocol.bedrock.packet.CommandOutputPacket;
import com.nukkitx.protocol.bedrock.packet.CommandRequestPacket;
import com.nukkitx.protocol.bedrock.packet.CompletedUsingItemPacket;
import com.nukkitx.protocol.bedrock.packet.ContainerClosePacket;
import com.nukkitx.protocol.bedrock.packet.ContainerOpenPacket;
import com.nukkitx.protocol.bedrock.packet.ContainerSetDataPacket;
import com.nukkitx.protocol.bedrock.packet.CorrectPlayerMovePredictionPacket;
import com.nukkitx.protocol.bedrock.packet.CraftingDataPacket;
import com.nukkitx.protocol.bedrock.packet.CraftingEventPacket;
import com.nukkitx.protocol.bedrock.packet.CreativeContentPacket;
import com.nukkitx.protocol.bedrock.packet.DebugInfoPacket;
import com.nukkitx.protocol.bedrock.packet.DisconnectPacket;
import com.nukkitx.protocol.bedrock.packet.EducationSettingsPacket;
import com.nukkitx.protocol.bedrock.packet.EmoteListPacket;
import com.nukkitx.protocol.bedrock.packet.EmotePacket;
import com.nukkitx.protocol.bedrock.packet.EntityEventPacket;
import com.nukkitx.protocol.bedrock.packet.EntityPickRequestPacket;
import com.nukkitx.protocol.bedrock.packet.EventPacket;
import com.nukkitx.protocol.bedrock.packet.ExplodePacket;
import com.nukkitx.protocol.bedrock.packet.FilterTextPacket;
import com.nukkitx.protocol.bedrock.packet.GameRulesChangedPacket;
import com.nukkitx.protocol.bedrock.packet.GuiDataPickItemPacket;
import com.nukkitx.protocol.bedrock.packet.HurtArmorPacket;
import com.nukkitx.protocol.bedrock.packet.InteractPacket;
import com.nukkitx.protocol.bedrock.packet.InventoryContentPacket;
import com.nukkitx.protocol.bedrock.packet.InventorySlotPacket;
import com.nukkitx.protocol.bedrock.packet.InventoryTransactionPacket;
import com.nukkitx.protocol.bedrock.packet.ItemComponentPacket;
import com.nukkitx.protocol.bedrock.packet.ItemFrameDropItemPacket;
import com.nukkitx.protocol.bedrock.packet.ItemStackRequestPacket;
import com.nukkitx.protocol.bedrock.packet.ItemStackResponsePacket;
import com.nukkitx.protocol.bedrock.packet.LabTablePacket;
import com.nukkitx.protocol.bedrock.packet.LecternUpdatePacket;
import com.nukkitx.protocol.bedrock.packet.LevelChunkPacket;
import com.nukkitx.protocol.bedrock.packet.LevelEventGenericPacket;
import com.nukkitx.protocol.bedrock.packet.LevelEventPacket;
import com.nukkitx.protocol.bedrock.packet.LevelSoundEvent1Packet;
import com.nukkitx.protocol.bedrock.packet.LevelSoundEvent2Packet;
import com.nukkitx.protocol.bedrock.packet.LevelSoundEventPacket;
import com.nukkitx.protocol.bedrock.packet.LoginPacket;
import com.nukkitx.protocol.bedrock.packet.MapCreateLockedCopyPacket;
import com.nukkitx.protocol.bedrock.packet.MapInfoRequestPacket;
import com.nukkitx.protocol.bedrock.packet.MobArmorEquipmentPacket;
import com.nukkitx.protocol.bedrock.packet.MobEffectPacket;
import com.nukkitx.protocol.bedrock.packet.MobEquipmentPacket;
import com.nukkitx.protocol.bedrock.packet.ModalFormRequestPacket;
import com.nukkitx.protocol.bedrock.packet.ModalFormResponsePacket;
import com.nukkitx.protocol.bedrock.packet.MotionPredictionHintsPacket;
import com.nukkitx.protocol.bedrock.packet.MoveEntityAbsolutePacket;
import com.nukkitx.protocol.bedrock.packet.MoveEntityDeltaPacket;
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket;
import com.nukkitx.protocol.bedrock.packet.MultiplayerSettingsPacket;
import com.nukkitx.protocol.bedrock.packet.NetworkChunkPublisherUpdatePacket;
import com.nukkitx.protocol.bedrock.packet.NetworkSettingsPacket;
import com.nukkitx.protocol.bedrock.packet.NetworkStackLatencyPacket;
import com.nukkitx.protocol.bedrock.packet.NpcRequestPacket;
import com.nukkitx.protocol.bedrock.packet.OnScreenTextureAnimationPacket;
import com.nukkitx.protocol.bedrock.packet.PacketViolationWarningPacket;
import com.nukkitx.protocol.bedrock.packet.PhotoTransferPacket;
import com.nukkitx.protocol.bedrock.packet.PlaySoundPacket;
import com.nukkitx.protocol.bedrock.packet.PlayStatusPacket;
import com.nukkitx.protocol.bedrock.packet.PlayerActionPacket;
import com.nukkitx.protocol.bedrock.packet.PlayerArmorDamagePacket;
import com.nukkitx.protocol.bedrock.packet.PlayerAuthInputPacket;
import com.nukkitx.protocol.bedrock.packet.PlayerEnchantOptionsPacket;
import com.nukkitx.protocol.bedrock.packet.PlayerFogPacket;
import com.nukkitx.protocol.bedrock.packet.PlayerHotbarPacket;
import com.nukkitx.protocol.bedrock.packet.PlayerInputPacket;
import com.nukkitx.protocol.bedrock.packet.PlayerListPacket;
import com.nukkitx.protocol.bedrock.packet.PlayerSkinPacket;
import com.nukkitx.protocol.bedrock.packet.PositionTrackingDBClientRequestPacket;
import com.nukkitx.protocol.bedrock.packet.PositionTrackingDBServerBroadcastPacket;
import com.nukkitx.protocol.bedrock.packet.PurchaseReceiptPacket;
import com.nukkitx.protocol.bedrock.packet.RemoveEntityPacket;
import com.nukkitx.protocol.bedrock.packet.RemoveObjectivePacket;
import com.nukkitx.protocol.bedrock.packet.RequestChunkRadiusPacket;
import com.nukkitx.protocol.bedrock.packet.ResourcePackChunkDataPacket;
import com.nukkitx.protocol.bedrock.packet.ResourcePackChunkRequestPacket;
import com.nukkitx.protocol.bedrock.packet.ResourcePackClientResponsePacket;
import com.nukkitx.protocol.bedrock.packet.ResourcePackDataInfoPacket;
import com.nukkitx.protocol.bedrock.packet.ResourcePackStackPacket;
import com.nukkitx.protocol.bedrock.packet.ResourcePacksInfoPacket;
import com.nukkitx.protocol.bedrock.packet.RespawnPacket;
import com.nukkitx.protocol.bedrock.packet.RiderJumpPacket;
import com.nukkitx.protocol.bedrock.packet.ScriptCustomEventPacket;
import com.nukkitx.protocol.bedrock.packet.ServerSettingsRequestPacket;
import com.nukkitx.protocol.bedrock.packet.ServerSettingsResponsePacket;
import com.nukkitx.protocol.bedrock.packet.ServerToClientHandshakePacket;
import com.nukkitx.protocol.bedrock.packet.SetCommandsEnabledPacket;
import com.nukkitx.protocol.bedrock.packet.SetDefaultGameTypePacket;
import com.nukkitx.protocol.bedrock.packet.SetDifficultyPacket;
import com.nukkitx.protocol.bedrock.packet.SetDisplayObjectivePacket;
import com.nukkitx.protocol.bedrock.packet.SetEntityDataPacket;
import com.nukkitx.protocol.bedrock.packet.SetEntityLinkPacket;
import com.nukkitx.protocol.bedrock.packet.SetEntityMotionPacket;
import com.nukkitx.protocol.bedrock.packet.SetHealthPacket;
import com.nukkitx.protocol.bedrock.packet.SetLastHurtByPacket;
import com.nukkitx.protocol.bedrock.packet.SetLocalPlayerAsInitializedPacket;
import com.nukkitx.protocol.bedrock.packet.SetPlayerGameTypePacket;
import com.nukkitx.protocol.bedrock.packet.SetScorePacket;
import com.nukkitx.protocol.bedrock.packet.SetScoreboardIdentityPacket;
import com.nukkitx.protocol.bedrock.packet.SetSpawnPositionPacket;
import com.nukkitx.protocol.bedrock.packet.SetTimePacket;
import com.nukkitx.protocol.bedrock.packet.SetTitlePacket;
import com.nukkitx.protocol.bedrock.packet.SettingsCommandPacket;
import com.nukkitx.protocol.bedrock.packet.ShowCreditsPacket;
import com.nukkitx.protocol.bedrock.packet.ShowProfilePacket;
import com.nukkitx.protocol.bedrock.packet.ShowStoreOfferPacket;
import com.nukkitx.protocol.bedrock.packet.SimpleEventPacket;
import com.nukkitx.protocol.bedrock.packet.SpawnExperienceOrbPacket;
import com.nukkitx.protocol.bedrock.packet.SpawnParticleEffectPacket;
import com.nukkitx.protocol.bedrock.packet.StartGamePacket;
import com.nukkitx.protocol.bedrock.packet.StopSoundPacket;
import com.nukkitx.protocol.bedrock.packet.StructureBlockUpdatePacket;
import com.nukkitx.protocol.bedrock.packet.StructureTemplateDataRequestPacket;
import com.nukkitx.protocol.bedrock.packet.StructureTemplateDataResponsePacket;
import com.nukkitx.protocol.bedrock.packet.SubClientLoginPacket;
import com.nukkitx.protocol.bedrock.packet.TakeItemEntityPacket;
import com.nukkitx.protocol.bedrock.packet.TextPacket;
import com.nukkitx.protocol.bedrock.packet.TickSyncPacket;
import com.nukkitx.protocol.bedrock.packet.TransferPacket;
import com.nukkitx.protocol.bedrock.packet.UpdateAttributesPacket;
import com.nukkitx.protocol.bedrock.packet.UpdateBlockPacket;
import com.nukkitx.protocol.bedrock.packet.UpdateBlockPropertiesPacket;
import com.nukkitx.protocol.bedrock.packet.UpdateBlockSyncedPacket;
import com.nukkitx.protocol.bedrock.packet.UpdateEquipPacket;
import com.nukkitx.protocol.bedrock.packet.UpdatePlayerGameTypePacket;
import com.nukkitx.protocol.bedrock.packet.UpdateSoftEnumPacket;
import com.nukkitx.protocol.bedrock.packet.UpdateTradePacket;
import com.nukkitx.protocol.bedrock.packet.VideoStreamConnectPacket;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestServerPacketHandler implements BedrockPacketHandler {
    private boolean lastReceived = false;

    public TestServerPacketHandler() {
    }

    boolean defaultHandler(BedrockPacket packet) {
        if ((packet instanceof TextPacket)) {
            if ("End".equals(((TextPacket) packet).getMessage())) {
                lastReceived = true;
            } else if ("Start".equals(((TextPacket) packet).getMessage())) {
                lastReceived = false;
            }
        }
        return true;
    }

    @Override
    public boolean handle(LoginPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ResourcePackClientResponsePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(AdventureSettingsPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(AnimatePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(BlockEntityDataPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(BlockPickRequestPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(BookEditPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ClientCacheBlobStatusPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ClientCacheMissResponsePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ClientCacheStatusPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ClientToServerHandshakePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(CommandBlockUpdatePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(CommandRequestPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ContainerClosePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(CraftingEventPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(EntityEventPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(EntityPickRequestPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(EventPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(InteractPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(InventoryContentPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(InventorySlotPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(InventoryTransactionPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ItemFrameDropItemPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(LabTablePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(LecternUpdatePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(LevelEventGenericPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(LevelSoundEventPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(MapInfoRequestPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(MobArmorEquipmentPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(MobEquipmentPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ModalFormResponsePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(MoveEntityAbsolutePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(MovePlayerPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(NetworkStackLatencyPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(PhotoTransferPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(PlayerActionPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(PlayerHotbarPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(PlayerInputPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(PlayerSkinPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(PurchaseReceiptPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(RequestChunkRadiusPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ResourcePackChunkRequestPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(RiderJumpPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ServerSettingsRequestPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(SetDefaultGameTypePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(SetLocalPlayerAsInitializedPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(SetPlayerGameTypePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(SubClientLoginPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(TextPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(AddBehaviorTreePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(AddEntityPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(AddHangingEntityPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(AddItemEntityPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(AddPaintingPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(AddPlayerPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(AvailableCommandsPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(BlockEventPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(BossEventPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(CameraPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ChangeDimensionPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ChunkRadiusUpdatedPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ClientboundMapItemDataPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(CommandOutputPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ContainerOpenPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ContainerSetDataPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(CraftingDataPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(DisconnectPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ExplodePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(LevelChunkPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(GameRulesChangedPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(GuiDataPickItemPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(HurtArmorPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(AutomationClientConnectPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(LevelEventPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(MapCreateLockedCopyPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(MobEffectPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ModalFormRequestPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(MoveEntityDeltaPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(NpcRequestPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(OnScreenTextureAnimationPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(PlayerListPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(PlaySoundPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(PlayStatusPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(RemoveEntityPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(RemoveObjectivePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ResourcePackChunkDataPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ResourcePackDataInfoPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ResourcePacksInfoPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ResourcePackStackPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(RespawnPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ScriptCustomEventPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ServerSettingsResponsePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ServerToClientHandshakePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(SetCommandsEnabledPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(SetDifficultyPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(SetDisplayObjectivePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(SetEntityDataPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(SetEntityLinkPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(SetEntityMotionPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(SetHealthPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(SetLastHurtByPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(SetScoreboardIdentityPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(SetScorePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(SetSpawnPositionPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(SetTimePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(SetTitlePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ShowCreditsPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ShowProfilePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ShowStoreOfferPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(SimpleEventPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(SpawnExperienceOrbPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(StartGamePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(StopSoundPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(StructureBlockUpdatePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(StructureTemplateDataRequestPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(StructureTemplateDataResponsePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(TakeItemEntityPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(TransferPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(UpdateAttributesPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(UpdateBlockPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(UpdateBlockPropertiesPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(UpdateBlockSyncedPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(UpdateEquipPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(UpdateSoftEnumPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(UpdateTradePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(AvailableEntityIdentifiersPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(BiomeDefinitionListPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(LevelSoundEvent2Packet packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(NetworkChunkPublisherUpdatePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(SpawnParticleEffectPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(VideoStreamConnectPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(EmotePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(TickSyncPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(AnvilDamagePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(NetworkSettingsPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(PlayerAuthInputPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(SettingsCommandPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(EducationSettingsPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(CompletedUsingItemPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(MultiplayerSettingsPacket packet) {
        return defaultHandler(packet);
    }

    // 1.16 new packets

    @Override
    public boolean handle(DebugInfoPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(EmoteListPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(CodeBuilderPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(CreativeContentPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ItemStackRequestPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(LevelSoundEvent1Packet packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ItemStackResponsePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(PlayerArmorDamagePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(PlayerEnchantOptionsPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(UpdatePlayerGameTypePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(PacketViolationWarningPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(PositionTrackingDBClientRequestPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(PositionTrackingDBServerBroadcastPacket packet) {
        return defaultHandler(packet);
    }

    // 1.16.100 new packets

    @Override
    public boolean handle(MotionPredictionHintsPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(AnimateEntityPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(CameraShakePacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(PlayerFogPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(CorrectPlayerMovePredictionPacket packet) {
        return defaultHandler(packet);
    }

    @Override
    public boolean handle(ItemComponentPacket packet) {
        return defaultHandler(packet);
    }

    // 1.16.200 new packet

    @Override
    public boolean handle(FilterTextPacket packet) {
        return defaultHandler(packet);
    }
}

