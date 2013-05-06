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
	 * ��ͼView
	 */
	protected MapView mapView;
	/**
	 * ����������View
	 */
	private View popView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ��ʼ������,������Ϊ���ɼ�
		popView = View.inflate(this, R.layout.popview, null);
		setContentView(R.layout.mymapview);
		mapView = (MapView) findViewById(R.id.map_view);
		mapView.addView(popView, new MapView.LayoutParams(
				MapView.LayoutParams.WRAP_CONTENT,
				MapView.LayoutParams.WRAP_CONTENT, null,
				MapView.LayoutParams.BOTTOM_CENTER));
		// �����ҵ����ݵ�β�������±߾��е�,���Ҫ���ó�MapView.LayoutParams.BOTTOM_CENTER.
		// ����û�и�GeoPoint,��onFocusChangeListener������
		popView.setVisibility(View.GONE);
		/**
		 * ����ͼ����Դ��������ʾ��overlayItem����ǵ�λ�ã�
		 */
		Drawable drawable = this.getResources().getDrawable(
				R.drawable.mis_usemobile);
		// Ϊmaker����λ�úͱ߽�
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		MyItemizedOverlay overlay = new MyItemizedOverlay(this, drawable);
		// ������ʾ/�������ݵļ�����
		overlay.setOnFocusChangeListener(onFocusChangeListener);
		/**
		 * ��������ӵ�һ����ǣ����� ����֮�������ȣ�22.5348 γ�ȣ�113.97246��
		 */
		// ����һ����γ�ȵ�
		GeoPoint point = new GeoPoint((int) (22.5348 * 1E6),
				(int) (113.97246 * 1E6));
		// ������ǣ�����֮����
		OverlayItem overlayItem = new OverlayItem(point, "����֮��",
				"λ���й��㶫ʡ��������ɽ�����ȳǵĴ����Ļ����ξ�������������Ϊ���������ξ���֮һ��");
		// �������ӵ�ͼ���У�����Ӷ��OverlayItem��
		overlay.addOverlay(overlayItem);

		/**
		 * ��������ӵڶ�����ǣ������л������ȣ�22.53108 γ�ȣ�113.99151��
		 */
		point = new GeoPoint((int) (22.53108 * 1E6), (int) (113.99151 * 1E6));
		// ������ǣ������л���
		overlayItem = new OverlayItem(point, "�����л�",
				"�й�����ʤ����ʮ��֮һ����Ŀǰ����������ʵ��΢������,����ѡ�й������¼Э���������ʵ��΢��������ѡ�����¼�� ");
		// �������ӵ�ͼ���У�����Ӷ��OverlayItem��
		overlay.addOverlay(overlayItem);

		/**
		 * ����ͼ������Զ����ItemizedOverlay
		 */
		List<Overlay> mapOverlays = mapView.getOverlays();
		mapOverlays.add(overlay);

		// ���õ�ͼģʽΪ��ͨ��ͼ
		mapView.setStreetView(true);
		// �����������õ����ſؼ�
		mapView.setBuiltInZoomControls(true);
		/**
		 * ȡ�õ�ͼ�������������ڿ���MapView
		 */
		// ���õ�ͼ������
		mapView.getController().setCenter(point);
		// ���õ�ͼĬ�ϵ����ż���
		mapView.getController().setZoom(13);

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * ������ ��һ��Overlay����ı�ʱ����
	 */
	private final ItemizedOverlay.OnFocusChangeListener onFocusChangeListener = new ItemizedOverlay.OnFocusChangeListener() {

		@Override
		public void onFocusChanged(ItemizedOverlay overlay, OverlayItem newFocus) {
			// �������ݴ���
			if (popView != null) {
				popView.setVisibility(View.GONE);
			}
			if (newFocus != null) {

				MapView.LayoutParams geoLP = (MapView.LayoutParams) popView
						.getLayoutParams();
				geoLP.point = newFocus.getPoint();// ��������popView�Ķ�λ
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