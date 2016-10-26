package qll.com.threadpool;


/**
 * Created by Administrator on 2016/10/26.
 */

public abstract class PriorityRunnable implements Runnable,Comparable<PriorityRunnable>{
    private int priority;
    public PriorityRunnable(int priority){
        if(priority < 0){
            throw new IllegalArgumentException();
        }
        this.priority = priority;
    }


    @Override
    public void run() {
        doSth();
    }

    @Override
    public int compareTo(PriorityRunnable o) {
        int my = this.getPriority();
        int other = o.getPriority();

        return my < other ? 1: my > other ? -1 : 0;
    }

    public int getPriority(){
        return priority;
    }

    public abstract void doSth();
}
