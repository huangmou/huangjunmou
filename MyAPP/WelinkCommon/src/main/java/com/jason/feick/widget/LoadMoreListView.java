 package com.jason.feick.widget;

 import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

 public class LoadMoreListView extends ListView
   implements AbsListView.OnScrollListener, View.OnClickListener
 {
   private final String TAG = getClass().getSimpleName();
   private Context context;
   private int currentPage = 0;
 
   private int totalPage = 2147483647;
 
   private int pageSize = 10;
   private View loadMoreView;
   private OnLoadMoreDataListener onLoadMoreDataListener;
   private int visibleLastIndex = 0;
   private int visibleItemCount;
   private boolean isLoadFinshed = true;
   private ListAdapter adapter;
   private boolean isAutoLoadMore = false;
 
   private boolean autoCorrectCurrentPage = false;
 
   private DataSetObserver observer = new DataSetObserver()
   {
     public void onChanged()
     {
       super.onChanged();
       LoadMoreListView.this.loadFinished();
     }
   };
 
   public LoadMoreListView(Context context)
   {
    super(context);
     this.context = context;
   }
 
   public LoadMoreListView(Context context, AttributeSet attrs) {
     super(context, attrs);
     this.context = context;
   }
 
   public LoadMoreListView(Context context, AttributeSet attrs, int defStyle) {
     super(context, attrs, defStyle);
     this.context = context;
   }
 
   public void setLoadMoreView(View loadMoreView)
   {
     this.loadMoreView = loadMoreView;
   }
 
   public void setOnLoadMoreDataListener(OnLoadMoreDataListener onLoadMoreDataListener)
   {
     this.onLoadMoreDataListener = onLoadMoreDataListener;
     if (this.loadMoreView != null)
       this.loadMoreView.setOnClickListener(this);
   }
 
   public void setAutoLoadMore(boolean isAutoLoadMore)
   {
     this.isAutoLoadMore = isAutoLoadMore;
   }
 
   public void setTotalPage(int totalPage)
   {
     if (totalPage < 1)
      totalPage = 1;
     else
       this.totalPage = totalPage;
   }
 
   public void setTotalSize(long totalSize)
   {
     if (totalSize < 0L) {
       totalSize = 0L;
     }
     int tatalPage = 2147483647;
     try {
       tatalPage = (int)(totalSize / this.pageSize);
     } catch (Exception e) {
       android.util.Log.w(this.TAG, "tatalPage 超过 int 最大值");
     }
 
     if (totalSize % this.pageSize == 0L)
      setTotalPage(tatalPage);
     else
      setTotalPage(tatalPage + 1);
   }
 
   public int getCurrentPage()
   {
    return this.currentPage;
   }
 
   public int getNextPage()
   {
     return this.currentPage + 1;
   }
 
   public int getPageSize()
   {
    return this.pageSize;
   }
 
   public void setPageSize(int pageSize)
   {
    long totalSize = this.pageSize * this.totalPage;
 
     if (pageSize < 1)
       this.pageSize = 10;
     else {
       this.pageSize = pageSize;
     }
 
     setTotalSize(totalSize);
   }
 
   public void openAutoCorrectCurrentPage(int pageSize)
   {
     setPageSize(pageSize);
     this.autoCorrectCurrentPage = true;
   }
 
   public void closeAutoCorrectCurrentPage()
   {
     this.autoCorrectCurrentPage = false;
   }
 
   private void loadFinished()
   {
     if (this.onLoadMoreDataListener != null) {
       this.onLoadMoreDataListener.loadMoreFinish(this.loadMoreView);
     }
 
      correctCurrentPage();
      this.isLoadFinshed = true;
   }
 
   private void correctCurrentPage()
   {
     if (this.adapter != null) {
        if (this.autoCorrectCurrentPage) {
          int count = this.adapter.getCount();
          if (count % this.pageSize == 0)
           this.currentPage = (count / this.pageSize);
         else
            this.currentPage = (count / this.pageSize + 1);
       }
       else
       {
         this.currentPage += 1;
       }
     }
     else this.currentPage = 0;
 
      if ((this.currentPage >= this.totalPage) || (this.totalPage == 1))
       try {
         super.removeFooterView(this.loadMoreView);
       }
       catch (Exception localException)
       {
       }
   }
 
   public void loadDataError()
   {
     loadFinished();
   }
 
   public void setAdapter(ListAdapter adapter)
   {
    if (this.adapter != null) {
       if (this.loadMoreView != null) {
          if (this.onLoadMoreDataListener != null)
            this.onLoadMoreDataListener.loadMoreFinish(this.loadMoreView);
         try
         {
           super.removeFooterView(this.loadMoreView);
         } catch (Exception localException) {
         }
       }
       try {
          this.adapter.unregisterDataSetObserver(this.observer);
       }
       catch (Exception localException1) {
       }
     }
      this.adapter = adapter;
      if ((this.loadMoreView != null) && (adapter != null)) {
       try {
         removeFooterView(this.loadMoreView);
         super.addFooterView(this.loadMoreView);
       } catch (Exception localException2) {
       }
       this.currentPage = 0;
       correctCurrentPage();
       this.loadMoreView.setOnClickListener(this);
       setOnScrollListener(this);
       adapter.registerDataSetObserver(this.observer);
     }
     if (adapter == null) {
       this.currentPage = 0;
     }
     super.setAdapter(adapter);
   }
 
   public void addFooterView(View v)
   {
   }
 
   public void onScroll(AbsListView arg0, int firstVisibleItem, int visibleItemCount, int totalItemCount)
   {
     this.visibleItemCount = visibleItemCount;
     this.visibleLastIndex = (firstVisibleItem + visibleItemCount - 1);
   }
 
   public void onScrollStateChanged(AbsListView view, int scrollState)
   {
     if (this.adapter != null) {
      int itemsLastIndex = this.adapter.getCount() - 1;
       int lastIndex = itemsLastIndex + 1;
       if ((scrollState == 0) && 
         (this.visibleLastIndex == lastIndex))
       {
         if ((this.isAutoLoadMore) && (this.onLoadMoreDataListener != null) && 
           (this.loadMoreView != null) && (this.currentPage < this.totalPage) && 
           (this.isLoadFinshed)) {
           this.onLoadMoreDataListener.loadMore(this.loadMoreView);
           this.isLoadFinshed = false;
         }
       }
     }
   }
 
   public void onClick(View v)
   {
     if ((this.isLoadFinshed) && (this.onLoadMoreDataListener != null)) {
       this.onLoadMoreDataListener.loadMore(this.loadMoreView);
       this.isLoadFinshed = false;
     }
   }
 
   public static abstract interface OnLoadMoreDataListener
   {
     public abstract void loadMore(View paramView);
 
     public abstract void loadMoreFinish(View paramView);
   }
 }
