package cn.com.example.fengquan.baselibrary.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by fengquan on 2019/5/16.
 */

public class WrapRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecyclerView.Adapter mRealAdapter; //实际条目的adapter
    ArrayList<View> mHeaderViews; // 头部
    ArrayList<View> mFooterViews; // 尾部

    public WrapRecyclerAdapter(RecyclerView.Adapter adapter) {
        mRealAdapter = adapter;
        mHeaderViews = new ArrayList<>();
        mFooterViews = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        int headersCount = getHeadersCount();
        if (position < headersCount) {
            return createHeaderOrFooterViewHolder(mHeaderViews.get(position));
        }

        int realPosition = position - headersCount;
        int itemCount = 0;
        if (mRealAdapter != null) {
            itemCount = mRealAdapter.getItemCount();
            if (realPosition < itemCount) {
                return mRealAdapter.onCreateViewHolder(parent, mRealAdapter.getItemViewType(realPosition));
            }
        }

        return createHeaderOrFooterViewHolder(mFooterViews.get(realPosition - itemCount));
    }

    private RecyclerView.ViewHolder createHeaderOrFooterViewHolder(View view) {
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int headersCount = getHeadersCount();
        if (position < headersCount) {
            return;
        }

        int realPosition = position - headersCount;
        if (mRealAdapter != null) {
            int itemCount = mRealAdapter.getItemCount();
            if (realPosition < itemCount) {
                mRealAdapter.onBindViewHolder(holder, realPosition);
            }
        }

    }

    @Override
    public int getItemCount() {
        return mRealAdapter.getItemCount() + mHeaderViews.size() + mFooterViews.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    public int getFootersCount() {
        return mFooterViews.size();
    }

    /**
     * 添加底部View
     * @param view
     */
    public void addFooterView(View view) {
        if (!mFooterViews.contains(view)) {
            mFooterViews.add(view);
            notifyDataSetChanged();
        }
    }

    /**
     * 添加头部View
     * @param view
     */
    public void addHeaderView(View view) {
        if (!mHeaderViews.contains(view)) {
            mHeaderViews.add(view);
            notifyDataSetChanged();
        }
    }

    /**
     * 移除底部View
     * @param view
     */
    public void removeFooterView(View view) {
        if (mFooterViews.contains(view)) {
            mFooterViews.remove(view);
            notifyDataSetChanged();
        }
    }

    /**
     * 移除头部View
     * @param view
     */
    public void removeHeaderView(View view) {
        if (mHeaderViews.contains(view)) {
            mHeaderViews.remove(view);
            notifyDataSetChanged();
        }
    }
}
