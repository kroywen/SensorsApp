package com.android.sensorsapp.ui.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.sensorsapp.R;

public class TabBarView extends LinearLayout implements View.OnClickListener {

    public interface OnTabChangedListener {
        void onTabChanged(int index);
    }

    private OnTabChangedListener mListener;
    private int mCurrentTabIndex = -1;

    public TabBarView(Context context) {
        super(context);
    }

    public TabBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addTab(String title, int iconResId) {
        TabView tabView = new TabView(getContext());
        tabView.setTitle(title);
        tabView.setIcon(iconResId);
        tabView.setOnClickListener(this);
        addView(tabView);
    }

    public void setCurrentTab(int index) {
        View v = getChildAt(index);
        onClick(v);
    }

    @Override
    public void onClick(View v) {
        TabView tabView = (TabView) v;
        if (tabView.isSelected()) {
            return;
        }

        mCurrentTabIndex = indexOfChild(v);
        for (int i=0; i<getChildCount(); i++) {
            getChildAt(i).setSelected(false);
        }
        tabView.setSelected(true);
        if (mListener != null) {
            mListener.onTabChanged(mCurrentTabIndex);
        }
    }

    public void setOnTabChangedListener(OnTabChangedListener listener) {
        mListener = listener;
    }

    public int getCurrentTabIndex() {
        return mCurrentTabIndex;
    }

    class TabView extends FrameLayout {

        private TextView mTitleView;
        private ImageView mIconView;

        public TabView(Context context) {
            this(context, null);
        }

        public TabView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        private void init() {
            setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f));

            LayoutInflater inflater = (LayoutInflater)
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View contentView = inflater.inflate(R.layout.view_tab, null);

            mTitleView = (TextView) contentView.findViewById(R.id.title);
            mIconView = (ImageView) contentView.findViewById(R.id.icon);
            addView(contentView);
        }

        public void setTitle(String title) {
            mTitleView.setText(title);
            mTitleView.setVisibility(!TextUtils.isEmpty(title) ? View.VISIBLE : View.INVISIBLE);
        }

        public void setIcon(int iconResId) {
            mIconView.setImageResource(iconResId);
            mIconView.setVisibility(iconResId > 0 ? View.VISIBLE : View.INVISIBLE);
        }

    }
}
