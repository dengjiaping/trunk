package com.histudent.jwsoft.histudent.view.fragment.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;


/**
 * Created by lichaojie on 2017/9/15.
 * desc:
 */

public class TranslucentBehavior extends CoordinatorLayout.Behavior<Toolbar> {

    private int offset = 0;
    private int startOffset = 0;
    private int endOffset = 0;
    private Context context;

    public TranslucentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return true;
    }


    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar toolbar, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, toolbar, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        startOffset = SystemUtil.dip2px(context, 200);
        endOffset = context.getResources().getDimensionPixelOffset(R.dimen.dp100) + startOffset - toolbar.getHeight();
        offset += dyConsumed;
        final View statusBar = coordinatorLayout.findViewById(R.id.view_home_status_bar);
        if (offset <= startOffset) {//完全透明
            toolbar.setAlpha(0);
            statusBar.setAlpha(0);
        } else if (offset > startOffset && offset < endOffset) {
            float percent = (float) (offset - startOffset) / endOffset;
            toolbar.setAlpha(percent);
            statusBar.setAlpha(percent);
        } else if (offset >= endOffset) {//完全不透明
            toolbar.setAlpha(1);
            statusBar.setAlpha(1);
        }
    }
}
