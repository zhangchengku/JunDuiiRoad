package com.mobo.zggkgisandroid.Bridge;

import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.WebModel.BridgeDetailInfoResult.BridgeDetailInfo.EvaluateInfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 
 * 桥梁评定信息
 * 
 */
public class BridgeJudgeInfoFragment extends Fragment {
	EvaluateInfo evaluate_info;//实体

	public BridgeJudgeInfoFragment() {
	}

	public BridgeJudgeInfoFragment(EvaluateInfo evaluate_info) {
		this.evaluate_info = evaluate_info;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.bridge_jude_info_fragment_layout,
				container, false);
		TextView tvBci = (TextView) view
				.findViewById(R.id.tv_fragment_bridge_judge_bci);// 桥梁技术状况指数(BCI)
		TextView tvBciLevel = (TextView) view
				.findViewById(R.id.tv_fragment_bridge_judge_bci_level);// 桥梁技术状况评定等级
		TextView tvEvaluationDate = (TextView) view
				.findViewById(R.id.tv_fragment_bridge_judge_evaluation_date);// 评定日期
		TextView tvEvaluationMethod = (TextView) view
				.findViewById(R.id.tv_fragment_bridge_judge_evaluation_method);// 评定方法
		TextView tvLastTesting = (TextView) view
				.findViewById(R.id.tv_fragment_bridge_judge_last_testing);// 上次检测
		TextView tvNextTesting = (TextView) view
				.findViewById(R.id.tv_fragment_bridge_judge_next_testing);// 下次检测
		TextView tvEvaluationExplain = (TextView) view
				.findViewById(R.id.tv_fragment_bridge_judge_evaluation_explain);// 评定说明
		if (evaluate_info == null)
			return view;
		tvBci.setText(evaluate_info.getBci());
		if ("1".equals(evaluate_info.getEvaluate_grade())) {
			tvBciLevel.setText("一类");
			tvBciLevel
					.setBackgroundResource(R.drawable.bridge_detail_info_strok_one_bg);
		} else if ("2".equals(evaluate_info.getEvaluate_grade())) {
			tvBciLevel.setText("二类");
			tvBciLevel
					.setBackgroundResource(R.drawable.bridge_detail_info_strok_two_bg);
		} else if ("3".equals(evaluate_info.getEvaluate_grade())) {
			tvBciLevel.setText("三类");
			tvBciLevel
					.setBackgroundResource(R.drawable.bridge_detail_info_strok_three_bg);
		} else if ("4".equals(evaluate_info.getEvaluate_grade())) {
			tvBciLevel.setText("四类");
			tvBciLevel
					.setBackgroundResource(R.drawable.bridge_detail_info_strok_four_bg);
		} else if ("5".equals(evaluate_info.getEvaluate_grade())) {
			tvBciLevel.setText("五类");
			tvBciLevel
					.setBackgroundResource(R.drawable.bridge_detail_info_strok_five_bg);
		}

		tvEvaluationDate.setText(evaluate_info.getEvaluate_data());
		tvEvaluationMethod.setText(evaluate_info.getEvaluate_function());
		tvLastTesting.setText(evaluate_info.getLast_time());
		tvNextTesting.setText(evaluate_info.getNext_time());
		tvEvaluationExplain.setText(evaluate_info.getEvaluate_detail());
		return view;
	}
}
