package sophisticated_wolves.entity.ai;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import sophisticated_wolves.entity.EntitySophisticatedWolf;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class EntityAITeleportAtDrowning extends EntityAIBase {

    protected EntitySophisticatedWolf pet;
    protected World world;
    protected PathNavigate petPathfinder;

    public EntityAITeleportAtDrowning(EntityTameable pet) {
        this.pet = (EntitySophisticatedWolf) pet;
        this.world = pet.world;
        this.petPathfinder = pet.getNavigator();
    }

    @Override
    public boolean shouldExecute() {
        return this.pet.isDrowning() && this.pet.isInWater() && this.pet.isTamed() && this.pet.getOwner() != null;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.pet.isDrowning() && this.pet.isInWater() && this.pet.isTamed() && this.pet.getOwner() != null;
    }

    @Override
    public void startExecuting() {
    }

    @Override
    public void resetTask() {
        this.pet.setDrowning(false);
    }

    @Override
    public void updateTask() {
        int xPos = MathHelper.floor(this.pet.getOwner().posX) - 2;
        int yPos = MathHelper.floor(this.pet.getOwner().getEntityBoundingBox().minY);
        int zPos = MathHelper.floor(this.pet.getOwner().posZ) - 2;

        for (int x = -2; x <= 2; ++x) {
            for (int z = -2; z <= 2; ++z) {
                BlockPos pos = new BlockPos(xPos + x, yPos, zPos + z);
                IBlockState state = this.world.getBlockState(pos);
                if (state.getBlock().isAir(state, world, pos)) {
                    this.pet.setLocationAndAngles(xPos + x + 0.5, yPos, zPos + z + 0.5, this.pet.rotationYaw, this.pet.rotationPitch);
                    this.petPathfinder.clearPath();
                    return;
                }
            }
        }
    }
}
