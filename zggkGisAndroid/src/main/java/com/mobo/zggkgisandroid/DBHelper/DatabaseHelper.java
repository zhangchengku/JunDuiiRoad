package com.mobo.zggkgisandroid.DBHelper;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.mobo.zggkgisandroid.CustomApp;
import com.mobo.zggkgisandroid.DBEntity.DB_Value;
import com.mobo.zggkgisandroid.DBEntity.Province;
import com.mobo.zggkgisandroid.DBEntity.SearchContent;
import com.mobo.zggkgisandroid.WebModel.CityList;

/**
 * 数据库用于管理你的数据库的创建和升级。这类通常提供 通过另一类使用DAO。
 * 
 * @author Qym
 * 
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	// 数据库文件的名称
	private final static String DATABASE_NAME = CustomApp.app.DATABASE_NAME;

	// 数据库的版本
	private static final int DATABASE_VERSION = 3;

	// 键值
	private Dao<DB_Value, String> valueSet_Dao = null;
	private Dao<Province, String> provinces_Dao = null;
	private Dao<CityList, String> cityDao;
	private Dao<SearchContent, String> searchDao;

	/**
	 * 构造方法
	 * 
	 * @param context
	 *            context 对象
	 */
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * 数据库第一次被创建
	 * 
	 * @param db
	 * @param connectionSource
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, DB_Value.class);
			TableUtils.createTable(connectionSource, Province.class);
			TableUtils.createTable(connectionSource, CityList.class);
			TableUtils.createTable(connectionSource, SearchContent.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 数据库升级
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		if (oldVersion == 1 && newVersion == 2) {
			try {
				TableUtils.createTable(connectionSource, CityList.class);
				TableUtils.createTable(connectionSource, SearchContent.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(newVersion == 3){
			try {
				TableUtils.dropTable(connectionSource, SearchContent.class,false);
				TableUtils.createTable(connectionSource, SearchContent.class);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 返回数据库访问对象（DAO）
	 */
	public Dao<DB_Value, String> getValueSetDao() throws SQLException {
		if (valueSet_Dao == null) {
			valueSet_Dao = getDao(DB_Value.class);

		}
		return valueSet_Dao;
	}

	/**
	 * 返回数据库访问对象（DAO）
	 */
	public Dao<Province, String> getProvincesDao() throws SQLException {
		if (provinces_Dao == null) {
			provinces_Dao = getDao(Province.class);

		}
		return provinces_Dao;
	}

	/**
	 * 返回数据库访问对象（DAO）
	 */
	public Dao<CityList, String> getCityDao() throws SQLException {
		if (cityDao == null) {
			cityDao = getDao(CityList.class);
		}
		return cityDao;
	}

	/**
	 * 返回数据库访问对象（DAO）
	 */
	public Dao<SearchContent, String> getSearchDao() throws SQLException {
		if (searchDao == null) {
			searchDao = getDao(SearchContent.class);
		}
		return searchDao;
	}

	/**
	 * 清除表
	 */
	public void clearValueTable() {
		try {
			TableUtils.clearTable(valueSet_Dao.getConnectionSource(), DB_Value.class);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 清除表
	 */
	public void clearProvinceTable() {
		try {
			TableUtils.clearTable(valueSet_Dao.getConnectionSource(), Province.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭数据库连接和清除缓存DAO
	 */
	@Override
	public void close() {
		super.close();
		valueSet_Dao = null;
		provinces_Dao = null;
		cityDao = null;
		searchDao = null;
	}
}
