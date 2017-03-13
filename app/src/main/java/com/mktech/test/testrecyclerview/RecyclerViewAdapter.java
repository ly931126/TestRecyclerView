package com.mktech.test.testrecyclerview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by liye on 2017/3/12.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder> implements OnMoveAndSwipedListener {
	SimpleItemsTouchHelper		mItemsTouchHelper;
	private List<String>		mItems	= new ArrayList<>();
	onStartDragListener			mOnStartDragListener;
	private static final String	TAG		= RecyclerViewAdapter.class.getSimpleName();
	public RecyclerViewAdapter(Context context, onStartDragListener onStartDragListener) {
		mItems.addAll(Arrays.asList(context.getResources().getStringArray(R.array.another_items)));
		this.mOnStartDragListener = onStartDragListener;
	}
	// 反射出我们ITEM的布局
	@Override
	public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, null);
		return new ItemViewHolder(view);
	}
	
	/**
	 * 为布局的控件添加数据
	 * 
	 * @param holder
	 * @param position
	 */
	@Override
	public void onBindViewHolder(final ItemViewHolder holder, int position) {
		holder.mTextView.setText(mItems.get(position));
		holder.mHandle.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				//如果按下，回调startDrag()方法，执行拖拽操作
				if(MotionEventCompat.getActionMasked(motionEvent)==MotionEvent.ACTION_DOWN){
					mOnStartDragListener.startDrag(holder);
				}
				return false;
			}
		});
	}
	
	/**
	 *
	 * 返回数据个数
	 * 
	 * @return
	 */
	@Override
	public int getItemCount() {
		return mItems.size();
	}
	
	@Override
	public boolean onItemMove(int fromPosition, int toPosition) {
		// 交换mItem 数据的位置
		Collections.swap(mItems, fromPosition, toPosition);
		// 交换Recyclerview列表中数据的位置
		notifyItemMoved(fromPosition, toPosition);
		return true;
	}
	
	@Override
	public void onIitmDismiss(int position) {
		// 删除mItem的数据
		mItems.remove(position);
		// 删除Recyclerview列表对应的item
		notifyItemRemoved(position);
	}
	
	/**
	 * 相当于ListView里的ViewHolder
	 */
	public static class ItemViewHolder extends RecyclerView.ViewHolder  implements  onStateChangedListener {
		private TextView	mTextView;
		private ImageView	mHandle;
		public ItemViewHolder(View itemView) {
			super(itemView);
			mTextView = (TextView) itemView.findViewById(R.id.text);
			mHandle = (ImageView) itemView.findViewById(R.id.handle);
		}

		@Override
		public void onItemSelected() {
			itemView.setBackgroundColor(Color.BLUE);
		}

		@Override
		public void onItemClear() {
			//恢复item的背景颜色
			itemView.setBackgroundColor(0);
		}
	}
	public interface onStartDragListener {
		void startDrag(RecyclerView.ViewHolder viewHolder);
	}
	public interface onStateChangedListener {
		void onItemSelected();
		void onItemClear();
	}
}
