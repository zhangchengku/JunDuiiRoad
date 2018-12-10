/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.mobo.zggkgisandroid.photoView;

import android.annotation.TargetApi;
import android.view.View;
/**
 * 设置view动画
 * @author dongxiaoqing
 *
 */
@TargetApi(16)
public class SDK16 {

	/**
	 * 用于在系统进行下一次动画操作时，运行当前的线程
	 * @param view 控件
	 * @param r 子线程
	 */
	public static void postOnAnimation(View view, Runnable r) {
		view.postOnAnimation(r);
	}
	
}
