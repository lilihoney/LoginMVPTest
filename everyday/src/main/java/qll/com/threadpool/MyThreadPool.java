package qll.com.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/10/25.
 * @author  Qiang Xiaoqiang
 * BlockingQueue 是实现不同功能线程池的关键
 */

public class MyThreadPool {
    private ExecutorService priorityThreadPool;
    public MyThreadPool(){
        priorityThreadPool = new ThreadPoolExecutor(3,3,0L,TimeUnit.SECONDS,new PriorityBlockingQueue<Runnable>());
    }

}
