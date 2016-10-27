package qll.com.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/10/27.
 */

public class PromptInfo {
    public static void popToast(Context context,String message,int duration){
        Toast.makeText(context,message,duration);
    }
}
