/*
 * Created by Spreadst
 */

package com.android.launcher3;

import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;

/**
 * TextView that draws a bubble behind the text. We cannot use a LineBackgroundSpan
 * because we want to make the bubble taller than the text and TextView's clip is
 * too aggressive.
 */
public class CalendarBubbleTextView extends BubbleTextView{

    private Resources mRes;
    private Context mContext;
    private Bitmap mBackgroundBitmap;
    private Bitmap mBitmap;
    private IntentFilter mFilter;
    private Launcher mLauncher;
    private Canvas mCanvas;
    private Handler mHandler;
    private int mLastDay;
    private int mCalendarWidth;
    private int mCalendarHeight;

    private Integer[] mThumbIds = {
        R.drawable.ic_date1,
        R.drawable.ic_date2,
        R.drawable.ic_date3,
        R.drawable.ic_date4,
        R.drawable.ic_date5,
        R.drawable.ic_date6,
        R.drawable.ic_date7,
        R.drawable.ic_date8,
        R.drawable.ic_date9,
        R.drawable.ic_date10,
        R.drawable.ic_date11,
        R.drawable.ic_date12,
        R.drawable.ic_date13,
        R.drawable.ic_date14,
        R.drawable.ic_date15,
        R.drawable.ic_date16,
        R.drawable.ic_date17,
        R.drawable.ic_date18,
        R.drawable.ic_date19,
        R.drawable.ic_date20,
        R.drawable.ic_date21,
        R.drawable.ic_date22,
        R.drawable.ic_date23,
        R.drawable.ic_date24,
        R.drawable.ic_date25,
        R.drawable.ic_date26,
        R.drawable.ic_date27,
        R.drawable.ic_date28,
        R.drawable.ic_date29,
        R.drawable.ic_date30,
        R.drawable.ic_date31
        };

    public CalendarBubbleTextView(Context context) {
        this(context, null, 0);
    }

    public CalendarBubbleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarBubbleTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mLauncher = (Launcher) context;
        mContext = context;
        mRes = context.getResources();
        init();
    }

    private void init(){
        mFilter = new IntentFilter();
        mFilter.addAction(Intent.ACTION_TIME_TICK);
        mFilter.addAction(Intent.ACTION_TIME_CHANGED);
        mFilter.addAction(Intent.ACTION_DATE_CHANGED);
        mFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        mBackgroundBitmap = BitmapFactory.decodeResource(mRes, R.drawable.ic_calendar_plate);
        mCalendarWidth = mBackgroundBitmap.getWidth();
        mCalendarHeight = mBackgroundBitmap.getHeight();
        mBitmap = Bitmap.createBitmap(mCalendarWidth,  mCalendarHeight, Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        if (mHandler == null) {
            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                     super.handleMessage(msg);
                     updateCalendarDate();
                     Message msgUp = mHandler.obtainMessage();
                     mHandler.sendMessageDelayed(msgUp, 60000);
                 }
            };
            Message msgUp = mHandler.obtainMessage();
            mHandler.sendMessageDelayed(msgUp, 60000);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mContext.registerReceiver(mBroadcastReceiver, mFilter);
        /* SPRD: add for bug 526593 to update date @{ */
        updateCalendarDate();
        /* @} */
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mContext.unregisterReceiver(mBroadcastReceiver);
        updateCalendarDate();
    }

    private int getTodayDate(){
        Calendar c = Calendar.getInstance();
        int date = c.get(Calendar.DATE);
        return date;
    }

    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(mLastDay == getTodayDate()){
                return;
            }else{
                updateCalendarDate();
                invalidate();
            }
        }
    };

    public void applyFromShortcutInfo(ShortcutInfo info, IconCache iconCache, boolean promiseStateChanged) {
        super.applyFromShortcutInfo(info, iconCache, promiseStateChanged);
        updateCalendarDate();
    }

    public void applyFromApplicationInfo(AppInfo info) {
        super.applyFromApplicationInfo(info);
        updateCalendarDate();
    }

    private void updateCalendarDate() {
        mLastDay = getTodayDate();
        Bitmap todayBitmap = BitmapFactory.decodeResource(mRes, mThumbIds[mLastDay-1]);

        Drawable d = getCompoundDrawables()[1];
        if (d != null) {
            if(mBitmap != null) {
                mBitmap.recycle();
                mBitmap = null;
            }
            mBitmap = Bitmap.createBitmap(mCalendarWidth, mCalendarHeight, Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
            mCanvas.drawBitmap(mBackgroundBitmap, 0, 0, null);
            mCanvas.drawBitmap(todayBitmap, 0 ,0, null);
            d = mLauncher.createIconDrawable(mBitmap);
            if (mIconSize != -1) {
                d.setBounds(0, 0, mIconSize, mIconSize);
            }
            setCompoundDrawables(null , d, null, null);
        }
    }
}
