package com.daemon.customview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.daemon.airticket.R;


public class SegmentedGroup extends RadioGroup {

    private int mMarginDp;
    private Resources resources;
    private int mTintColor;
    private int mUncheckedTintColor;
    private int mCheckedTextColor = Color.WHITE;
    private LayoutSelector mLayoutSelector;
    private Float mCornerRadius;

    public SegmentedGroup(Context context) {
        super(context);
        resources = getResources();
        mTintColor = resources.getColor(R.color.segment_button_selected_color);
        mUncheckedTintColor = resources.getColor(R.color.segment_button_unselected_color);
        mMarginDp = (int) getResources().getDimension(R.dimen.radio_button_stroke_border);
        mCornerRadius = getResources().getDimension(R.dimen.radio_button_conner_radius);
        mLayoutSelector = new LayoutSelector(mCornerRadius);
    }

    /* Reads the attributes from the layout */
    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SegmentedGroup,
                0, 0);

        try {
            mMarginDp = (int) typedArray.getDimension(
                    R.styleable.SegmentedGroup_sc_border_width,
                    getResources().getDimension(R.dimen.radio_button_stroke_border));

            mCornerRadius = typedArray.getDimension(
                    R.styleable.SegmentedGroup_sc_corner_radius,
                    getResources().getDimension(R.dimen.radio_button_conner_radius));

            mTintColor = typedArray.getColor(
                    R.styleable.SegmentedGroup_sc_tint_color,
                    getResources().getColor(R.color.segment_button_selected_color));

            mUncheckedTintColor = typedArray.getColor(
            R.styleable.SegmentedGroup_sc_unchecked_tint_color,
            getResources().getColor(R.color.segment_button_unselected_color));

            mCheckedTextColor = typedArray.getColor(
                    R.styleable.SegmentedGroup_sc_checked_text_color,
                    getResources().getColor(android.R.color.white));



        } finally {
            typedArray.recycle();
        }
    }

    public SegmentedGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        resources = getResources();
        mTintColor = resources.getColor(R.color.segment_button_selected_color);
        mUncheckedTintColor = resources.getColor(R.color.segment_button_unselected_color);
        mMarginDp = (int) getResources().getDimension(R.dimen.radio_button_stroke_border);
        mCornerRadius = getResources().getDimension(R.dimen.radio_button_conner_radius);
        initAttrs(attrs);
        mLayoutSelector = new LayoutSelector(mCornerRadius);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //Use holo light for default
        updateBackground();
    }

    public void setTintColor(int tintColor) {
        mTintColor = tintColor;
        updateBackground();
    }

    public void setTintColor(int tintColor, int checkedTextColor) {
        mTintColor = tintColor;
        mCheckedTextColor = checkedTextColor;
        updateBackground();
    }

    public int getVisibleChildCount() {
        int originalCount = getChildCount();

        int visibleCount = 0;

        for (int i = 0; i < originalCount; i++) {
            if (SegmentedGroup.this.getChildAt(i).getVisibility() == View.VISIBLE) {
                visibleCount++;
            }
        }

        return visibleCount;
    }

    private int indexOfVisibleChild(View child) {

        int originalIndex = indexOfChild(child);

        int visibleIndex = -1;

        for (int i = 0; i <= originalIndex; i++) {
            if (SegmentedGroup.this.getChildAt(i).getVisibility() == View.VISIBLE) {
                visibleIndex++;
            }
        }

        return visibleIndex;
    }

    private View getVisibleChildAt(int index) {
        View result = null;

        if (index >= 0) {
            int visibleIndex = -1;
            for (int i = 0; i < getChildCount(); i++) {

                View child = getChildAt(i);

                if (child.getVisibility() == View.VISIBLE) {
                    visibleIndex++;

                    if (index == visibleIndex) {
                        result = child;
                        break;
                    }
                }
            }
        }

        return result;
    }

    public void updateBackground() {

        int count = getVisibleChildCount();

        for (int i = 0; i < count; i++) {
            View child = getVisibleChildAt(i);

            updateBackground(child);

            // If this is the last view, don't set LayoutParams
            if (i == count - 1) break;

            LayoutParams initParams = (LayoutParams) child.getLayoutParams();
            LayoutParams params = new LayoutParams(initParams.width, initParams.height, initParams.weight);
            // Check orientation for proper margins
            if (getOrientation() == LinearLayout.HORIZONTAL) {
                params.setMargins(0, 0, -mMarginDp, 0);
            } else {
                params.setMargins(0, 0, 0, -mMarginDp);
            }
            child.setLayoutParams(params);
        }

        /*
        int count = super.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            updateBackground(child);

            // If this is the last view, don't set LayoutParams
            if (i == count - 1) break;

            LayoutParams initParams = (LayoutParams) child.getLayoutParams();
            LayoutParams params = new LayoutParams(initParams.width, initParams.height, initParams.weight);
            // Check orientation for proper margins
            if (getOrientation() == LinearLayout.HORIZONTAL) {
                params.setMargins(0, 0, -mMarginDp, 0);
            } else {
                params.setMargins(0, 0, 0, -mMarginDp);
            }
            child.setLayoutParams(params);
        }
        */
    }

    private void updateBackground(View view) {
        int checked = mLayoutSelector.getSelected();
        int unchecked = mLayoutSelector.getUnselected();

        //Set text color
//        ColorStateList colorStateList = new ColorStateList(new int[][]{
//                {android.R.attr.state_pressed},
//                {-android.R.attr.state_pressed, -android.R.attr.state_checked},
//                {-android.R.attr.state_pressed, android.R.attr.state_checked}},
//                new int[]{mTintColor, mTintColor, mCheckedTextColor});
//        ((Button) view).setTextColor(colorStateList);

//        ((Button) view).setTextColor(getResources().getColor(R.drawable.button_text_color));

        //Redraw with tint color
        Drawable checkedDrawable = resources.getDrawable(checked).mutate();
        Drawable uncheckedDrawable = resources.getDrawable(unchecked).mutate();

        ((GradientDrawable) checkedDrawable).setColor(mTintColor);
        ((GradientDrawable) checkedDrawable).setStroke(mMarginDp, mTintColor);

        ((GradientDrawable) uncheckedDrawable).setColor(mUncheckedTintColor);
        ((GradientDrawable) uncheckedDrawable).setStroke(mMarginDp, mTintColor);
        //Set proper radius
        ((GradientDrawable) checkedDrawable).setCornerRadii(mLayoutSelector.getChildRadii(view));

        ((GradientDrawable) uncheckedDrawable).setCornerRadii(mLayoutSelector.getChildRadii(view));

        //Create drawable
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{-android.R.attr.state_checked}, uncheckedDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, checkedDrawable);

        //Set button background
        if (Build.VERSION.SDK_INT >= 16) {
            view.setBackground(stateListDrawable);
        } else {
            view.setBackgroundDrawable(stateListDrawable);
        }
    }

    /*
     * This class is used to provide the proper layout based on the view.
     * Also provides the proper radius for corners.
     * The layout is the same for each selected left/top middle or right/bottom button.
     * float tables for setting the radius via Gradient.setCornerRadii are used instead
     * of multiple xml drawables.
     */
    private class LayoutSelector {

        private int childCount;
        private int childIndex;
        private final int SELECTED_LAYOUT = R.drawable.radio_checked;
        private final int UNSELECTED_LAYOUT = R.drawable.radio_unchecked;

        private float r;    //this is the radios read by attributes or xml dimens
        private final float r1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                , 0.1f, getResources().getDisplayMetrics());    //0.1 dp to px
        private final float[] rLeft;    // left radio button
        private final float[] rRight;   // right radio button
        private final float[] rMiddle;  // middle radio button
        private final float[] rDefault; // default radio button
        private final float[] rTop;     // top radio button
        private final float[] rBot;     // bot radio button
        private float[] radii;          // result radii float table

        public LayoutSelector(float cornerRadius) {
            childCount = -1; // Init this to force setChildRadii() to enter for the first time.
            childIndex = -1; // Init this to force setChildRadii() to enter for the first time
            r = cornerRadius;
            rLeft = new float[]{r, r, r1, r1, r1, r1, r, r};
            rRight = new float[]{r1, r1, r, r, r, r, r1, r1};
            rMiddle = new float[]{r1, r1, r1, r1, r1, r1, r1, r1};
            rDefault = new float[]{r, r, r, r, r, r, r, r};
            rTop = new float[]{r, r, r, r, r1, r1, r1, r1};
            rBot = new float[]{r1, r1, r1, r1, r, r, r, r};
        }

        private void setChildRadii(int newChildCount, int newChildIndex) {

            // If same values are passed, just return. No need to update anything
            if (childCount == newChildCount && childIndex == newChildIndex)
                return;

            // Set the new values
            childCount = newChildCount;
            childIndex = newChildIndex;

            // if there is only one childIndex provide the default radio button
            if (childCount == 1) {
                radii = rDefault;
            } else if (childIndex == 0) { //left or top
                radii = (getOrientation() == LinearLayout.HORIZONTAL) ? rLeft : rTop;
            } else if (childIndex == childCount - 1) {  //right or bottom
                radii = (getOrientation() == LinearLayout.HORIZONTAL) ? rRight : rBot;
            } else {  //middle
                radii = rMiddle;
            }
        }

        /* Returns the selected layout id based on view */
        public int getSelected() {
            return SELECTED_LAYOUT;
        }

        /* Returns the unselected layout id based on view */
        public int getUnselected() {
            return UNSELECTED_LAYOUT;
        }

        /* Returns the radii float table based on view for Gradient.setRadii()*/
        public float[] getChildRadii(View view) {
            int newChildCount = SegmentedGroup.this.getVisibleChildCount();
            int newChildIndex = SegmentedGroup.this.indexOfVisibleChild(view);
            setChildRadii(newChildCount, newChildIndex);
            return radii;
        }
    }
}