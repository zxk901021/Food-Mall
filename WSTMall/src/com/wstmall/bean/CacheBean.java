
package com.wstmall.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.util.Log;

import com.wstmall.activity.MainActivity;
import com.wstmall.application.Const;
import com.wstmall.util.EAJson;

public class CacheBean implements Serializable {

	private static final String TAG = "么么哒";
	public Point point = new Point(39.0849967352,117.1994308610);// 经纬度坐标
	public City city = new City("820302", "天津市"); // 城市
	public City city2 = new City("820308", "和平区"); // 县区
	
	/*public Point point = new Point(24.445374, 118.103766);// 经纬度坐标
	public City city = new City("350200", "厦门市"); // 城市
	public City city2 = new City("350203", "思明区"); // 县区
*/	
/*	public Point point = new Point(40.657366, 109.874611);// 经纬度坐标
	public City city = new City("150200", "包头市"); // 城市
	public City city2 = new City("150204", "青山区"); // 县区
*/	
	private List<String> searchList;// 历史搜索
	private List<GoodsListBean> shoppingCartList;// 购物车列表
	private int shoppingCartSum;
	private String tokenId;
	private boolean flag;
	// 首页数据缓存
	private List<Advertisement> advertisementlist;
	private List<RecommendGoodsBean> recommendgoodlist;

	public void addAdvList(Advertisement advertisement) {
		if (advertisementlist == null) {
			advertisementlist = new ArrayList<Advertisement>();
		}
		advertisementlist.add(advertisement);
		Const.isNeedSaveCache = true;
	}

	public void addRgList(RecommendGoodsBean recommendGoodsBean) {
		if (recommendgoodlist == null) {
			recommendgoodlist = new ArrayList<RecommendGoodsBean>();
		}
		recommendgoodlist.add(recommendGoodsBean);
		Const.isNeedSaveCache = true;
	}

	public void removeAdvList() {
		if (advertisementlist != null) {
			advertisementlist.clear();
		}
		Const.isNeedSaveCache = true;
	}

	public void removeRgList() {
		if (recommendgoodlist != null) {
			recommendgoodlist.clear();
		}
		Const.isNeedSaveCache = true;
	}

	public List<Advertisement> getAdvertisementList() {
		return advertisementlist;
	}

	public List<RecommendGoodsBean> getRecommendGoodsList() {
		return recommendgoodlist;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
		Const.isNeedSaveCache = true;
	}
	
	/**
	 * 
	 * 返回数据goodsAttrId字段值为null，
	 * 该字段用于同类商品添加到购物车数量不+1
	 * 
	 * @param bean
	 */
	//购物车数据缓存
	public void addShoppingCartList(GoodsListBean bean) {
		shoppingCartSum++;
		if (shoppingCartList == null) {
			shoppingCartList = new ArrayList<GoodsListBean>();
		}
		flag = true;
		Log.e("goodslistbean", bean.toString());
		for (int i = 0; i < Const.cache.shoppingCartList.size(); i++) {
			if (Const.cache.shoppingCartList.get(i).goodsId
					.equals(bean.goodsId)
					&&Const.cache.shoppingCartList.get(i).goodsAttrId.equals(bean.goodsAttrId)
					)
			{
				Log.i(TAG, "购物车存在相同商品");
				Const.cache.shoppingCartList.get(i).goodscount = Const.cache.shoppingCartList
						.get(i).goodscount + 1;
				flag = false;
			}
		}
		if (flag) {
			bean.setGoodscount(1);
			shoppingCartList.add(bean);
		}
		Const.isNeedSaveCache = true;
	}

	public void addShoppingCartListWithNum(GoodsListBean bean, int num) {
		shoppingCartSum += num;
		if (shoppingCartList == null) {
			shoppingCartList = new ArrayList<GoodsListBean>();
		}
		flag = true;
		for (int i = 0; i < Const.cache.shoppingCartList.size(); i++) {
			if (Const.cache.shoppingCartList.get(i).goodsId
					.equals(bean.goodsId)&&Const.cache.shoppingCartList.get(i).goodsAttrId.equals(bean.goodsAttrId)) {
				Log.e("Const.cache.shoppingCartList.get(i).goodsAttrId", Const.cache.shoppingCartList.get(i).goodsAttrId);
				Log.e(TAG, "购物车存在相同商品");
				Const.cache.shoppingCartList.get(i).goodscount = Const.cache.shoppingCartList
						.get(i).goodscount + num;
				flag = false;
			}
		}
		if (flag) {
			bean.setGoodscount(num);
			shoppingCartList.add(bean);
		}
		Const.isNeedSaveCache = true;
	}

	public void clearShoppingCartList() {
		shoppingCartList = null;
		Const.isNeedSaveCache = true;
		shoppingCartSum = 0;
	}

	public int getShoppingCartSum() {
		return shoppingCartSum;
	}

	public int getShoppingCartListSize() {
		if (shoppingCartList == null) {
			shoppingCartList = new ArrayList<GoodsListBean>();
			return 0;
		}
		return shoppingCartList.size();
	}

	public GoodsListBean getGoodsBeanFromShoppingCartList(int location) {
		return shoppingCartList.get(location);
	}

	public void deleteGoodsFromShoppingCartWithGoodsId(String goodsId) {
		for (int i = 0; i < shoppingCartList.size(); i++) {
			if (shoppingCartList.get(i).goodsId.equals(goodsId)) {
				shoppingCartSum -= shoppingCartList.get(i).goodscount;
				shoppingCartList.remove(i);
				break;
			}
		}
	}

	public void addSearchTarget(String searchTarget) {
		if (searchList == null) {
			searchList = new ArrayList<String>();
		}
		searchList.add(searchTarget);
		Const.isNeedSaveCache = true;
	}

	public List<String> getSearchList() {
		return searchList;
	}

	public void clearSearchList() {
		searchList = null;
		Const.isNeedSaveCache = true;
	}

}
