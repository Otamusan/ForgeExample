package otamusan.forgeexample.block;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TestBlocks {
    //今回作成するブロックと、アイテム状態のブロックのインスタンス。
    public static BlockTest blockTest;
    public static BlockItem itemBlockTest;

    //ブロックを登録するためのイベント
    @SubscribeEvent
    public static void onBlocksRegistration(final RegistryEvent.Register<Block> register) {
        //登録の流れはアイテムの時と同じである。つまりブロックでもsetRegistryNameで設定した名前はモデルの指定に使用する。
        blockTest = new BlockTest();
        blockTest.setRegistryName("blocktest");
        register.getRegistry().register(blockTest);
    }

    //アイテムを登録するためのイベント
    @SubscribeEvent
    public static void onItemsRegistration(final RegistryEvent.Register<Item> register) {
        //登録の流れは通常のアイテムと同じであるが、今回は既存のBlockItemクラスを用いてアイテムのインスタンスを生成する。

        //自作アイテムの時はコンストラクタでPropertiesを設定したが、既存のクラスを使用するため引数に渡す必要がある。
        Item.Properties properties = new Item.Properties().group(ItemGroup.BUILDING_BLOCKS);

        //アイテムにしたいブロックとPropertiesを渡してインスタンスを生成。
        itemBlockTest = new BlockItem(blockTest, properties);

        itemBlockTest.setRegistryName("blocktest");
        register.getRegistry().register(itemBlockTest);
    }

}
