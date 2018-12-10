package com.mobo.zggkgisandroid.WebModel;

import java.util.List;

public class StatisticsModelTwo extends BaseResult {
	private List<ResultsBean> results;

	public List<ResultsBean> getResults() {
		return results;
	}

	public void setResults(List<ResultsBean> results) {
		this.results = results;
	}

	public static class ResultsBean {
		private String indicator_year;
		/**
		 * indicator : MQI indicator_grade : 2 indicator_name : 公路技术状况
		 * indicator_number : 86.2
		 */

		public List<IndicatorInfoBean> indicator_info;
		/**
		 * line_bar_data : {"line_bar_name":"MQI","line_bar_value":"86.2"}
		 */

		private List<LineBarBean> line_bar;

		public String getIndicator_year() {
			return indicator_year;
		}

		public void setIndicator_year(String indicator_year) {
			this.indicator_year = indicator_year;
		}

		public List<IndicatorInfoBean> getIndicator_info() {
			return indicator_info;
		}

		public void setIndicator_info(List<IndicatorInfoBean> indicator_info) {
			this.indicator_info = indicator_info;
		}

		public List<LineBarBean> getLine_bar() {
			return line_bar;
		}

		public void setLine_bar(List<LineBarBean> line_bar) {
			this.line_bar = line_bar;
		}

		public static class IndicatorInfoBean {
			private String indicator;
			private String indicator_grade;
			private String indicator_name;
			private String indicator_number;

			public String getIndicator() {
				return indicator;
			}

			public void setIndicator(String indicator) {
				this.indicator = indicator;
			}

			public String getIndicator_grade() {
				return indicator_grade;
			}

			public void setIndicator_grade(String indicator_grade) {
				this.indicator_grade = indicator_grade;
			}

			public String getIndicator_name() {
				return indicator_name;
			}

			public void setIndicator_name(String indicator_name) {
				this.indicator_name = indicator_name;
			}

			public String getIndicator_number() {
				return indicator_number;
			}

			public void setIndicator_number(String indicator_number) {
				this.indicator_number = indicator_number;
			}
		}

		public static class LineBarBean {
			/**
			 * line_bar_name : MQI line_bar_value : 86.2
			 */

			private LineBarDataBean line_bar_data;

			public LineBarDataBean getLine_bar_data() {
				return line_bar_data;
			}

			public void setLine_bar_data(LineBarDataBean line_bar_data) {
				this.line_bar_data = line_bar_data;
			}

			public static class LineBarDataBean {
				private String line_bar_name;
				private String line_bar_value;

				public String getLine_bar_name() {
					return line_bar_name;
				}

				public void setLine_bar_name(String line_bar_name) {
					this.line_bar_name = line_bar_name;
				}

				public String getLine_bar_value() {
					return line_bar_value;
				}

				public void setLine_bar_value(String line_bar_value) {
					this.line_bar_value = line_bar_value;
				}
			}
		}
	}

	

}
