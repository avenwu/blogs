#不得不谈的自定义View
不会自定义View就不是好的的Android攻城狮。  
###背景
随着开发者文档的完善，关于自定义view这个话题，现在已经有不少更加深入，详细的相关资料，在写本文之前，由于工作需要，笔者已经自定义过不少视图，之所以再写此文，原因很简单，读了Expect Android书中的Exploring Custom View章节，被其中细致的原理阐述折服，写得太好了。谨以此文做翻译，总结之用。

###View的亲密伴侣
大致来说自定义的view这个话题，可以分为三个方面：

* A.直接继承View的自定义
* B.继承ViewGroup及其子类的自定义
* C.组合现有View的自定义

一般来根据业务需求说这三中的实现难度是不同，C相对来说最为简单，往往只是各种现有View的组合封装，一般不涉及View的复杂机制流程。A，B差不多，对一般开发者来说都不是三言两语就可完成，A的定制程度最高，从颜色，形状，时间都可以深度定制，比如圆形视图，图表视图，B可以做出交互别致的容器view，比如瀑布流，侧滑菜单，下拉刷新。  

除了View和ViewGroup还有ViewParent，ViewManager和ViewRoot.  

* View是所有视图类的原始基类
* ViewParent是一个接口，定义了容器类需要实现的方法，view通过getParent得到父级，注意父一不一定是View
* ViewManager也是借口，定义了添加，删除子view的方法
* ViewGroup实现了ViewParent，ViewManager，可以添加子view
* ViewRoot是view最底层的一节，本身并不是view，实现了ViewParent，主要是View和窗体交互的过度层，是隐藏的内部类，实际上自定义view，不太会涉及，其他不太清楚。

###流程三部曲
要定制view，则需要了解view的绘制原理，需要经过哪些过程。  
网上有很多资料都提到了这几点，基本都是对的：

1. measure：测绘视图的尺寸
2. layout：确定视图放置位置
3. draw：绘制视图内容

如果读过源码，一定会发现一个很有意思，有规律的命名问题，那就是measure,layout,drawd对应了两套方法：

* public final void measure(int widthMeasureSpec, int heightMeasureSpec)
* public void layout(int l, int t, int r, int b)
* public void draw(Canvas canvas)

* protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
* protected void onLayout(boolean changed, int left, int top, int right, int bottom)
* protected void onDraw(Canvas canvas)

根据Android的命名风格，我们知道onXxx()都有一点回调的味道，就是说xxx事件发生了，接着onXxx()就被调用了。name事实上是不是这样？  
从代码上来看除了draw之外两对是遵循这个规律的：  

	measure()   		onMeasure()       		for(child in children) child.measure()	or
	template()   		hook()			for(child in children) child.hook()


概括起来，我们需要实现的方法一般有：  

	onMeasure	onSizeChanged	onLayout	onDraw	dispatchDraw


//未完待续


