package roisoleil.snowforecast;

import java.util.Locale;

import roisoleil.snowforecast.adapter.PreviewAdapter;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.XmlDom;

public class NearMeSectionFragment extends ListFragment {

	protected View headerView;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
		headerView = getActivity().getLayoutInflater().inflate(R.layout.error,
				null);
		getListView().addHeaderView(headerView);
		refresh(false);
	}

	public void result(String url, XmlDom xmlDom, AjaxStatus status) {
		if (200 == status.getCode() && xmlDom != null) {
			PreviewAdapter favoriteAdapter = new PreviewAdapter(getActivity(),
					xmlDom);
			setListAdapter(favoriteAdapter);
			setListShown(true);
		} else {
			headerView.findViewById(R.id.error).setVisibility(View.VISIBLE);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_refresh:
			refresh(true);
			break;
		}
		return true;
	}

	private void refresh(boolean forced) {
		// headerView.findViewById(R.id.error).setVisibility(View.GONE);
		setListShown(false);
		long expire = 24 * 60 * 60 * 1000;
		if (forced) {
			expire = 10;
		}
		double lat = 47.548489;
		double lon = -122.154526;
		String androidId = getAndroidId();
		Location location = getLocation();
		if (location != null) {
			lat = location.getLatitude();
			lon = location.getLongitude();
		}
		AQuery aQuery = new AQuery(getActivity());
		String url = "http://www.onthesnow.com/app/skireport/nearbyareas.html?ver=2.2&lat="
				+ lat
				+ "&lon="
				+ lon
				+ "&androidid="
				+ androidId
				+ "&language="
				+ Locale.getDefault().getLanguage()
				+ "&locale="
				+ Locale.getDefault().getLanguage()
				+ "_"
				+ Locale.getDefault().getCountry();
		aQuery.ajax(url, XmlDom.class, expire, this, "result");
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.favorite, menu);
	}

	private Location getLocation() {
		LocationManager locationManager = (LocationManager) getActivity()
				.getSystemService(Context.LOCATION_SERVICE);
		return locationManager
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	}

	private String getAndroidId() {
		return Secure.getString(getActivity().getContentResolver(),
				Secure.ANDROID_ID);
	}

}
