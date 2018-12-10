package com.handmark.pulltorefresh.library;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.internal.EmptyViewMethodAccessor;
import com.handmark.pulltorefresh.library.internal.LoadingLayout;

public class PullToRefreshListView extends
		PullToRefreshAdapterViewBase<ListView> {

	private LoadingLayout headerLoadingView;
	private LoadingLayout footerLoadingView;

	class InternalListView extends ListView implements EmptyViewMethodAccessor {

		public InternalListView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		public void setEmptyView(View emptyView) {
			PullToRefreshListView.this.setEmptyView(emptyView);
		}

		@Override
		public void setEmptyViewInternal(View emptyView) {
			super.setEmptyView(emptyView);
		}

		public ContextMenuInfo getContextMenuInfo() {
			return super.getContextMenuInfo();
		}
	}

	public PullToRefreshListView(Context context) {
		super(context);
		this.setDisableScrollingWhileRefreshing(false);
	}

	public PullToRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setDisableScrollingWhileRefreshing(false);
	}

	@Override
	public ContextMenuInfo getContextMenuInfo() {
		return ((InternalListView) getRefreshableView()).getContextMenuInfo();
	}

	public void setReleaseLabel(String releaseLabel) {
		super.setReleaseLabel(releaseLabel);

		if (null != headerLoadingView) {
			headerLoadingView.setReleaseLabel(releaseLabel);
		}
		if (null != footerLoadingView) {
			footerLoadingView.setReleaseLabel(releaseLabel);
		}
	}

	public void setPullLabel(String pullLabel) {
		super.setPullLabel(pullLabel);

		if (null != headerLoadingView) {
			headerLoadingView.setPullLabel(pullLabel);
		}
		if (null != footerLoadingView) {
			footerLoadingView.setPullLabel(pullLabel);
		}
	}

	public void setRefreshingLabel(String refreshingLabel) {
		super.setRefreshingLabel(refreshingLabel);

		if (null != headerLoadingView) {
			headerLoadingView.setRefreshingLabel(refreshingLabel);
		}
		if (null != footerLoadingView) {
			footerLoadingView.setRefreshingLabel(refreshingLabel);
		}
	}

	@Override
	protected final ListView createRefreshableView(Context context,
			AttributeSet attrs) {
		ListView lv = new InternalListView(context, attrs);

		final int mode = this.getMode();

		// Loading View Strings
		String headerDown = context.getString(R.string.header_down);
		String headerUp = context.getString(R.string.header_up);
		String footerDown = context.getString(R.string.footer_down);
		String footerUp = context.getString(R.string.footer_up);
		String refreshingLabel = context
				.getString(R.string.pull_to_refresh_refreshing_label);

		// Add Loading Views
		if (mode == MODE_PULL_DOWN_TO_REFRESH || mode == MODE_BOTH) {
			FrameLayout frame = new FrameLayout(context);
			headerLoadingView = new LoadingLayout(context,
					MODE_PULL_DOWN_TO_REFRESH, headerUp, headerDown,
					refreshingLabel);
			headerLoadingView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Log.d("header", "cancel");
					return;

				}
			});
			frame.addView(headerLoadingView,
					FrameLayout.LayoutParams.FILL_PARENT,
					FrameLayout.LayoutParams.WRAP_CONTENT);
			headerLoadingView.setVisibility(View.GONE);
			lv.addHeaderView(frame);
		}
		if (mode == MODE_PULL_UP_TO_REFRESH || mode == MODE_BOTH) {
			FrameLayout frame = new FrameLayout(context);
			footerLoadingView = new LoadingLayout(context,
					MODE_PULL_UP_TO_REFRESH, footerUp, footerDown,
					refreshingLabel);
			footerLoadingView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Log.d("footer", "cancel");
					return;

				}
			});
			frame.addView(footerLoadingView,
					FrameLayout.LayoutParams.FILL_PARENT,
					FrameLayout.LayoutParams.WRAP_CONTENT);
			footerLoadingView.setVisibility(View.GONE);
			lv.addFooterView(frame);
		}

		// Set it to this so it can be used in ListActivity/ListFragment
		lv.setId(android.R.id.list);
		return lv;
	}

	@Override
	protected boolean setRefreshingInternal(boolean doScroll) {
		super.setRefreshingInternal(false);
		boolean isUp = false;
		final LoadingLayout originalLoadingLayout, listViewLoadingLayout;
		final int selection, scrollToY;

		switch (getCurrentMode()) {
		case MODE_PULL_UP_TO_REFRESH:
			originalLoadingLayout = this.getFooterLayout();
			listViewLoadingLayout = this.footerLoadingView;
			selection = refreshableView.getCount() - 1;
			scrollToY = getScrollY() - getHeaderHeight();
			isUp = true;
			break;
		case MODE_PULL_DOWN_TO_REFRESH:
		default:
			originalLoadingLayout = this.getHeaderLayout();
			listViewLoadingLayout = this.headerLoadingView;
			selection = 0;
			scrollToY = getScrollY() + getHeaderHeight();
			break;
		}

		if (doScroll) {
			// We scroll slightly so that the ListView's header/footer is at the
			// same Y position as our normal header/footer
			this.setHeaderScroll(scrollToY);
		}

		// Hide our original Loading View
		originalLoadingLayout.setVisibility(View.INVISIBLE);

		// Show the ListView Loading View and set it to refresh
		listViewLoadingLayout.setVisibility(View.VISIBLE);
		listViewLoadingLayout.refreshing();

		if (doScroll) {
			// Make sure the ListView is scrolled to show the loading
			// header/footer
			refreshableView.setSelection(selection);

			// Smooth scroll as normal
			smoothScrollTo(0);
		}
		return isUp;
	}

	LoadingLayout loadingLayout;
	LoadingLayout loadingLayout2;

	@Override
	protected void resetHeader() {

		LoadingLayout originalLoadingLayout;
		LoadingLayout listViewLoadingLayout;

		int scrollToHeight = getHeaderHeight();
		final boolean doScroll;

		switch (getCurrentMode()) {
		case MODE_PULL_UP_TO_REFRESH:
			originalLoadingLayout = this.getFooterLayout();
			listViewLoadingLayout = footerLoadingView;
			doScroll = this.isReadyForPullUp();
			break;
		case MODE_PULL_DOWN_TO_REFRESH:
		default:
			originalLoadingLayout = this.getHeaderLayout();
			listViewLoadingLayout = headerLoadingView;
			scrollToHeight *= -1;
			doScroll = this.isReadyForPullDown();
			break;
		}
		if (originalLoadingLayout == null) {
			originalLoadingLayout = loadingLayout;
		} else {
			loadingLayout = originalLoadingLayout;
		}
		// Set our Original View to Visible
		if (originalLoadingLayout != null) {

			originalLoadingLayout.setVisibility(View.VISIBLE);
		}

		// Scroll so our View is at the same Y as the ListView header/footer,
		// but only scroll if the ListView is at the top/bottom
		if (doScroll) {
			this.setHeaderScroll(scrollToHeight);
		}
		if (listViewLoadingLayout == null) {
			listViewLoadingLayout = loadingLayout2;
		} else {
			loadingLayout2 = listViewLoadingLayout;
		}
		if (originalLoadingLayout != null) {

			// Hide the ListView Header/Footer
			listViewLoadingLayout.setVisibility(View.GONE);
		}
		super.resetHeader();
	}

}