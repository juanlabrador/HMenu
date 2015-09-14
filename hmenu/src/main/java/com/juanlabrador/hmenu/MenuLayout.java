package com.juanlabrador.hmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

/**
 * Created by juanlabrador on 14/09/15.
 */
public class MenuLayout extends FrameLayout {

    private TypedArray mTypedArray;
    /**
     * children will be set the same size.
     */
    private int mChildSize;

    /* the distance between child Views */
    private int mChildGap;

    /* left space to place the switch button */
    private int mSpacing = 16;
    private int mPaddingBetweenIcons = 40;

    // Set duration
    private long mExpandTime = 1000;
    private long mShrinkTime = 500;

    private boolean mExpanded = false;

    private RotateAndTranslateAnimation mAnimation;

    public MenuLayout(Context context) {
        super(context);
    }

    public MenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    private static int computeChildGap(final float width, final int childCount, final int childSize, final int minGap) {
        return Math.max((int) (width / childCount - childSize), minGap);
    }

    private static Rect computeChildFrame(final boolean expanded, final int paddingLeft, final int childIndex,
                                          final int gap, final int size) {
        final int left = expanded ? (paddingLeft + childIndex * (gap + size) + gap) : ((paddingLeft - size) / 2);

        return new Rect(left, 0, left + size, size);
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return mChildSize;
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return mSpacing + (mChildSize + mPaddingBetweenIcons) * getChildCount();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(getSuggestedMinimumHeight(), MeasureSpec.EXACTLY));

        final int count = getChildCount();
        mChildGap = computeChildGap(getMeasuredWidth() - mSpacing, count, mChildSize, 0);

        for (int i = 0; i < count; i++) {
            getChildAt(i).measure(MeasureSpec.makeMeasureSpec(mChildSize, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(mChildSize, MeasureSpec.EXACTLY));
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int paddingLeft = mSpacing;
        final int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            Rect frame = computeChildFrame(mExpanded, paddingLeft, i, mChildGap, mChildSize);
            getChildAt(i).layout(frame.left, frame.top, frame.right, frame.bottom);
        }

    }

    /**
     * refers to {@link LayoutAnimationController#getDelayForView(View view)}
     */
    private long computeStartOffset(final int childCount, final boolean expanded, final int index,
                                           final float delayPercent, Interpolator interpolator) {
        final float delay = delayPercent * (mExpandTime / 2);
        final long viewDelay = (long) (getTransformedIndex(expanded, childCount, index) * delay);
        final float totalDelay = delay * childCount;

        float normalizedDelay = viewDelay / totalDelay;
        normalizedDelay = interpolator.getInterpolation(normalizedDelay);

        return (long) (normalizedDelay * totalDelay);
    }

    private static int getTransformedIndex(final boolean expanded, final int count, final int index) {
        return count - 1 - index;
    }

    private Animation createExpandAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta,
                                                   long startOffset, Interpolator interpolator) {
        mAnimation = new RotateAndTranslateAnimation(0, toXDelta, 0, toYDelta, 0, 0);
        mAnimation.setStartOffset(startOffset);
        mAnimation.setDuration(mExpandTime);
        mAnimation.setInterpolator(interpolator);
        mAnimation.setFillAfter(true);

        return mAnimation;
    }

    private Animation createShrinkAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta,
                                                   long startOffset, Interpolator interpolator) {

        mAnimation = new RotateAndTranslateAnimation(0, toXDelta, 0, toYDelta, 0, 0);
        mAnimation.setStartOffset(startOffset);
        mAnimation.setDuration(mShrinkTime);
        mAnimation.setInterpolator(interpolator);
        mAnimation.setFillAfter(true);

        return mAnimation;
    }

    private void bindChildAnimation(final View child, final int index) {
        final boolean expanded = mExpanded;
        final int childCount = getChildCount();
        Rect frame = computeChildFrame(!expanded, mSpacing, index, mChildGap, mChildSize);

        final int toXDelta = frame.left - child.getLeft();
        final int toYDelta = frame.top - child.getTop();

        Interpolator interpolator = mExpanded ? new AccelerateInterpolator() : new OvershootInterpolator(1.5f);
        final long startOffset = computeStartOffset(childCount, mExpanded, index, 0.1f, interpolator);

        mAnimation = (RotateAndTranslateAnimation) (mExpanded ? createShrinkAnimation(0, toXDelta, 0, toYDelta, startOffset,
                        interpolator) : createExpandAnimation(0, toXDelta, 0, toYDelta, startOffset, interpolator));

        final boolean isLast = getTransformedIndex(expanded, childCount, index) == childCount - 1;
        mAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isLast) {
                    postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            onAllAnimationsEnd();
                        }
                    }, 0);
                }
            }
        });

        child.setAnimation(mAnimation);
    }

    public boolean isExpanded() {
        return mExpanded;
    }

    public void setPaddingBetweenIcons(int padding) {
        mPaddingBetweenIcons = padding;
    }

    public void setChildSize(int size) {
        if (mChildSize == size || size < 0) {
            return;
        }

        mChildSize = size;

        requestLayout();
    }

    public void setDurationShrink(long duration) {
        mShrinkTime = duration;
    }

    public void setDurationExpand(long duration) {
        mExpandTime = duration;
    }

    public void setSpacing(int spacing) {
        mSpacing = spacing;
    }

    /**
     * switch between expansion and shrinkage
     *
     * @param showAnimation
     */
    public void switchState(final boolean showAnimation) {
        if (showAnimation) {
            final int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                bindChildAnimation(getChildAt(i), i);
            }
        }

        mExpanded = !mExpanded;

        if (!showAnimation) {
            requestLayout();
        }

        invalidate();
    }

    private void onAllAnimationsEnd() {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).clearAnimation();
        }

        requestLayout();
    }

}
