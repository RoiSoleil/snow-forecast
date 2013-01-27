package roisoleil.snowforecast.adapter;

import roisoleil.snowforecast.Constants;
import roisoleil.snowforecast.R;
import android.app.Activity;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidquery.util.XmlDom;

public class PreviewAdapter extends XmlDomListAdapter {

	public PreviewAdapter(Activity activity, XmlDom xmlDom) {
		super(activity, xmlDom);
	}

	@Override
	public int getCount() {
		return xmlDom.children(Constants.AREA).size();
	}

	@Override
	public Object getItem(int position) {
		return xmlDom.children(Constants.AREA).get(position);
	}

	@Override
	public long getItemId(int position) {
		long l = -1;
		String id = xmlDom.children(Constants.AREA).get(position)
				.attr(Constants.ID);
		try {
			l = Long.parseLong(id);
		} catch (Exception e) {
		}
		return l;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Object item = getItem(position);
		LayoutInflater inflater = (LayoutInflater) activity.getLayoutInflater();
		View view = null;
		if (item instanceof XmlDom) {
			XmlDom xmlDom = (XmlDom) item;
			boolean isClosed = Constants.Y.equals(xmlDom
					.child(Constants.CLOSED).getElement().getTextContent());
			if (isClosed) {
				view = inflater.inflate(R.layout.preview_closed, parent, false);
			} else {
				view = inflater.inflate(R.layout.preview, parent, false);
			}
			try {
				((TextView) view.findViewById(R.id.resortName)).setText(xmlDom
						.child(Constants.AREA_NAME).getElement()
						.getTextContent());
				((TextView) view.findViewById(R.id.regionName)).setText(xmlDom
						.child(Constants.STATE).getElement().getTextContent());
				if (!isClosed) {
					((TextView) view.findViewById(R.id.newFallValue))
							.setText(xmlDom.child(Constants.NEW_SNOW)
									.child(Constants.METRIC).getElement()
									.getTextContent());
					((TextView) view.findViewById(R.id.onTopValue))
							.setText(xmlDom.child(Constants.BASE)
									.child(Constants.METRIC).getElement()
									.getTextContent());
				}
			} catch (Exception e) {
			}
		}
		return view;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isEmpty() {
		return getCount() == 0;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public boolean isEnabled(int position) {
		return true;
	}

}
