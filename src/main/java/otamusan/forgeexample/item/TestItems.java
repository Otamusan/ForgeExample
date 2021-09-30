package otamusan.forgeexample.item;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//イベントとして呼び出してほしいメソッドがクラス内に存在することを示すアノテーション
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TestItems {
    //今回作成したいItemのインスタンス
    public static ItemTest itemTest;

    //ベントとして呼び出してほしいメソッドであることを示すアノテーション
    //このアノテーションを付けるとForge側が起動時に自動でこのメソッドを見つけ出し、勝手にイベントとして登録してくれる。
    //またどのイベントとして呼び出されるかは引数registerの型によって決定される。
    @SubscribeEvent
    public static void onItemsRegistry(RegistryEvent.Register<Item> register) {
        //ItemTestのインスタンスを生成。
        itemTest = new ItemTest();

        //作成したいItemにシステム上での識別名を設定する。
        //テクスチャなどはこのRegistryNameを用いて紐づけする。
        itemTest.setRegistryName("itemtest");

        //itemTestを登録。
        register.getRegistry().register(itemTest);
    }
}
