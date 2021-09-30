package otamusan.forgeexample.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CryingObsidianBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
//Blockクラスを継承する。
public class BlockTest extends Block {
    public BlockTest() {
        //Itemの時と同様に親クラスのコンストラクタにPropertiesを渡している。
        //MaterialはBlockを作るときのプリセットで、.setRequiresTool()は採掘にツールが必要であること、.hardnessAndResistance()は採掘時間と爆破体制を設定している。Material以外の設定は必須ではない。
        super(Block.Properties.create(Material.ROCK)
                .setRequiresTool()
                .hardnessAndResistance(1.5F, 6.0F));
    }

    //ブロックを採掘するためのツールの指定。
    @Nullable
    @Override
    public ToolType getHarvestTool(BlockState state) {
        return ToolType.PICKAXE;
    }

    //ブロックを右クリックしたときに呼ばれるメソッド。
    //今回は右クリックするとプレイヤーが上に飛び上がるように実装した。
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        //右クリックしたプレイヤーにy方向の速度を与えている
        player.addVelocity(0,1,0);

        //返り値はItemTestの時と同じ。
        return  ActionResultType.SUCCESS;
    }

    //ブロックを破壊したときに呼ばれるメソッド。
    //今回は破壊した地点にエンダーマンがスポーンするように実装した。
    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        //エンダーマンのインスタンスを生成、EntityTypeは対応したものを使用する。
        EndermanEntity endermanEntity = new EndermanEntity(EntityType.ENDERMAN, worldIn);

        //エンダーマンの座標をブロックの破壊した場所に書き換える。
        endermanEntity.setPosition(pos.getX(), pos.getY(),pos.getZ());

        //ブロックを破壊したワールドにエンダーマンをスポーン
        worldIn.addEntity(endermanEntity);
    }
}
