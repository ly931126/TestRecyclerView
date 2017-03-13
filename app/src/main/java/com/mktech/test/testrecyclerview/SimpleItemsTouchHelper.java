package com.mktech.test.testrecyclerview;

import android.graphics.Canvas;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by liye on 2017/3/12.
 */

public class SimpleItemsTouchHelper extends ItemTouchHelper.Callback {
	private static final float ALPHA_FULL =1.0f ;
	private OnMoveAndSwipedListener mOnMoveAndSwipedListener;
	public SimpleItemsTouchHelper(OnMoveAndSwipedListener onMoveAndSwipedListener) {
		this.mOnMoveAndSwipedListener = onMoveAndSwipedListener;
	}
	/**
	 * 这个方法用于设置拖动的方向和侧滑的方向
	 * 
	 * @param recyclerView
	 * @param viewHolder
	 * @return
	 */
	@Override
	public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
		if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
			final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
			final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
			return makeMovementFlags(dragFlags, swipeFlags);
		} else {
			final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
			final int swipeFlags = 0;
			return makeMovementFlags(dragFlags, swipeFlags);
		}
	}
	
	/**
	 * 当我们拖动ITEM时，会回调此方法
	 * 
	 * @param recyclerView
	 * @param viewHolder
	 * @param target
	 * @return
	 */
	@Override
	public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
		if (viewHolder.getItemViewType() != target.getItemViewType()) {
			return false;
		}
		mOnMoveAndSwipedListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
		return true;
	}
	
	/**
	 * 当我们侧滑item时会回调此方法
	 * 
	 * @param viewHolder
	 * @param direction
	 */
	@Override
	public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
		mOnMoveAndSwipedListener.onIitmDismiss(viewHolder.getAdapterPosition());
	}
	
	/**
	 * 拖拽item时，改变item的背景颜色
	 * 
	 * @param viewHolder
	 * @param actionState
	 */
	@Override
	public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
		// 当前不是状态idel（空闲）状态时，说明它正在拖拽或侧滑
		if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
			// 做改变item背景颜色的操作
			// 看看这个viewholder是否实现了onstateChangeListener接口
			if (viewHolder instanceof RecyclerViewAdapter.onStateChangedListener) {
				RecyclerViewAdapter.onStateChangedListener onStateChangedListener = (RecyclerViewAdapter.onStateChangedListener) viewHolder;
				// 回调itemViewholder中的onItemSelect方法来改变选中时的背景色
				onStateChangedListener.onItemSelected();
			}
		}
		super.onSelectedChanged(viewHolder, actionState);
	}
	// 当用户拖拽完成或侧滑完一个item时回调此方法，用来清除施加在item上的一些状态
	@Override
	public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
		super.clearView(recyclerView, viewHolder);
		if (viewHolder instanceof RecyclerViewAdapter.onStateChangedListener) {
			RecyclerViewAdapter.onStateChangedListener onStateChangedListener = (RecyclerViewAdapter.onStateChangedListener) viewHolder;
			onStateChangedListener.onItemClear();
		}
	}

	/**
	 * 添加侧滑删除时item的颜色逐渐变浅的效果
	 * Math.abs(dX)表示在x方向手指滑动的距离的绝对值
	 * @param c
	 * @param recyclerView
	 * @param viewHolder
	 * @param dX
	 * @param dY
	 * @param actionState
     * @param isCurrentlyActive
     */
	// 这个方法可以判断当前是拖拽还是侧滑
	@Override
	public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
			boolean isCurrentlyActive) {
		if(actionState==ItemTouchHelper.ACTION_STATE_SWIPE){
			//根据侧滑的位移来改变背景的透明度

			final float alpha=ALPHA_FULL-Math.abs(dX)/(float)viewHolder.itemView.getWidth();
			viewHolder.itemView.setAlpha(alpha);
			viewHolder.itemView.setTranslationX(dX);

		}
		super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
	}
}
