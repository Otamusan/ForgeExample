package otamusan.forgeexample.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

//MinecraftでのItemは主にゲーム内の様々なアイテムの種類の一つとして、そのアイテムの性質を実装する。
//あくまでItemが扱うのはItemの持つ性質だけであって、アイテムの個数や耐久値の情報は一切持たない。
//インベントリで操作するようなアイテムはItemStackというクラスで管理されていて、ItemはItemStackの属性として保持される。
//ItemStackにはアイテムの個数や耐久値などが保持されているので、具体的なアイテムの操作はItemStackを介して行うことになる。

//Itemクラスを継承させる。
public class ItemTest extends Item {
    public ItemTest() {
        //Propertiesはアイテムに性質を持たせるためのクラス。
        //今回は生成したPropertiesにクリエタブの情報を持たせてItemを生成する。
        super(new Properties().group(ItemGroup.MISC));
    }
    //アイテムを追加する最低限の実装は出来ているが、追加できる機能の例を以下に挙げる。
    //また機能の追加は基本的にはItemクラスのメソッドを書き換えることで行うことができる。どのようなメソッドが存在するかは継承しているItemクラスや、Itemクラスが実装しているIForgeItemを参照してほしい。

    //アイテムの最大スタック数を4に変更。
    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 4;
    }

    //空中に対してアイテムを使用したときに呼び出されるメソッド
    //使用するとアイテムを一つ消費し爆発を起こす機能を実装した。
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        //引数のWorldはアイテムを使用したディメンション、PlayerEntityは使用したプレイヤーで、Handはアイテムを使用したときの手を表す。

        //引数として与えられたプレイヤーの情報と、アイテムを使用した手の情報から、プレイヤーが使用しているItemStackを取得。
        ItemStack stack = player.getHeldItem(hand);

        //アイテムを消費させるためにItemStackのアイテムの個数を一つ減らしている。
        stack.shrink(1);

        //爆発を起こす処理を記述するが、おまじないとして爆発処理を以下のif文の中に記述する。
        if(!world.isRemote()) {

            //第一引数に好きなEntityを渡すと、そのEntityは爆発のダメージを受けなくなる。今回は使用したプレイヤーを渡している。
            //第二引数、第三引数、第四引数は爆発を起こす座標、それぞれx,y,zを渡している。今回はプレイヤーのいる座標を渡している。
            //第五引数は爆発の範囲を渡している。
            //第六引数は爆発のモードを渡している。今回はTNTの爆発と同じモードを設定した。
            world.createExplosion(player, player.getPosX(), player.getPosY(), player.getPosZ(), 10, Explosion.Mode.BREAK);
        }

        //アイテムを使用した結果を返す。
        //第一引数のActionResultTypeは使用に成功すればActionResultType.SUCCESS、失敗すればActionResultType.FAILを設定すればよい。
        //第二引数はそのアイテムの使用によって変化したItemStackを渡せばよい。
        //この返り値は全体的にそこまで重要ではないためそこまで気にしなくともよい。
        return new ActionResult<>(ActionResultType.SUCCESS,stack);
    }

    //ブロックに対してアイテムを使用したときに呼び出されるメソッド。
    //使用した先のブロックを消去する機能を実装した。
    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        //アイテムを使用した時の情報はItemUseContextに格納されている。

        //使用した先のブロックの座標を取得。
        BlockPos pos = context.getPos();

        //使用したディメンションを取得。
        World world = context.getWorld();

        //置き換えるブロックの種類を取得。ItemとItemStackの関係のようにBlockStateはブロックの種類としてBlockを保持しブロックの向きなどの簡単な情報も保持する。
        Block air = Blocks.AIR;

        //今回扱う空気ブロックは向きなどの情報を持たないため、空気ブロックにデフォルトのBlockStateを生成させている。
        BlockState state = air.getDefaultState();

        //指定された座標に空気ブロックを上書き。
        world.setBlockState(pos, state);

        //onItemRightClickとは違い、ActionResultTypeをそのまま返すことができる。
        //またここでActionResultType.PASSを返すとonItemRightClickの方も同時に呼び出せる。
        return ActionResultType.SUCCESS;
    }
}
