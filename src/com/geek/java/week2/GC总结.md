**GC日志**

1. GC日志命令

java -XX:PrintGCDetails -XX:PrintGCDateStamps -Xlogggc:gc.log -Xmx1g -Xms1g GCLogAnaLysis

-XX:PrintGCDetails = 打印GC详细信息。

-XX:PrintGCDateStamps：打印GC发生时间

-Xmx1g：最大堆内存大小，默认1/4内存

-Xlogggc：gc日志打印到文件

-XX:+UseSerialGC：使用串行GC



2. GC日志怎么读

2014-07-18T16:02:17.606+0800:611.633（时间戳）: [GC（表示Young GC） 611.633: [DefNew（单线程Serial年轻代GC）: 843458K（年轻代垃圾回收前的大小）->2K（年轻代回收后的大小）(948864K（年轻代总大小）), 0.0059180 secs（本次回收的时间）] 2186589K（整个堆回收前的大小）->1343132K（整个堆回收后的大小）(3057292K（堆总大小）), 0.0059490 secs（回收时间）] [Times: user=0.00（用户耗时） sys=0.00（系统耗时）, real=0.00 secs（实际耗时）]



3. GC回收器试验

（1）searialGC：效率比较低，耗时长。

（2）ParallelGC：Xmx调大后，GC次数变少，但是单次耗时变长。

（3）ConcMarkSweepGC：

​		a. young区（ParNew）相对慢但是是并发的。Old区分阶段init-mark和final-remark会stw。

​		b. CmsGC执行过程中，可以发生YoungGC.

（4）G1GC

​		a. 只看概要，去掉PrintGCDetails

​		b. 退化成串行GC，性能严重下降。





