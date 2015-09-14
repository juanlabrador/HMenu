package com.juanlabrador.hmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by juanlabrador on 14/09/15.
 */
public class Menu extends RelativeLayout {

    private int mSpacing;
    private int mIconSize;
    private int mChildSize;
    private int mPaddingBetweenIcons;
    private  float mScale;
    private MenuLayout mMenuLayout;
    private HorizontalScrollView mScroll;
    private TypedArray mTypedArray;
    private FrameLayout mControlLayout;

    // Hint
    private ImageView mHintView;
    private Drawable mIconHint;

    public Menu(Context context) {
        super(context);
    }

    public Menu(Context context, AttributeSet attrs) {
        super(context, attrs);

        mScale = getResources().getDisplayMetrics().density;
        if (attrs != null) {
            mTypedArray = getContext().obtainStyledAttributes(attrs, R.styleable.HMenu, 0, 0);
            mIconHint = mTypedArray.getDrawable(R.styleable.HMenu_icon);
            mChildSize = (int) Math.max(mTypedArray.getDimension(R.styleable.HMenu_iconChildSize, (int) (26 * mScale + 0.5f)), 0);
            mIconSize = (int) Math.max(mTypedArray.getDimension(R.styleable.HMenu_iconSize, (int) (32 * mScale + 0.5f)), 0);
            mPaddingBetweenIcons = mTypedArray.getInteger(R.styleable.HMenu_paddingBetweenIcons, 40);
            mSpacing = (int) Math.max(mTypedArray.getDimension(R.styleable.HMenu_spacing, 5), 0);
            mTypedArray.recycle();
        }

        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        setClipChildren(false);

        LayoutInflater mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayoutInflater.inflate(R.layout.menu, this);

        mScroll = (HorizontalScrollView) findViewById(R.id.scroll);

        mMenuLayout = (MenuLayout) findViewById(R.id.item_layout);
        mMenuLayout.setChildSize(mChildSize);
        mMenuLayout.setPaddingBetweenIcons(mPaddingBetweenIcons);
        mMenuLayout.setSpacing(mSpacing);

        mControlLayout = (FrameLayout) findViewById(R.id.control_layout);

        if (mIconSize != 0) mControlLayout.setLayoutParams(new LayoutParams(mIconSize, mIconSize));
        else mControlLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        mControlLayout.setClickable(true);
        mControlLayout.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mHintView.startAnimation(createHintSwitchAnimation(mMenuLayout.isExpanded()));
                    mMenuLayout.switchState(true);
                }
                return false;
            }
        });

        mHintView = (ImageView) findViewById(R.id.control_hint);
        if (mIconHint != null)
            mHintView.setImageDrawable(mIconHint);
    }

    public void openMenu() {
        mHintView.startAnimation(createHintSwitchAnimation(mMenuLayout.isExpanded()));
        mMenuLayout.switchState(true);
    }

    /**------------------------------------------------------------------------*/
    /**-------------------------- Public Methods ------------------------------*/

    /**
     * Add icon via programmatically
     * @param drawable
     */
    public void setIcon(Drawable drawable) {
        if (mHintView != null)
            mHintView.setImageDrawable(drawable);
    }

    /**
     * Add icon via programmatically
     * @param bm
     */
    public void setIcon(Bitmap bm) {
        if (mHintView != null)
            mHintView.setImageBitmap(bm);
    }

    /**
     * Add icon via programmatically
     * @param res
     */
    public void setIcon(int res) {
        if (mHintView != null)
            mHintView.setImageResource(res);
    }

    /**
     * set size child icons
     * @param size
     */
    public void setChildSize(int size) {
        mChildSize = (int) (size * mScale + 0.5f);
    }

    public void addItem(View item, OnClickListener listener) {
        mMenuLayout.addView(item);
        item.setOnClickListener(getItemClickListener(listener));
    }

    private OnClickListener getItemClickListener(final OnClickListener listener) {
        return new OnClickListener() {

            @Override
            public void onClick(final View viewClicked) {
                Animation animation = bindItemAnimation(viewClicked, true, 400);
                animation.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                itemDidDisappear();
                            }
                        }, 0);
                    }
                });

                final int itemCount = mMenuLayout.getChildCount();
                for (int i = 0; i < itemCount; i++) {
                    View item = mMenuLayout.getChildAt(i);
                    if (viewClicked != item) {
                        bindItemAnimation(item, false, 300);
                    }
                }

                mMenuLayout.invalidate();
                mHintView.startAnimation(createHintSwitchAnimation(true));

                if (listener != null) {
                    listener.onClick(viewClicked);
                }
            }
        };
    }

    private Animation bindItemAnimation(final View child, final boolean isClicked, final long duration) {
        Animation animation = createItemDisapperAnimation(duration, isClicked);
        child.setAnimation(animation);

        return animation;
    }

    private void itemDidDisappear() {
        final int itemCount = mMenuLayout.getChildCount();
        for (int i = 0; i < itemCount; i++) {
            View item = mMenuLayout.getChildAt(i);
            item.clearAnimation();
        }

        mMenuLayout.switchState(false);
    }

    private static Animation createItemDisapperAnimation(final long duration, final boolean isClicked) {
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(new ScaleAnimation(1.0f, isClicked ? 2.0f : 0.0f, 1.0f, isClicked ? 2.0f : 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f));
        animationSet.addAnimation(new AlphaAnimation(1.0f, 0.0f));

        animationSet.setDuration(duration);
        animationSet.setInterpolator(new DecelerateInterpolator());
        animationSet.setFillAfter(true);

        return animationSet;
    }

    /**
     * Animation in hint button
     * @param expanded
     * @return
     */
    private Animation createHintSwitchAnimation(final boolean expanded) {
        Animation animation = new RotateAnimation(expanded ? 45 : 0, expanded ? 0 : 45, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setStartOffset(0);
        animation.setDuration(500);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setFillAfter(true);

        if (!expanded)
            mScroll.fullScroll(HorizontalScrollView.FOCUS_LEFT);

        return animation;
    }

}

