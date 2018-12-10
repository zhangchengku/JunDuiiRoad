package com.mobo.zggkgisandroid.DBHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.mobo.zggkgisandroid.DBEntity.DB_Value;
import com.mobo.zggkgisandroid.DBEntity.Province;
import com.mobo.zggkgisandroid.DBEntity.SearchContent;
import com.mobo.zggkgisandroid.WebModel.CityList;

/**
 * 数据库服务类
 * 
 * 命名规范: 1.本地数据add 2.移除remove 3.修改update 4.提交submit 5.查询query
 * 
 * @author Qym
 * 
 */
public class DBManager {

	private DatabaseHelper databaseHelper = null;// 数据库帮助对象

	private Context context;// 上下文对象

	// 企业Dao
	Dao<DB_Value, String> valueSet_Dao;
	Dao<Province, String> province_Dao;

	/**
	 * 构造方法
	 * 
	 * @param context
	 *            对象
	 */
	public DBManager(Context context) {
		this.context = context;

		try {
			valueSet_Dao = getHelper().getValueSetDao();
			province_Dao = getHelper().getProvincesDao();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * You'll need this in your class to get the helper from the manager once
	 * per class.
	 */
	private DatabaseHelper getHelper() {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
		}
		return databaseHelper;
	}

	/******************************** 城市管理开始 ********************************/

	/**
	 * 获取指定code的键值实体
	 * 
	 * @return DB_Value
	 */
	public DB_Value queryValueByCode(String dic_code) {
		try {
			QueryBuilder<DB_Value, String> query = valueSet_Dao.queryBuilder();
			return query.where().eq("dic_code", dic_code).queryForFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取指定code的键值实体
	 * 
	 * @return DB_Value
	 */
	public ArrayList<DB_Value> queryValuesByCode(String dic_code) {
		try {
			QueryBuilder<DB_Value, String> query = valueSet_Dao.queryBuilder();
			return (ArrayList<DB_Value>) query.where().eq("dic_code", dic_code).query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取指定dic_type的键值实体
	 * 
	 * @return DB_Value
	 */
	public ArrayList<DB_Value> queryValuesByType(String dic_type) {
		try {
			QueryBuilder<DB_Value, String> query = valueSet_Dao.queryBuilder();
			return (ArrayList<DB_Value>) query.where().eq("dic_type", dic_type).query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取指定code的键值实体
	 * 
	 * @return DB_Value
	 */
	public ArrayList<DB_Value> queryValuesByCodeOrder(String dic_type) {
		try {
			QueryBuilder<DB_Value, String> query = valueSet_Dao.queryBuilder();
			QueryBuilder<DB_Value, String> queryOrder = query.orderByRaw("cast(dic_order as int)");
			return (ArrayList<DB_Value>) queryOrder.where().eq("dic_type", dic_type).query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取指定code的键值实体
	 * 
	 * @return DB_Value
	 */
	public int updateValueSet(List<DB_Value> newValueSet) {
		try {
			databaseHelper.clearValueTable();
			int count = 0;
			for (int i = 0; i < newValueSet.size(); i++) {
				Log.i("=====================]", "======dic_desc==" + newValueSet.get(i).getDic_desc());
				valueSet_Dao.create(newValueSet.get(i));

				count++;
			}
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取指定code的键值实体
	 * 
	 * @return DB_Value
	 */
	public int updateProvinceSet(List<Province> provinces) {
		try {
			databaseHelper.clearProvinceTable();
			int count = 0;
			for (int i = 0; i < provinces.size(); i++) {
				province_Dao.create(provinces.get(i));
				count++;
			}
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取指定dic_type的键值实体
	 * 
	 * @return DB_Value
	 */
	public ArrayList<DB_Value> queryValuesByType(String dic_type, String dic_code) {
		try {

			QueryBuilder<DB_Value, String> query = valueSet_Dao.queryBuilder();
			return (ArrayList<DB_Value>) query.where().eq("dic_type", dic_type).and().eq("dic_order", dic_code).query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取指定dic_type的键值实体
	 * 
	 * @return DB_Value
	 */
	public ArrayList<Province> queryValuesByType() {
		try {

			QueryBuilder<Province, String> query = province_Dao.queryBuilder();
			return (ArrayList<Province>) query.query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 更新最近选择的城市数据库
	 * 
	 * @param cityList
	 */
	public void updataCity(CityList cityList) {
		try {

			getHelper().getCityDao().createOrUpdate(cityList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取最近选择过的城市
	 * 
	 * @return
	 */
	public List<CityList> queryCityList() {
		try {
			return getHelper().getCityDao().queryBuilder().orderBy("time", false).query();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据id获取省份
	 * 
	 * @param cityList
	 */
	public CityList getCity(String province_code) {
		try {
			return getHelper().getCityDao().queryBuilder().where().eq("province_code", province_code).queryForFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除最近选择的城市记录
	 * 
	 * @param cityList
	 */
	public int deleteCity(CityList cityList) {
		try {
			getHelper().getCityDao().delete(cityList);
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}

	/**
	 * 获取搜索历史数据
	 * 
	 * @param searchContent
	 */
	public List<SearchContent> getSearchList() {
		try {
			return getHelper().getSearchDao().queryBuilder().orderBy("time", false).query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 添加或者更新一条搜索历史数据
	 * 
	 * @param searchContent
	 */
	public void updataSearch(SearchContent searchContent) {
		try {
			QueryBuilder<SearchContent, String> queryBuilder = getHelper().getSearchDao().queryBuilder();
			if (searchContent.getType().equals("0")) {
				SearchContent searchContent2 = queryBuilder.where().eq("bridge_name", searchContent.getBridge_name())
						.and().eq("route_name", searchContent.getRoute_name()).and()
						.eq("city_name", searchContent.getCity_name()).queryForFirst();
				if (searchContent2 != null) {
					searchContent2.setTime(searchContent.getTime());
					searchContent2.setCity_name(searchContent.getCity_name());
					getHelper().getSearchDao().createOrUpdate(searchContent2);
					return;
				}
			} else {
				SearchContent searchContent2 = queryBuilder.where().eq("route_code", searchContent.getRoute_code())
						.and().eq("route_name", searchContent.getRoute_name()).queryForFirst();
				if (searchContent2 != null) {
					searchContent2.setTime(searchContent.getTime());
					searchContent2.setCity_name(searchContent.getCity_name());
					searchContent2.setRoute_length(searchContent.getRoute_length());
					getHelper().getSearchDao().createOrUpdate(searchContent2);
					return;
				}
			}
			List<SearchContent> searchContents = getSearchList();
			if (searchContents != null && searchContents.size() == 30) {
				deleteSearch(searchContents.get(searchContents.size() - 1));
			}
			getHelper().getSearchDao().createOrUpdate(searchContent);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 删除一条搜索历史记录数据
	 * 
	 * @param searchContent
	 */
	public void deleteSearch(SearchContent searchContent) {
		try {
			getHelper().getSearchDao().delete(searchContent);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 清空搜索历史记录数据
	 */
	public void calcelSearch() {
		try {
			getHelper().getSearchDao().deleteBuilder().delete();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
