
## ATab----仿爱奇艺tabbar
仿爱奇艺选中凸起的tabbar
项目地址：https://github.com/csfwff/ATab

## 效果图
[video](https://github.com/csfwff/ATab/blob/master/screenshot/SVID_20200309_094758_1.mp4?raw=true)

## 食用方法
1. 引入
现在还么的上传，自己下载引入吧，略🤪
2. 使用
	a. xml中使用
	```
    <com.xiamo.atab.ATab
        android:layout_width="match_parent"
        android:layout_alignParentBottom="true"
        android:orientation="horizontal"
        android:id="@+id/atab"
        android:layout_height="80dp">
        <com.xiamo.atab.ATabItem
            android:layout_width="0dp"
            android:layout_weight="1"
            app:msgText="哦豁"
            app:title="啦啦啦"
            app:checkIcon="@mipmap/home_un"
            app:uncheckIcon="@mipmap/home"
            android:layout_height="match_parent"/>
    </com.xiamo.atab.ATab>
	```

	  b. Java中使用
		xml
	```
    <com.xiamo.atab.ATab
        android:layout_width="match_parent"
        android:layout_alignParentBottom="true"
        android:orientation="horizontal"
        android:id="@+id/atab"
        android:layout_height="80dp">
    </com.xiamo.atab.ATab>
	```
	java
`       aTab = (ATab)findViewById(R.id.atab);`
	```
    aTab.addItem(new ATabItem.Builder(this).title("首页").uncheckIcon(getDrawable(R.mipmap.home_un))
                .checkIcon(getDrawable(R.mipmap.home)).checkColor(R.color.colorPrimary).create());
    aTab.addItem(new ATabItem.Builder(this).title("热门").uncheckIcon(getDrawable(R.mipmap.hot_un))
                .checkIcon(getDrawable(R.mipmap.hot)).checkColor(R.color.colorPrimary).create());
    aTab.addItem(new ATabItem.Builder(this).title("会员").uncheckIcon(getDrawable(R.mipmap.vip_un))
                .checkIcon(getDrawable(R.mipmap.vip)).checkColor(R.color.colorAccent).create());
    aTab.addItem(new ATabItem.Builder(this).title("消息").uncheckIcon(getDrawable(R.mipmap.msg_un))
                .checkIcon(getDrawable(R.mipmap.msg)).checkColor(R.color.colorPrimary).msg("哦豁").create());
    aTab.addItem(new ATabItem.Builder(this).title("我的").uncheckIcon(getDrawable(R.mipmap.mine_un))
                .checkIcon(getDrawable(R.mipmap.mine)).checkColor(R.color.colorPrimary).create());

	```
3. 设置监听
	```
    aTab.setOnItemSelectListener(new ATab.OnItemSelectListener() {
        @Override
        public void onItemSelect(int nowPos, int prePos) {
            Log.e("-----", nowPos+"---"+prePos);
        }
    });
	```
## ATabItem属性
定义了如下属性
```
<declare-styleable name="ATabItem">
        <!-- 基线距顶部的距离-->
        <attr name="baseLinePos" format="dimension" />
        <!--凸起的高度，需要小于baseLinePos，需要留一部分空间显示弹性效果-->
        <attr name="upHeight" format="dimension" />
        <!-- 背景颜色-->
        <attr name="bgColor" format="color" />
        <!--未选中icon-->
        <attr name="uncheckIcon" format="reference"/>
        <!--选中icon-->
        <attr name="checkIcon" format="reference"/>
        <!--icon的宽度-->
        <attr name="iconWidth" format="dimension" />
        <!--icon的高度-->
        <attr name="iconHeight" format="dimension" />
        <!--未选中颜色-->
        <attr name="uncheckColor" format="color"/>
        <!--选中颜色-->
        <attr name="checkColor" format="color"/>
        <!--选中的背景圆半径-->
        <attr name="checkRadius" format="dimension"/>
        <!--icon顶部到基线的距离-->
        <attr name="iconToBase" format="dimension"/>
        <!--文字下边距-->
        <attr name="textMarginBottom" format="dimension"/>
        <!--文字大小-->
        <attr name="textSize" format="dimension"/>
        <!--文字-->
        <attr name="title" format="string"/>
        <!--消息-->
        <attr name="msgText" format="string"/>
        <!--消息文字大小-->
        <attr name="msgSize" format="dimension"/>
        <!--消息文字颜色-->
        <attr name="msgColor" format="color"/>
        <!--消息背景颜色-->
        <attr name="msgBgColor" format="color"/>
        <!--消息距顶部距离-->
        <attr name="msgToTop" format="dimension"/>
        <!--消息距中心的x距离-->
        <attr name="msgToCenter" format="dimension"/>
        <!--消息内边距-->
        <attr name="msgPadding" format="dimension"/>
        <!--消息背景圆角半径-->
        <attr name="msgRadius" format="dimension"/>
</declare-styleable>
```
部分含糊的属性看如下说明
![img](https://github.com/csfwff/ATab/raw/master/screenshot/QQ%E5%9B%BE%E7%89%8720200309113000.png)

## ATab方法说明
1. 设置选中，举例：
` aTab.setSelect(2);`
2. 设置消息
` aTab.setMsg(3,"消息内容");`
注：消息内容为null即表示不显示消息
