package com.android.sensorsapp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.sensorsapp.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ToastView extends FrameLayout {

    @InjectView(R.id.toastIcon) ImageView mToastIcon;
    @InjectView(R.id.toastMessage) TextView mToastMessage;
    @InjectView(R.id.toastCloseBtn) ImageView mCloseBtn;

    private int iconResId;
    private String message;

    public ToastView(Context context) {
        this(context, null);
    }

    public ToastView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToastView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater)
                getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_toast, null);
        ButterKnife.inject(this, view);
        addView(view);
    }

    @OnClick(R.id.toastCloseBtn)
    public void close() {
        setVisibility(View.GONE);
    }

    public void setIcon(int iconResId) {
        this.iconResId = iconResId;
        if (mToastIcon != null) {
            mToastIcon.setImageResource(iconResId);
        }
    }

    public void setMessage(String message) {
        this.message = message;
        if (mToastMessage != null) {
            mToastMessage.setText(message);
        }
    }

}
