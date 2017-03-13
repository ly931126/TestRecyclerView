package com.mktech.test.testrecyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by liye on 2017/3/12.
 */

public class MainFragment extends ListFragment {
	private OnListItemClickListener	mOnListItemClickListener;
	private static final String		TAG	= MainFragment.class.getSimpleName();
	public interface OnListItemClickListener {
		void onListItemClick(int position);
	}
	public MainFragment() {
		
	}
	
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		this.mOnListItemClickListener = (OnListItemClickListener) getActivity();
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final String[] items = getResources().getStringArray(R.array.main_items);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
		setListAdapter(adapter);
		
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		if (mOnListItemClickListener != null) {
			mOnListItemClickListener.onListItemClick(position);
		}
	}
}
