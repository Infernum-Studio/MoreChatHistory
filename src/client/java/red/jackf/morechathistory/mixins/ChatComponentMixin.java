package red.jackf.morechathistory.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.util.ArrayListDeque;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatComponent.class)
public class ChatComponentMixin {
	private static final int EXPANDED_MAX_HISTORY = 16384;

	@WrapOperation(
			method = "<init>",
			at = @At(value = "NEW", args = "class=net/minecraft/util/ArrayListDeque")
	)
	private ArrayListDeque<String> expandQueue(int capacity, Operation<ArrayListDeque<String>> original) {
		return original.call(EXPANDED_MAX_HISTORY);
	}

	@ModifyExpressionValue(
			method = {"addMessageToDisplayQueue", "addMessageToQueue", "addRecentChat"},
			at = @At(value = "CONSTANT", args = "intValue=100")
	)
	public int increaseMaxHistory(int original) {
		return EXPANDED_MAX_HISTORY;
	}
}