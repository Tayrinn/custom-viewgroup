package ru.yandex.yamblz.ui.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Volha on 26.07.2016.
 */
public class YaViewGroup extends LinearLayout {
    public YaViewGroup( Context context ) {
        super( context );
    }

    public YaViewGroup( Context context, AttributeSet attrs ) {
        super( context, attrs );
    }

    public YaViewGroup( Context context, AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public YaViewGroup( Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes ) {
        super( context, attrs, defStyleAttr, defStyleRes );
    }

    @Override
    protected void onLayout( boolean changed, int l, int t, int r, int b ) {

        int childCount = getChildCount();
        int childLeft = this.getPaddingLeft();
        final int childTop = this.getPaddingTop();
        final int childBottom = this.getMeasuredHeight() - this.getPaddingBottom();

        for ( int i = 0; i < childCount; ++i ) {

            View child = getChildAt( i );

            int childWidth = child.getMeasuredWidth();
            child.layout( childLeft, childTop, childLeft + childWidth, childBottom );
            childLeft += childWidth;
        }
    }

    @Override
    protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec ) {
        super.onMeasure( widthMeasureSpec, heightMeasureSpec );
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = 0;
        int height = MeasureSpec.getSize( heightMeasureSpec );

        if ( widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST ) {

            width = MeasureSpec.getSize(widthMeasureSpec);
        } else {
            //todo:
        }

        int maxWidth = 0;
        View matchParentView = null;
        for ( int i = 0; i < getChildCount(); ++i ) {
            final View child = getChildAt(i);

            if ( child.getVisibility() == GONE )
                continue;

            final LayoutParams lp = (LayoutParams) child.getLayoutParams();

            if ( lp.width != LayoutParams.MATCH_PARENT ) {

                int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec( width, MeasureSpec.AT_MOST );
                int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec( height, MeasureSpec.AT_MOST );

                child.measure( childWidthMeasureSpec, childHeightMeasureSpec );
                maxWidth += child.getMeasuredWidth();
            } else {
                if ( matchParentView == null )
                    matchParentView = child;
            }
        }
        int matchParentViewWidth = MeasureSpec.makeMeasureSpec( width - maxWidth, MeasureSpec.EXACTLY );
        int matchParentViewHeight = MeasureSpec.makeMeasureSpec( height, MeasureSpec.EXACTLY );
        if ( matchParentView != null ) {
            matchParentView.measure( matchParentViewWidth, matchParentViewHeight );
        }
    }


}
