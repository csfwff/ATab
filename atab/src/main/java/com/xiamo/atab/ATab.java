package com.xiamo.atab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class ATab extends LinearLayout {

    private ViewPager vp;
    private int itemCount;
    private List<ATabItem> items = new ArrayList<>();
    private int nowSelect;

    private OnItemSelectListener mOnItemSelectListener;


    public ATab(Context context) {
        this(context, null);
    }

    public ATab(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ATab(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init(){
        items.clear();
        itemCount = getChildCount();
        if(itemCount == 0) return;
        for(int i = 0;i<itemCount;i++){
            if(getChildAt(i) instanceof  ATabItem){
                    ATabItem item = (ATabItem)getChildAt(i);
                    if(i==0)item.setCheckStatus(true);
                    items.add(item);
                    item.setOnClickListener(new OnItemClickListener(i));
            }else {
                throw new IllegalArgumentException("ATab的子View必须是ATabItem");
            }
        }
    }


    //设置选中
    public void setSelect(int selectPos){
        updateSelect(selectPos);
    }

    public void setMsg(int pos,String msg){
        if(pos>=items.size()||pos<0) return;
        items.get(pos).setMsg(msg);
    }

    public void addItem(ATabItem item){
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;
        item.setLayoutParams(layoutParams);
        addView(item);
        init();
    }




    private class OnItemClickListener implements OnClickListener{
        private int currentIndex;

        public OnItemClickListener(int i){
            this.currentIndex = i;
        }

        @Override
        public void onClick(View view) {
            if(currentIndex == nowSelect)return;
            updateSelect(currentIndex);
        }
    }

    //更新状态
    private void updateSelect(int selectPos){
        if(mOnItemSelectListener!=null){
            mOnItemSelectListener.onItemSelect(selectPos,nowSelect);
        }
        items.get(nowSelect).setCheckStatus(false);
        items.get(selectPos).setCheckStatus(true);
        nowSelect = selectPos;
    }

    public interface OnItemSelectListener{
        void onItemSelect(int nowPos,int prePos);
    }

    public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener){
        this.mOnItemSelectListener = onItemSelectListener;
    }
}
