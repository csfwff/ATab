package com.xiamo.atab;


import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ATabItem extends View {
    private Context context;

    private boolean isChecked = false;  //是否选中
    private boolean isAnimating = false;  //是否动画中

    private Paint bgPaint;    //画笔
    private Paint iconPaint;  //icon和文字画笔
    private Paint msgPaint; //消息画笔


    private int baseLinePos = 20; //基线距离顶部的高度 默认20dp
    private int upHeight = 10; //凸起的高度 默认12dp，需要留一部分空间显示弹性效果
    private int bgColor = 0; //背景颜色，默认白色
    private Drawable uncheckIcon; //未选中icon
    private Drawable checkIcon; //选中icon
    private int iconWidth = 25;//icon宽度
    private int iconHeight = 25;//icon高度
    private int uncheckColor; //未选中颜色
    private int checkColor; //选中颜色
    private int checkRadius = 18; //选中的背景圆半径，默认18dp
    private int iconToBase = 14; //icon中心到基线的距离，默认14dp
    private int textMarginBottom = 8; //文字下边距，默认8dp
    private int textSize = 12; //文字大小，默认12sp
    private String title;  //item的名称

    private String msg; //消息
    private int msgSize =8; //消息文字大小，默认8sp
    private int msgColor; //消息文字颜色
    private int msgBgColor; //消息背景颜色
    private int msgToTop = 34; //消息距顶部距离，默认34dp
    private int msgToCenter = 18; //消息距中心的x距离，默认18dp
    private int msgPadding = 2; //消息内边距，默认2dp
    private int msgRadius = 18; //消息背景圆角半径，默认18dp

    private int nowUpHeight = 0; //记录当前凸起的高度
    private Rect rect;
    private RectF msgRect;

    private ValueAnimator checkAnimator;
    private ValueAnimator uncheckAnimator;


    ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            nowUpHeight = (Integer) valueAnimator.getAnimatedValue();
            invalidate();
        }
    };

    ValueAnimator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animator) {
            isAnimating = true;
        }

        @Override
        public void onAnimationEnd(Animator animator) {
            isAnimating = false;
        }

        @Override
        public void onAnimationCancel(Animator animator) {
            isAnimating = false;
        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    };


    public ATabItem(Context context) {
        this(context,null);
    }

    public ATabItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public ATabItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    private void  init(Context context, AttributeSet attrs) {
        this.context = context;
        //先获取属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.ATabItem);
        initAttrs(typedArray);
        typedArray.recycle();
        //初始化画笔
        bgPaint = new Paint();
        bgPaint.setColor(bgColor);
        bgPaint.setStrokeWidth(0.1f);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setAntiAlias(true);

        iconPaint = new Paint();
        iconPaint.setStrokeWidth(0.1f);
        iconPaint.setStyle(Paint.Style.FILL);
        iconPaint.setAntiAlias(true);
        iconPaint.setTextAlign(Paint.Align.CENTER);
        iconPaint.setTextSize(textSize);

        msgPaint = new Paint();
        msgPaint.setStrokeWidth(0.1f);
        msgPaint.setStyle(Paint.Style.FILL);
        msgPaint.setAntiAlias(true);
        msgPaint.setTextAlign(Paint.Align.CENTER);
        msgPaint.setTextSize(msgSize);

        rect = new Rect(0,0,0,0);
        msgRect = new RectF(0,0,0,0);

        super.setClickable(true);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAnimating)return;
                if(isChecked){
                    isChecked = false;
                    uncheckAnimator.start();
                }else {
                    isChecked = true;
                    checkAnimator.start();
                }
            }
        });

        //选中动画
        checkAnimator = ValueAnimator.ofInt(baseLinePos,upHeight);
        checkAnimator.addUpdateListener(updateListener);
        checkAnimator.setDuration(300);
        checkAnimator.setInterpolator(new OvershootInterpolator(3));
        checkAnimator.addListener(animatorListener);
        //未选中动画
        uncheckAnimator = ValueAnimator.ofInt(upHeight,baseLinePos);
        uncheckAnimator.addUpdateListener(updateListener);
        uncheckAnimator.setDuration(150);
        uncheckAnimator.setInterpolator(new DecelerateInterpolator(3));
        checkAnimator.addListener(animatorListener);

    }


    //获取属性
    private void initAttrs(TypedArray typedArray){
        baseLinePos = typedArray.getDimensionPixelOffset(R.styleable.ATabItem_baseLinePos,dip2px(context,baseLinePos));
        upHeight = typedArray.getDimensionPixelOffset(R.styleable.ATabItem_upHeight,dip2px(context,upHeight));
        upHeight = baseLinePos - upHeight;
        nowUpHeight = baseLinePos;
        bgColor = typedArray.getColor(R.styleable.ATabItem_bgColor,Color.WHITE);

        uncheckColor = typedArray.getColor(R.styleable.ATabItem_uncheckColor,Color.DKGRAY);
        checkColor = typedArray.getColor(R.styleable.ATabItem_checkColor,Color.GREEN);
        checkIcon = typedArray.getDrawable(R.styleable.ATabItem_checkIcon);
        uncheckIcon = typedArray.getDrawable(R.styleable.ATabItem_uncheckIcon);
        checkRadius = typedArray.getDimensionPixelOffset(R.styleable.ATabItem_checkRadius,dip2px(context,checkRadius));
        iconToBase = typedArray.getDimensionPixelOffset(R.styleable.ATabItem_iconToBase,dip2px(context,iconToBase));
        iconWidth = typedArray.getDimensionPixelOffset(R.styleable.ATabItem_iconWidth,dip2px(context,iconWidth));
        iconHeight = typedArray.getDimensionPixelOffset(R.styleable.ATabItem_iconHeight,dip2px(context,iconHeight));

        textMarginBottom = typedArray.getDimensionPixelOffset(R.styleable.ATabItem_textMarginBottom,dip2px(context,textMarginBottom));
        textSize = typedArray.getDimensionPixelOffset(R.styleable.ATabItem_textSize,sp2px(context,textSize));
        title = typedArray.getString(R.styleable.ATabItem_title);

        msg = typedArray.getString(R.styleable.ATabItem_msgText);
        msgSize = typedArray.getDimensionPixelOffset(R.styleable.ATabItem_msgSize,sp2px(context,msgSize));
        msgColor = typedArray.getColor(R.styleable.ATabItem_msgColor,Color.WHITE);
        msgBgColor = typedArray.getColor(R.styleable.ATabItem_msgBgColor,Color.RED);
        msgToTop = typedArray.getDimensionPixelOffset(R.styleable.ATabItem_msgToTop,dip2px(context,msgToTop));
        msgToCenter = typedArray.getDimensionPixelOffset(R.styleable.ATabItem_msgToCenter,dip2px(context,msgToCenter));
        msgPadding = typedArray.getDimensionPixelOffset(R.styleable.ATabItem_msgPadding,dip2px(context,msgPadding));
        msgRadius = typedArray.getDimensionPixelOffset(R.styleable.ATabItem_msgRadius,dip2px(context,msgRadius));


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //获取控件宽高
        int width = getWidth();
        int height = getHeight();

        //绘制背景
        Path path = new Path();
        path.moveTo(0,baseLinePos);
        path.lineTo(width*0.1f,baseLinePos);
        path.cubicTo(width*3/10f,baseLinePos,width*3/10f,nowUpHeight,width*0.5f,nowUpHeight);
        path.cubicTo(width*7/10f,nowUpHeight,width*7/10f,baseLinePos,width*0.9f,baseLinePos);
        path.lineTo(width,baseLinePos);
        path.lineTo(width,height);
        path.lineTo(0,height);
        path.close();
        canvas.drawPath(path,bgPaint);
        //绘制icon
        iconPaint.setColor(isChecked?checkColor:uncheckColor);
        if(isChecked)
            canvas.drawCircle(width*0.5f,nowUpHeight+iconToBase+ iconHeight /2,checkRadius,iconPaint);
        BitmapDrawable bd = isChecked?(BitmapDrawable)checkIcon:(BitmapDrawable)uncheckIcon;
        if(bd!=null){
            Bitmap bm = bd.getBitmap();
            rect.left = (width-iconWidth)/2;
            rect.top = nowUpHeight + iconToBase;
            rect.right = (width+iconWidth)/2;
            rect.bottom = nowUpHeight + iconToBase + iconHeight;
            canvas.drawBitmap(bm,null,rect,iconPaint);
        }
        //绘制文字
        if(title!=null)canvas.drawText(title,width/2,height-textMarginBottom,iconPaint);
        //绘制消息
        if(msg!=null){
            getTextBackgroundSize(width/2+msgToCenter  , msgToTop,msg,msgPaint);
            msgPaint.setColor(msgBgColor);
            canvas.drawRoundRect(msgRect,msgRadius,msgRadius,msgPaint);
            msgPaint.setColor(msgColor);
            canvas.drawText(msg,width/2+msgToCenter ,msgToTop,msgPaint);
        }
    }

    public void setCheckStatus(boolean checked){
        isChecked = checked;
        if(isChecked){
            checkAnimator.start();
        }else {
            uncheckAnimator.start();
        }
    }

    public boolean getCheckStatus(){
        return isChecked;
    }




    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }

    public static int sp2px(Context context,float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    //获取消息背景矩形
    private  void getTextBackgroundSize(float x, float y, @NonNull String text, @NonNull Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float halfTextLength = paint.measureText(text) /2;
        msgRect.left= (int) (x -msgPadding-halfTextLength);
        msgRect.top =  (int) (y + fontMetrics.top -msgPadding);
        msgRect.bottom = (int) (y + fontMetrics.bottom +msgPadding);
        msgRect.right =  (int) (x + halfTextLength+msgPadding);
    }

}

