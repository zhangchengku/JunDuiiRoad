package com.mobo.zggkgisandroid.ClassfyInfo;

import java.io.Serializable;

public class IndicatorInfo implements Serializable{

	private String indicator_id;// 指标ID
	private String indicator_value;// 指标名称
	private String indicator_num;// 指标值
	private String indicator_grade;// 指标等级

	public String getIndicator_id() {
		return indicator_id;
	}

	public void setIndicator_id(String indicator_id) {
		this.indicator_id = indicator_id;
	}

	public String getIndicator_value() {
		return indicator_value;
	}

	public void setIndicator_value(String indicator_value) {
		this.indicator_value = indicator_value;
	}

	public String getIndicator_num() {
		return indicator_num;
	}

	public void setIndicator_num(String indicator_num) {
		this.indicator_num = indicator_num;
	}

	public String getIndicator_grade() {
		return indicator_grade;
	}

	public void setIndicator_grade(String indicator_grade) {
		this.indicator_grade = indicator_grade;
	}

}
