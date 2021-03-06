package roisoleil.snowforecast;

import java.util.Locale;

import roisoleil.snowforecast.adapter.PreviewAdapter;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.XmlDom;

public class RegionSectionFragment extends ListFragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
		refresh(false);
	}

	public void result(String url, XmlDom xmlDom, AjaxStatus status) {
		if (200 == status.getCode() && xmlDom != null) {
			PreviewAdapter favoriteAdapter = new PreviewAdapter(getActivity(),
					xmlDom);
			setListAdapter(favoriteAdapter);
			setListShown(true);
		} else {
			getListView().setHeaderDividersEnabled(true);
			getListView().addHeaderView(
					getActivity().getLayoutInflater().inflate(R.layout.error,
							null));
			setListShown(true);
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
		setListShown(false);
		long expire = 24 * 60 * 60 * 1000;
		if (forced) {
			expire = 10;
		}
		AQuery aQuery = new AQuery(getActivity());
		String url = "http://www.onthesnow.com/app/skireport/areas.html?ver=2.2&areas=724&androidid=5451516516516&language="
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
	
	private void createDatabase() {
		getActivity().getD
	}

}
