package com.mobo.zggkgisandroid.DBEntity;

import com.j256.ormlite.field.DatabaseField;

public class Province {
	// 设置id[见api]
		@DatabaseField(id = true)
		String province_code;// 省份编码
		@DatabaseField
		String province_name;// 省份名称
		
		public String getProvince_code() {
			return province_code;
		}
		public void setProvince_code(String province_code) {
			this.province_code = province_code;
		}
		public String getProvince_name() {
			return province_name;
		}
		public void setProvince_name(String province_name) {
			this.province_name = province_name;
		}
		
		
}
