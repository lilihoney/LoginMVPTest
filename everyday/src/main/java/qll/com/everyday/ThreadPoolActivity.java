package qll.com.everyday;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import qll.com.threadpool.MyThreadPool;
import qll.com.threadpool.PriorityRunnable;
import qll.com.utils.MyLog;

public class ThreadPoolActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnShutdown,btnShutdownNow;
    private Spinner spThreadPool;
    private ExecutorService fixedThreadPool,singleThreadExecutor,cachedThreadExecutor;
    private ScheduledExecutorService scheduledThreadPool,singleThreadScheduleExecutor;
    private String selected = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_thread_pool);

        initViews();


    }

    private void initViews(){
        btnShutdown = (Button)findViewById(R.id.btn_shutdown);
        btnShutdownNow = (Button)findViewById(R.id.btn_shutdown_now);
        btnShutdown.setOnClickListener(this);
        btnShutdownNow.setOnClickListener(this);
        spThreadPool = (Spinner)findViewById(R.id.spinner);
        spThreadPool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = getResources().getStringArray(R.array.thread_executor)[position];
                Toast.makeText(ThreadPoolActivity.this, "position = " + position +" id = "+ id, Toast.LENGTH_SHORT).show();
                switch (position){
                    case 0:
                        shutdownExecutor(scheduledThreadPool);
                        shutdownExecutor(singleThreadExecutor);
                        shutdownExecutor(cachedThreadExecutor);
                        shutdownExecutor(singleThreadScheduleExecutor);
                        createFixedThreadPool();
                        break;
                    case 1:
                        shutdownExecutor(scheduledThreadPool);
                        shutdownExecutor(singleThreadExecutor);
                        shutdownExecutor(fixedThreadPool);
                        shutdownExecutor(singleThreadScheduleExecutor);
                        getCachedThreadPool();
                        break;
                    case 2:
                        shutdownExecutor(fixedThreadPool);
                        shutdownExecutor(scheduledThreadPool);
                        shutdownExecutor(cachedThreadExecutor);
                        shutdownExecutor(singleThreadScheduleExecutor);
                        getSingleThreadExecutor();
                        break;
                    case 3:
                        shutdownExecutor(fixedThreadPool);
                        shutdownExecutor(scheduledThreadPool);
                        shutdownExecutor(cachedThreadExecutor);
                        shutdownExecutor(singleThreadScheduleExecutor);
                        getScheduleThreadPool();
                        break;
                    case 4:
                        shutdownExecutor(scheduledThreadPool);
                        shutdownExecutor(singleThreadExecutor);
                        shutdownExecutor(cachedThreadExecutor);
                        shutdownExecutor(fixedThreadPool);
                        getSingleThreadScheduledExecutor();
                        break;
                    case 5:
                        shutdownExecutor(scheduledThreadPool);
                        shutdownExecutor(singleThreadExecutor);
                        shutdownExecutor(cachedThreadExecutor);
                        shutdownExecutor(fixedThreadPool);
                        shutdownExecutor(singleThreadScheduleExecutor);
                        getPriorityThreadExecutor();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //固定线程数量的线程池，依次一个一个处理任务，利用一个线程
    private ExecutorService createFixedThreadPool(){
        System.out.println("createFixedThreadPool is execute");
        fixedThreadPool = Executors.newFixedThreadPool(3);//参数代表corePoolSize和maxmumPoolSize
        for(int i = 1;i <= 10;i++){
            final int index = i;
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    MyLog.v("threadName= ",threadName+",正在执行第"+index+"个任务");
                    try{
                        Thread.sleep(3000);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            });
        }
        MyLog.v("fixedThreadPool isTerminated()= ",fixedThreadPool.isTerminated()+"");
        return fixedThreadPool;
    }

    //只有一个线程的线程池，每次只能执行不念旧恶任务，其他任务在队列中等待
    private ExecutorService getSingleThreadExecutor(){
        singleThreadExecutor = Executors.newSingleThreadExecutor();
        for(int i = 1;i <= 10;i++){
            final int index = i;
            singleThreadExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    MyLog.v("threadName= ",threadName+",正在执行第"+index+"个任务");
                    try{
                        Thread.sleep(3000);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            });
        }
        MyLog.v("singleThreadExecutor isTerminated()= ",singleThreadExecutor.isTerminated()+"");
        return singleThreadExecutor;
    }

    //可根据实际情况调整线程池中线程的数量的线程池
    private ExecutorService getCachedThreadPool(){
        cachedThreadExecutor = Executors.newCachedThreadPool();
        for(int i = 1;i <= 10;i++){
            final int index = i;
            try{
                Thread.sleep(2000);//每2s提交一个Task
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            cachedThreadExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    MyLog.v("cachedThreadExecutor ","thread "+ threadName + ",正在执行第"+index+"个任务");
                    try{
                        long time = index * 500;//任务时间
                        Thread.sleep(time);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            });
        }
        return cachedThreadExecutor;
    }

    //可以定时或周期性执行任务的线程池
    private void getScheduleThreadPool(){
        scheduledThreadPool = Executors.newScheduledThreadPool(3);
        //延迟2秒后执行任务
        scheduledThreadPool.schedule(new Runnable() {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                MyLog.v("scheduleThreadPool  ",",2delay thrad "+ threadName +"正在执行");
            }
        },2, TimeUnit.SECONDS);
        //延迟1秒后，每隔2秒执行一次该任务
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                MyLog.v("scheduleThreadPool  ",",1delay 2 delay thread "+ threadName +"正在执行");
            }
        },1,2, TimeUnit.SECONDS);
    }

    //
    private void getSingleThreadScheduledExecutor(){
        singleThreadScheduleExecutor = Executors.newSingleThreadScheduledExecutor();
        singleThreadScheduleExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                MyLog.v("stse",", thread: "+ threadName + ",正在执行");
            }
        },1,2,TimeUnit.SECONDS);
    }

    //自定义线程池
    private void getPriorityThreadExecutor(){
        ExecutorService priorityThreadPool = new ThreadPoolExecutor(3,3,0L,TimeUnit.SECONDS,new PriorityBlockingQueue<Runnable>());
        for(int i = 1;i <= 10;i++){
            final int priority = i;
            priorityThreadPool.execute(new PriorityRunnable(priority) {
                @Override
                public void doSth() {
                    String threadName = Thread.currentThread().getName();
                    MyLog.e("priorityThreadPool","thread " + threadName + ",正在执行优先级为"+priority+"的任务");
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void shutdownExecutor(ExecutorService e){
        if(e != null && !e.isShutdown()){
            e.shutdown();//ExecutorService.shutdown()后会终止线程池中所有正在执行的线程，未执行的线程会继续执行
        }
    }
    private void shutdownNowExecutor(ExecutorService e){
        if(e != null && !e.isShutdown())
            e.shutdownNow();
    }

    private void shutdownNowCurrentExecutor(String selected){
        if(selected.equals(""))
            return;
        if(selected.equals("fixedThreadPool")){
            fixedThreadPool.shutdownNow();
        }
        if(selected.equals("cachedThreadPool"))
            cachedThreadExecutor.shutdownNow();
        if(selected.equals("singleThreadPool"))
            singleThreadExecutor.shutdownNow();
        if(selected.equals("scheduledThreadPool"))
            scheduledThreadPool.shutdownNow();
        if(selected.equals("singleThreadScheduledPool"))
            singleThreadScheduleExecutor.shutdownNow();
    }

    private void shutdownCurrentExecutor(String selected){
        if(selected.equals(""))
            return;
        if(selected.equals("fixedThreadPool")){
            fixedThreadPool.shutdown();
        }
        if(selected.equals("cachedThreadPool"))
            cachedThreadExecutor.shutdown();
        if(selected.equals("singleThreadPool"))
            singleThreadExecutor.shutdown();
        if(selected.equals("scheduledThreadPool"))
            scheduledThreadPool.shutdown();
        if(selected.equals("singleThreadScheduledPool"))
            singleThreadScheduleExecutor.shutdown();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_shutdown:
                shutdownCurrentExecutor(selected);
                break;
            case R.id.btn_shutdown_now:
                shutdownNowCurrentExecutor(selected);
                break;
        }
    }
}
