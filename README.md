# WaveView
 
  :smile: <br>
  ![](https://github.com/XuNeverMore/WaveView/raw/master/wave.gif)

  心得：
  上面的是普通的view,下面的是surfaceview,下面貌似流畅些。
  利用Regin可以获取曲线x位置的y坐标，PathMesure这里不好处理，distance无法确定。
  viewanimator使用必须在looper线程里，ui线程是looper线程所以平时不用在意这些。本来用HandlerThread来运行动画报错了，看源码才发现这个问题。
  无限循环的动画若没有looper，还怎么做其他事。

  >源码面前，一切了然。*
  

# Drawable
  
  ![](https://github.com/XuNeverMore/WaveView/raw/master/text.gif)
