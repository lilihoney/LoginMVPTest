package qll.com.everyday;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import qll.com.threadpool.ExpandThreadPoolExecutor;
import qll.com.threadpool.PausebleThreadPool;
import qll.com.threadpool.PriorityRunnable;
import qll.com.utils.MyLog;
import qll.com.utils.PromptInfo;

public class ThreadPoolActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnShutdown,btnShutdownNow;
    private Button btnExpandExecutor,btnPause,btnStart;
    private TextView tvPauseThread;
    private Spinner spThreadPool;
    private ExecutorService fixedThreadPool,singleThreadExecutor,cachedThreadExecutor;
    private ScheduledExecutorService scheduledThreadPool,singleThreadScheduleExecutor;
    private PausebleThreadPool pausebleThreadPool;
    private String selected = "";
    private boolean isPaused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_thread_pool);
        createFixedThreadPool();
        initViews();
        getCPUcounts();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyLog.v("onDestroy",threadPoolLife());
    }

    @Override
    protected void onStop() {
        super.onStop();
        MyLog.v("onStop",threadPoolLife());
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyLog.v("onPause",threadPoolLife());
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyLog.v("onResume",threadPoolLife());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        MyLog.v("onResume",threadPoolLife());
    }

    private void initViews(){
        tvPauseThread = (TextView)findViewById(R.id.tv_pause_thread);
        btnShutdown = (Button)findViewById(R.id.btn_shutdown);
        btnShutdownNow = (Button)findViewById(R.id.btn_shutdown_now);
        btnShutdown.setOnClickListener(this);
        btnShutdownNow.setOnClickListener(this);
        btnExpandExecutor = (Button)findViewById(R.id.btn_ethe);
        btnPause = (Button)findViewById(R.id.btn_interrupt);
        btnStart = (Button)findViewById(R.id.btn_can_pause);
        btnExpandExecutor.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        spThreadPool = (Spinner)findViewById(R.id.spinner);
        spThreadPool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = getResources().getStringArray(R.array.thread_executor)[position];
//                Toast.makeText(ThreadPoolActivity.this, "position = " + position +" id = "+ id, Toast.LENGTH_SHORT).show();
                switch (position){
                    case 0:
                        shutdownExecutor(scheduledThreadPool);
                        shutdownExecutor(singleThreadExecutor);
                        shutdownExecutor(cachedThreadExecutor);
                        shutdownExecutor(singleThreadScheduleExecutor);
                        //createFixedThreadPool();
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


    /**
     * FixedThreadPool
     * 用LinkedBlockingQueue实现
     * 固定线程数量的线程池，依次一个一个处理任务，利用一个线程
     * */
    private ExecutorService createFixedThreadPool(){
        System.out.println("createFixedThreadPool is execute");
        fixedThreadPool = Executors.newFixedThreadPool(3);//参数代表corePoolSize和maxmumPoolSize
        for(int i = 1;i <= 100;i++){
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

    /**
     * SingleThreadExecutor
     * LinkedBlockingQueue
     * 只有一个线程的线程池，每次只能执行不念旧恶任务，其他任务在队列中等待
     * */
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

    /**
     * CachedThreadPool
     * SynchronousQueue
     * 可根据实际情况调整线程池中线程的数量的线程池
     * */
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

    /**
     * ScheduledThreadPool
     * DelayedWorkQueue
     * 可以定时或周期性执行任务的线程池
     * */
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

    /**
     * SingleThreadScheduledExecutor
     * DelayedWorkQueue
     * 可以定时或周期性执行任务的线程池,只有一个线程
     * */
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

    /**
     * new ThreadPoolExecutor 的方式
     * PriorityBlockingQueue
     * 自定义优先级线程池，极大地改善了系统默认线程池以FIFO方式处理的不灵活
     * 超过corePoolSize的线程在队列中排队并按优先级来出队执行
     * */
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
            e.shutdown();//ExecutorService.shutdown()后在终止前允许执行以前提交的任务
        }
    }

    private void shutdownNowExecutor(ExecutorService e){
        if(e != null && !e.isShutdown())
            e.shutdownNow();//阻止正在任务队列中等待任务的启动并试图停止当前正在执行的任务
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

    /*
    * 通过extends ThreadPoolExecutor 自定义线程池

    */
    private void startExpandThreadPool(){
        ExpandThreadPoolExecutor myPool = new ExpandThreadPoolExecutor(3,3,0L,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());
        for(int i = 1;i <= 5;i++){
            final int index = i;
            myPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("第"+ index +"个 thread " + Thread.currentThread().getName());
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    /*
    * 自定义可暂停的线程池
    * */
    private void getPauseThreadPool(){
        pausebleThreadPool = new PausebleThreadPool(1,1,0L,TimeUnit.SECONDS,new PriorityBlockingQueue<Runnable>());
        for(int i = 1;i <= 50;i++){
            final int priority = i;
            pausebleThreadPool.execute(new PriorityRunnable(priority) {
                @Override
                public void doSth() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvPauseThread.setText("优先级为"+priority+"的任务正在执行");
                        }
                    });
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            });

        }
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
            case R.id.btn_ethe:
                startExpandThreadPool();
                break;
            case R.id.btn_interrupt:
                   if(isPaused){
                       pausebleThreadPool.resume();
                       isPaused = false;
                   }else{
                       pausebleThreadPool.pause();
                       isPaused = true;
                   }
                break;
            case R.id.btn_can_pause:
                getPauseThreadPool();
                break;
            default:
                break;
        }
    }

    private void getCPUcounts(){
        PromptInfo.popToast(getApplicationContext(),"此设备的cpu数量="+ Runtime.getRuntime().availableProcessors(),Toast.LENGTH_LONG);
    }

    private String threadPoolLife(){
        boolean terminated = fixedThreadPool.isTerminated();
        boolean shutted = fixedThreadPool.isShutdown();
        return "fixedThreadPool shutted = "+ shutted +" , terminated = "+terminated;
    }
}
