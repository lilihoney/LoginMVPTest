package qll.com.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import qll.com.utils.MyLog;

/**
 * Created by Administrator on 2016/10/26.
 * @author Qiang Xiaoqiang
 */

public class ExpandThreadPoolExecutor extends ThreadPoolExecutor{
    private static String TAG = "ExpandThreadPoolExecutor";
    private boolean isPause;
    private ReentrantLock pauseLock = new ReentrantLock();
    private Condition unpaused = pauseLock.newCondition();

    public ExpandThreadPoolExecutor(int corePoolSize, int maxmumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue){
        super(corePoolSize,maxmumPoolSize,keepAliveTime,unit,workQueue);
    }
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t,r);
//        pauseLock.lock();
//        try{
//            while(isPause)
//                unpaused.await();
//        }catch (InterruptedException e){
//            t.interrupt();
//        }finally {
//            pauseLock.unlock();
//        }
        String threadName = t.getName();
        MyLog.v(TAG,"thread " + threadName +"准备执行");
    }

    public void pause(){
        pauseLock.lock();
        try{
            isPause = true;
        }finally {
            pauseLock.unlock();
        }
    }

    public void resume(){
        pauseLock.lock();
        try{
            isPause = false;
            unpaused.signalAll();
        }finally {
            pauseLock.unlock();
        }
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        String threadName = Thread.currentThread().getName();
        MyLog.v(TAG,"thread " + threadName +"任务执行结束！");
    }

    @Override
    protected void terminated() {
        super.terminated();
        MyLog.v(TAG,"线程池结束！");
    }
}
