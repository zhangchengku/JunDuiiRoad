package com.mobo.zggkgisandroid.WebModel;

import java.util.List;

/**
 * Created by mobo on 2016/7/7.
 */
public class StatisticsModel extends BaseResult {

	 private List<ResultsBean> results;

	    public List<ResultsBean> getResults() {
	        return results;
	    }

	    public void setResults(List<ResultsBean> results) {
	        this.results = results;
	    }

	    public static class ResultsBean {
	        private String data_circle_img;
	        private String instructions;
	        private String year;
	        /**
	         * data_key_name : 统计项
	         * data_value_name : 个数（座）
	         * data_values_colom : [{"data_name":"桥梁总数","data_value":"2,152,901.00"},{"data_name":"特大桥数","data_value":"480,264.00"},{"data_name":"大桥数量","data_value":"810,506.00"},{"data_name":"中桥数量","data_value":"388,206.00"},{"data_name":"小桥数量","data_value":"473,925.00"}]
	         */

	        private List<ColomDataBean> colom_data;
	        /**
	         * data_circle_data : {"data_circle_name":"小桥","data_circle_value":"22.01"}
	         */

	        private List<DataCircleBean> data_circle;

	        public String getData_circle_img() {
	            return data_circle_img;
	        }

	        public void setData_circle_img(String data_circle_img) {
	            this.data_circle_img = data_circle_img;
	        }

	        public String getInstructions() {
	            return instructions;
	        }

	        public void setInstructions(String instructions) {
	            this.instructions = instructions;
	        }

	        public String getYear() {
	            return year;
	        }

	        public void setYear(String year) {
	            this.year = year;
	        }

	        public List<ColomDataBean> getColom_data() {
	            return colom_data;
	        }

	        public void setColom_data(List<ColomDataBean> colom_data) {
	            this.colom_data = colom_data;
	        }

	        public List<DataCircleBean> getData_circle() {
	            return data_circle;
	        }

	        public void setData_circle(List<DataCircleBean> data_circle) {
	            this.data_circle = data_circle;
	        }

	        public static class ColomDataBean {
	            private String data_key_name;
	            private String data_value_name;
	            /**
	             * data_name : 桥梁总数
	             * data_value : 2,152,901.00
	             */

	            private List<DataValuesColomBean> data_values_colom;

	            public String getData_key_name() {
	                return data_key_name;
	            }

	            public void setData_key_name(String data_key_name) {
	                this.data_key_name = data_key_name;
	            }

	            public String getData_value_name() {
	                return data_value_name;
	            }

	            public void setData_value_name(String data_value_name) {
	                this.data_value_name = data_value_name;
	            }

	            public List<DataValuesColomBean> getData_values_colom() {
	                return data_values_colom;
	            }

	            public void setData_values_colom(List<DataValuesColomBean> data_values_colom) {
	                this.data_values_colom = data_values_colom;
	            }

	            public static class DataValuesColomBean {
	                private String data_name;
	                private String data_value;

	                public String getData_name() {
	                    return data_name;
	                }

	                public void setData_name(String data_name) {
	                    this.data_name = data_name;
	                }

	                public String getData_value() {
	                    return data_value;
	                }

	                public void setData_value(String data_value) {
	                    this.data_value = data_value;
	                }
	            }
	        }

	        public static class DataCircleBean {
	            /**
	             * data_circle_name : 小桥
	             * data_circle_value : 22.01
	             */

	            private DataCircleDataBean data_circle_data;

	            public DataCircleDataBean getData_circle_data() {
	                return data_circle_data;
	            }

	            public void setData_circle_data(DataCircleDataBean data_circle_data) {
	                this.data_circle_data = data_circle_data;
	            }

	            public static class DataCircleDataBean {
	                private String data_circle_name;
	                private String data_circle_value;

	                public String getData_circle_name() {
	                    return data_circle_name;
	                }

	                public void setData_circle_name(String data_circle_name) {
	                    this.data_circle_name = data_circle_name;
	                }

	                public String getData_circle_value() {
	                    return data_circle_value;
	                }

	                public void setData_circle_value(String data_circle_value) {
	                    this.data_circle_value = data_circle_value;
	                }
	            }
	        }
	    }
	
}
