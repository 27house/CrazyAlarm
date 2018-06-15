package com.sunn.xhui.crazyalarm.ui.widget.list;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class MyRecyclerView extends RecyclerView {

    LinearLayoutManager layoutManager;
    ScrollCallback scrollCallback;
    ItemToTopCallback itemToTopCallback;

    public void setScrollCallback(ScrollCallback scrollCallback) {
        this.scrollCallback = scrollCallback;
    }

    public MyRecyclerView(Context context) {
        super(context);
        init();
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * @param layoutManager GridLayoutManager或LinearLayoutManager
     */
    public void setLayoutM(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        setLayoutManager(this.layoutManager);
    }

    private boolean isUp = true;

    public void setUp(boolean up) {
        isUp = up;
    }

    public void init() {
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (scrollCallback == null || layoutManager == null) {
                    return;
                }
                int firstItem = layoutManager.findFirstCompletelyVisibleItemPosition();
                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                // findOneVisibleChild == null 时 firstItem 返回 -1
                if (firstItem < 0) {
                    return;
                }
                scrollCallback.scrollToTop(firstItem <= scrollCallback.topPos);
                scrollCallback.scrollToBottom(lastVisibleItem >= totalItemCount - scrollCallback.bottomPos && dy > 0);

                // 先触发上面的事件
                if (dy < -30) {
                    if (!isUp) {
                        // 记录上次是向上还是向下，若已回调过一次相同的结果就不必再次回掉，避免UI频繁的判断
                        scrollCallback.scrollUp(true);
                        isUp = true;
                    }
                } else if (dy > 30) {
                    if (isUp) {
                        scrollCallback.scrollUp(false);
                        isUp = false;
                    }
                }

                if (itemToTopCallback != null) {
                    itemToTopCallback.scrollToItem(firstItem > itemToTopCallback.itemPos);
                }
            }
        });
    }

    public void setItemToTopCallback(ItemToTopCallback itemToTopCallback) {

        this.itemToTopCallback = itemToTopCallback;
    }

    public abstract static class ItemToTopCallback {

        int itemPos;

        public void setItemPos(int itemPos) {
            this.itemPos = itemPos;
        }

        public ItemToTopCallback(int itemPos) {
            this.itemPos = itemPos;
        }

        public void scrollToItem(boolean isTop) {

        }
    }

    public abstract static class ScrollCallback {

        int topPos = 4, bottomPos = 1;

        /**
         * @param posA 第一个是topPos，第二个是bottomPos
         */
        protected ScrollCallback(int... posA) {
            // 第一个是topPos
            topPos = posA[0];
            // 第二个是bottomPos
            if (posA.length >= 2) {
                bottomPos = posA[1];
            }
        }

        protected ScrollCallback() {
        }

        /**
         * 滚动到第二条
         *
         * @param isShow 前两条是否显示
         */
        public void scrollToTop(boolean isShow) {
        }

        /**
         * 滚动到底部倒数第二条
         *
         * @param isBottom 。
         */
        public void scrollToBottom(boolean isBottom) {
        }

        /**
         * 是否向上滚动了
         *
         * @param isUp true：向上滚动了; false : 向下滚动了--小幅度滚动不回调此方法
         */
        public void scrollUp(boolean isUp) {
        }
    }
}
