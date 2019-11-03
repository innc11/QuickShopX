package cn.innc11.QuickShopX.config;

import java.util.ArrayList;
import java.util.HashMap;

import cn.innc11.QuickShopX.QuickShopXPlugin;
import cn.nukkit.utils.TextFormat;

public class LangConfig extends MyConfig 
{
	HashMap<Lang, String> lang = new HashMap<Lang, String>();
	
	public LangConfig()
	{
		super("language.yml");
		
		reload();
	}

	@Override
	public void save() 
	{
		config.save();
	}

	@Override
	public void reload() 
	{
		lang.clear();

		boolean supplement = false;
		int ct = 0;
		
		for(Lang key : Lang.values())
		{
			Object v = config.get(key.name());

			if(v==null)
			{
				config.set(key.name(), key.getDefaultLangText());
				QuickShopXPlugin.instance.getLogger().info("set default language for language.yml("+key.name()+")");
				supplement = true;
				lang.put(key, key.getDefaultLangText());
			}

			if(v instanceof String)
			{
				lang.put(key, (String) v);
				ct++;
			} else if(v instanceof ArrayList)
			{
				ArrayList<String> values = (ArrayList<String>) v;

				StringBuffer sb = new StringBuffer();

				for(int i=0;i<values.size();i++)
				{
					sb.append(values.get(i));
					if(i!=values.size()-1) {
						sb.append("\n");
					}
				}

				lang.put(key, sb.toString());
				ct++;
			}

			
		}

		if(supplement)
			save();
		
		QuickShopXPlugin.instance.getLogger().info("Loaded "+ct+" Languages.");
	}
	
	public String get(Lang l, String... argsPair)
	{
		String rawStr = lang.get(l);
		
		int argCount = argsPair.length / 2;
		
		for(int i=0;i<argCount;i++)
		{
			String reg = argsPair[i*2];
			String replacement = argsPair[i*2+1];
			
			if(reg.startsWith("{") && reg.endsWith("}"))
			{
				reg = reg.replaceAll("\\{", "\\\\{");
				reg = reg.replaceAll("\\}", "\\\\}");
				rawStr = rawStr.replaceAll("\\$"+reg, replacement);
			}
			
		}
		
		return TextFormat.colorize(rawStr);
	}
	
	public static enum Lang
	{
		BUY("购买"),
		SELL("回收"),
		SERVER_SHOP_NICKNAME("官方"),
		
		IM_SHOP_TYPE_UPDATED("新的商店类型: ${NEW_SHOP_TYPE}"),
		IM_SHOP_TYPE_NO_UPDATE("这个商店类型不需要更改"),
		IM_PRICE_NO_UPDATE_NO_ONWER("只有商店主人才能更改商店的价格"),
		IM_INTERACTOIN_TIMEOUT("输入超时"),
		IM_NO_SELECTED_SHOP("请先点击来选中一个商店"),
		IM_INTERCEPT_CONSOLE("后台不要捣乱"),
		IM_SHOP_PRICE_UPDATED("新的价格是&l&a ${PRICE}"),
		IM_PRICE_WRONG_FORMAT("价格不是一个数字"),
		IM_PRICE_NO_UPDATE_WRONG_ARGS("参数不正确, 示例: /qs price 10.5"),
		IM_SHOP_UPDATED_SERVER_SHOP("已经修改为服务器商店"),
		IM_SHOP_UPDATED_LIMITED("已经修改为普通商店"),
		IM_SHOP_INFO_SHOW("商品: ${GOODS}/${PRICE}, 类型: ${SHOP_TYPE}, 店主(${OWNER})\\n${SIGN_STOCK_TEXT}"),
		IM_ENTER_TRANSACTIONS_COUNT("输入交易数量"),
		IM_NO_ENTER_NUMBER("输入的不是一个数字"),
		IM_SUCCEESSFULLY_REMOVED_SHOP("成功移除这个商店"),
		IM_NO_REMOVE_SHOP_NO_ONWER("不能破环非自己的商店"),
		IM_NO_REMOVE_SHOP_EXISTS_SIGN("只有移除牌子才能移除商店"),
		IM_CREATING_SHOP_ENTER_PRICE("输入一个价格来创建商店"),
		IM_SUCCEESSFULLY_CREATED_SHOP("商店已经创建"),
		IM_NO_ITEM_ON_HAND("手上必须拿着一样物品"),
		IM_SHOP_SIGN_BLOCKED("箱子的前方有: ${BLOCK_NAME} 阻挡,无法生成商店牌子"),
		IM_TRADE_CANCELED("交易取消"),
		IM_BUYSHOP_OWNER("成功售出&6 ${ITEM_NAME}&rx&6${ITEM_COUNT}&r, 获得&6 ${MONEY}"),
		IM_BUYSHOP_CUSTOMER("成功买下&6 ${ITEM_NAME}&rx&6${ITEM_COUNT}&r, 花费&6 ${MONEY}"),
		IM_STOCK_FULL("这个箱子库存已经满了，没法回收这么多(${TARGET_COUNT})物品,最多只能回收${MAX_COUNT}个"),
		IM_ITEM_NOT_ENOUGH("背包里的物品(${ITEM_NAME})不够这么多(${TARGET_COUNT})"),
		IM_SHOP_OWNER_NOT_ENOUGH_MONEY("这个商店的店主(${OWNER})没有这么多钱来回收这些物品"),
		IM_SELLSHOP_OWNER("成功回收&6 ${ITEM_NAME}&rx&6${ITEM_COUNT}&r, 花费&6 ${MONEY}"),
		IM_SELLSHOP_CUSTOMER("卖出${ITEM_COUNT}个,获得&6 ${MONEY}"),
		IM_BACKPACK_FULL("背包已经不够装下${TARGET_COUNT}个${ITEM_NAME}了"),
		IM_SHOP_SOLD_OUT("商店的商品已经售空了"),
		IM_INSUFFICIENT_SHOP_STOCK("商店里没有这么多商品(${TARGET_COUNT}个),只有${MAX_COUNT}个"),
		IM_NOT_ENOUGH_MONEY_TO_BUYING("你的钱(${MONEY})不够这次购买(&a${ITEM_PRICE}&r*${ITEM_COUNT}= &a${SUBTOTAL}&r),还差(${LACK_MONEY})"),
		IM_SHOP_INFO_UPDATED("商店信息已经更新"),
		IM_SNAKE_MODE_DESTROY_SHOP("只能在潜行下才能破坏商店"),
		IM_NO_RESIDENCE_PERMISSION("你没有这个领地的${PERMISSION}权限"),
		IM_CREATE_SHOP_IN_RESIDENCE_NOLY("只能在领地中创建商店"),
		IM_CHEST_SIGN_DIFFERENT_RESIDENCE("商店的箱子和牌子不允许跨领地"),
		IM_SIGN_NOT_IN_A_RESIDENCE_RANGE("商店的牌子不在领地范围内"),
		IM_SIGN_NOT_ALLOWED_IN_A_RESIDENCE("商店的牌子不允许在一个领地范围内"),
		IM_NOT_SHOP_OWNER_CANNOT_OPEN_CHEST("你不是这个商店的主人,无法打开箱子"),

		FORM_SHOPDATA__TITLE("商店信息面板"),
		FORM_SHOPDATA__UNIT_PRICE("物品价格"),
		FORM_SHOPDATA__SHOP_OWNER("商店主人"),
		FORM_SHOPDATA__SHOP_TYPE("商店类型"),
		FORM_SHOPDATA__SERVER_SHOP("服务器商店"),
		FORM_TRADING__TITLE("${OWNER}的商店交易界面"),
		FORM_TRADING__SHOP_INFO("商品: ${GOODS_NAME}\\n\\n价格: ${UNIT_PRICE}\\n\\n类型: ${SHOP_TYPE}\\n\\n${STOCK}"),
		FORM_TRADING__TRADING_VOLUME("交易量 ${TRADING_VOLUME}"),
		FORM_MASTER__TITLE("${OWNER}的商店"),
		FORM_MASTER__CONTENT("商店的价格: ${UNIT_PRICE}\\n商店的类型: ${SHOP_TYPE}\\n商店的库存: ${SHOP_STOCK}\\n\\n\\n\\n\\n\\n\\n\\n"),
		FORM_MASTER__BUTTON_SHOP_DATA_PANEL("打开商店设置界面"),
		FORM_MASTER__BUTTON_SHOP_TRADING_PANEL("打开商店交易界面"),
		FORM_MASTER__BUTTON_REMOVE_SHOP("移除商店"),
		FORM_CONTROL_PANEL__TITLE("${PLUGIN_NAME}控制面板(插件版本: ${PLUGIN_VERSION})"),
		FORM_CONTROL_PANEL__SHOP_INTERACTION_TIME("商店交互时间(秒)"),
		FORM_CONTROL_PANEL__SHOP_HOLOGRAM_ITEM("商店全息物品显示"),
		FORM_CONTROL_PANEL__FORM_OPERATE("FormWindow触发"),
		FORM_CONTROL_PANEL__PACKET_SEND_PS("全息物品的每秒最大发包量"),
		FORM_CONTROL_PANEL__INTERACTION_WITH_RESIDENCE_PLUGIN("和领地插件交互"),
		FORM_CONTROL_PANEL__CREATE_SHOP_IN_RESIDENCE_NOLY("只能在领地中创建商店"),
		FORM_CONTROL_PANEL__OP_IGNORE_RESIDENCE_BUILD_PERMISSION("OP无视领地的build权限"),
		FORM_CONTROL_PANEL__SNAKE_MODE_DESTROY_SHOP("潜行模式下才能破坏商店"),

		PLUGIN_MESSAGE_RELOAD_DONE("重新加载完成"),
		PLUGIN_MESSAGE_PLUGIN_CONFIGURE_UPDATED("插件配置已经更新"),
		PLUGIN_MESSAGE_HELP_NORMAL("&6----QuickShop 指令----\n&b/qs help(h) - 查看帮助\n&b/qs buy(b) - 设置当前点击过的商店为&a购买&b类型\n&b/qs sell(s) - 设置当前点击过的商店为&4回收&b类型\n&b/qs price(p) <价格> - 设置当前点击过的商店的价格"),
		PLUGIN_MESSAGE_HELP_OPERATOR("&9/qs unlimited(ul) - 设置当前点击过的商店为无限商店\n&9/qs version(v) - 查看插件版本信息\n&9/qs controlpanel(cp) - 打开插件的控制面板(FormUI)\n&9/qs reload(r) - 重新加载插件的所有配置文件(商店数据文件除外)");

		String defaultLangText;

		private Lang(String defaultLangText)
		{
			this.defaultLangText = defaultLangText;
		}

		public String getDefaultLangText()
		{
			return defaultLangText;
		}

		public static boolean contains(String value)
		{
			for(Lang lang : values())
			{
				if(lang.name().equals(value))
					return true;
			}
			return false;
		}
	}

}