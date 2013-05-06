package com.pms.demo;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class PopMapActivity extends MapActivity {
	/**
	 * 地图View
	 */
	protected MapView mapView;
	/**
	 * 弹出的气泡View
	 */
	private View popView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 初始化气泡,并设置为不可见
		popView = View.inflate(this, R.layout.popview, null);
		setContentView(R.layout.mymapview);
		mapView = (MapView) findViewById(R.id.map_view);
		mapView.addView(popView, new MapView.LayoutParams(
				MapView.LayoutParams.WRAP_CONTENT,
				MapView.LayoutParams.WRAP_CONTENT, null,
				MapView.LayoutParams.BOTTOM_CENTER));
		// 由于我的气泡的尾巴是在下边居中的,因此要设置成MapView.LayoutParams.BOTTOM_CENTER.
		// 这里没有给GeoPoint,在onFocusChangeListener中设置
		popView.setVisibility(View.GONE);
		/**
		 * 创建图标资源（用于显示在overlayItem所标记的位置）
		 */
		Drawable drawable = this.getResources().getDrawable(
				R.drawable.mis_usemobile);
		// 为maker定义位置和边界
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		MyItemizedOverlay overlay = new MyItemizedOverlay(this, drawable);
		// 设置显示/隐藏泡泡的监听器
		overlay.setOnFocusChangeListener(onFocusChangeListener);
		/**
		 * 创建并添加第一个标记：深圳 世界之窗（经度：22.5348 纬度：113.97246）
		 */
		// 构造一个经纬度点
		GeoPoint point = new GeoPoint((int) (22.5348 * 1E6),
				(int) (113.97246 * 1E6));
		// 创建标记（世界之窗）
		OverlayItem overlayItem = new OverlayItem(point, "世界之窗",
				"位于中国广东省深圳市南山区华侨城的大型文化旅游景区，是深圳最为著名的旅游景点之一。");
		// 将标记添加到图层中（可添加多个OverlayItem）
		overlay.addOverlay(overlayItem);

		/**
		 * 创建并添加第二个标记：锦绣中华（经度：22.53108 纬度：113.99151）
		 */
		point = new GeoPoint((int) (22.53108 * 1E6), (int) (113.99151 * 1E6));
		// 创建标记（锦绣中华）
		overlayItem = new OverlayItem(point, "锦绣中华",
				"中国旅游胜地四十佳之一，是目前世界上最大的实景微缩景区,已入选中国世界纪录协会世界最大实景微缩景区候选世界纪录。 ");
		// 将标记添加到图层中（可添加多个OverlayItem）
		overlay.addOverlay(overlayItem);

		/**
		 * 往地图上添加自定义的ItemizedOverlay
		 */
		List<Overlay> mapOverlays = mapView.getOverlays();
		mapOverlays.add(overlay);

		// 设置地图模式为交通地图
		mapView.setStreetView(true);
		// 设置启用内置的缩放控件
		mapView.setBuiltInZoomControls(true);
		/**
		 * 取得地图控制器对象，用于控制MapView
		 */
		// 设置地图的中心
		mapView.getController().setCenter(point);
		// 设置地图默认的缩放级别
		mapView.getController().setZoom(13);

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 监听器 当一个Overlay焦点改变时触发
	 */
	private final ItemizedOverlay.OnFocusChangeListener onFocusChangeListener = new ItemizedOverlay.OnFocusChangeListener() {

		@Override
		public void onFocusChanged(ItemizedOverlay overlay, OverlayItem newFocus) {
			// 创建气泡窗口
			if (popView != null) {
				popView.setVisibility(View.GONE);
			}
			if (newFocus != null) {

				MapView.LayoutParams geoLP = (MapView.LayoutParams) popView
						.getLayoutParams();
				geoLP.point = newFocus.getPoint();// 这行用于popView的定位
				TextView title = (TextView) popView
						.findViewById(R.id.map_bubbleTitle);
				title.setText(newFocus.getTitle());

				TextView desc = (TextView) popView
						.findViewById(R.id.map_bubbleText);
				if (newFocus.getSnippet() == null
						|| newFocus.getSnippet().length() == 0) {
					desc.setVisibility(View.GONE);
				} else {
					desc.setVisibility(View.VISIBLE);
					desc.setText(newFocus.getSnippet());
				}
				mapView.updateViewLayout(popView, geoLP);
				popView.setVisibility(View.VISIBLE);
			}
		}
	};

}