package com.mktech.test.testrecyclerview;

/**
 * Created by liye on 2017/3/12.
 */

public interface OnMoveAndSwipedListener {
    boolean onItemMove(int fromPosition ,int toPosition);
    void onIitmDismiss(int position);
}
