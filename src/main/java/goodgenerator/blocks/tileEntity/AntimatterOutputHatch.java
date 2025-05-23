package goodgenerator.blocks.tileEntity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import gregtech.api.enums.Materials;
import gregtech.api.enums.MaterialsUEVplus;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.MTEHatchOutput;
import gregtech.api.util.GTUtility;

public class AntimatterOutputHatch extends MTEHatchOutput {

    private static final FluidStack ANTIMATTER = Materials.Antimatter.getFluid(1);

    public AntimatterOutputHatch(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional, 11);
        this.mDescriptionArray[1] = "Stores Antimatter";
        this.mDescriptionArray[2] = "Antimatter can be inserted from any side";
        this.mDescriptionArray[3] = "Front face input can be toggled with a screwdriver";
        this.mDescriptionArray[4] = "Capacity: 16,384,000L";
    }

    public AntimatterOutputHatch(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aDescription, aTextures);
        setLockedFluidName(
            MaterialsUEVplus.Antimatter.getFluid(1)
                .getFluid()
                .getName());
    }

    @Override
    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new AntimatterOutputHatch(this.mName, this.mTier, this.mDescriptionArray, this.mTextures);
    }

    @Override
    public int getCapacity() {
        return 16_384_000;
    }

    @Override
    public boolean isFluidLocked() {
        return true;
    }

    @Override
    protected void onEmptyingContainerWhenEmpty() {
        // Disable removing the lock
    }

    @Override
    public void onScrewdriverRightClick(ForgeDirection side, EntityPlayer aPlayer, float aX, float aY, float aZ,
        ItemStack aTool) {
        if (!getBaseMetaTileEntity().getCoverAtSide(side)
            .isGUIClickable()) return;
        mMode ^= 1;
        GTUtility.sendChatToPlayer(aPlayer, "Front face input " + (mMode == 1 ? "enabled" : "disabled"));
    }

    @Override
    public boolean isLiquidInput(ForgeDirection side) {
        return mMode == 1 || side != this.getBaseMetaTileEntity()
            .getFrontFacing();
    }

    @Override
    public boolean isLiquidOutput(ForgeDirection side) {
        return side == getBaseMetaTileEntity().getFrontFacing();
    }
}
