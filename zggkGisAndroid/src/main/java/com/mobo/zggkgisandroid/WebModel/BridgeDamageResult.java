package com.mobo.zggkgisandroid.WebModel;

import java.util.ArrayList;

public class BridgeDamageResult extends BaseResult
{

	private ArrayList<bridge_damage> results;

	public ArrayList<bridge_damage> getResults()
	{
		return results;
	}

	public void setResults(ArrayList<bridge_damage> results)
	{
		this.results = results;
	}

	public class bridge_damage
	{

		private String bridge_length; // 桥梁全长
		private String Bridge_max_span;// 主跨最大跨径
		private String Bridge_span_group; // 桥跨组合
		private String completed_monthy; // 竣工年月
		private String last_evaluate_time; // 末次评定时间
		private String last_evaluate_grade;// 末次评定等级
		private String fix_money; // 维修经费
		private Bridge_damage_info bridge_damage_info; // 病害信息

		public class Bridge_damage_info
		{

		}
	}

}
