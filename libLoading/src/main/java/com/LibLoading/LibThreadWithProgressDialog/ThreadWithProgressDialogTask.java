package com.LibLoading.LibThreadWithProgressDialog;

/**
 * 
 * ProgressDialogThread 的Interface
 * 
 * ProgressDialogThreadMain ： 线程的主函数，将在ProgressDialog显示的时候在线程中运行 OnTaskDismissed
 * ： 在任务未完成，ProgressDialog被dismiss时的响应 OnTaskDone ： 任务完成时的响应
 * 
 * @author Qym
 * 
 */
public interface ThreadWithProgressDialogTask {

	/**
	 * 异步
	 * 
	 * @return true：成功 false ：失败
	 */
	boolean TaskMain();

	/**
	 * 异步执行失败
	 * 
	 * @return false
	 */
	boolean OnTaskDismissed();

	/**
	 * 异步执行成功
	 * 
	 * @return true
	 */
	boolean OnTaskDone();

}
