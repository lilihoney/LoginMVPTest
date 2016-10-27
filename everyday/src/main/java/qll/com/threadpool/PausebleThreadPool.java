package qll.com.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import qll.com.utils.MyLog;

/**
 * Created by Administrator on 2016/10/27.
 * @author Qiang Xiaoqiang
 */

public class PausebleThreadPool extends ThreadPoolExecutor{
    private static String TAG = "PausebleThreadPool";
    private boolean isPaused;
    private ReentrantLock pauseLock =new ReentrantLock();//可重入锁
    private Condition unpause = pauseLock.newCondition();//其变量可表示一个线程延缓执行，直到其他线程通知后可以调整其状态，管理多线程的共享信息因此必须与lock同用

    public PausebleThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue){
        super(corePoolSize,maximumPoolSize,keepAliveTime,unit,workQueue);
    }
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        String threadName = Thread.currentThread().getName();
        MyLog.v(TAG,"thread "+ threadName +"任务执行结束！");
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        MyLog.v(TAG,"thread "+ t.getName() +"任务执行结束！");
        pauseLock.lock();
        try{
            while(isPaused)
                unpause.await();
        }catch (InterruptedException e){
            t.interrupt();
        }finally {
            pauseLock.unlock();
        }

    }

    public void pause(){
        pauseLock.lock();
        try{
            isPaused = true;
        }finally {
            pauseLock.unlock();
        }
    }

    public void resume(){
        pauseLock.lock();
        try{
            isPaused = false;
            unpause.signalAll();
        }finally {
            pauseLock.unlock();
        }
    }

//    public boolean isPaused(){
//        return isPaused;
//    }
//    public void setPaused(boolean paused){
//        isPaused = paused;
//    }

    @Override
    protected void terminated() {
        super.terminated();
        MyLog.v(TAG,"线程池结束！");
    }
}
