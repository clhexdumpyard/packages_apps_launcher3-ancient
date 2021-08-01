package com.nabil_aba;
import android.app.ActivityManager;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.os.Handler;
import com.android.launcher3.R;

public class RAM extends LinearLayout {
    ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
    ActivityManager activityManager = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
    String finalValue = "";
    
    public RAM(Context c, AttributeSet a) {
        super(c, a);      
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                nabil_aba();
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(run);
    }

    public void nabil_aba() {
        activityManager.getMemoryInfo(memoryInfo);
        
        long avail = memoryInfo.availMem;
        long usage = memoryInfo.totalMem - memoryInfo.availMem;
        long total = memoryInfo.totalMem;
        
        TextView textused = findViewById(R.id.nabil_usedram);
        textused.setText(nabil(usage) + " " + getResources().getString(R.string.nabil_aba_used));
        
        TextView textfree = findViewById(R.id.nabil_freeram);
        textfree.setText(nabil(avail) + " " + getResources().getString(R.string.nabil_aba_free));
        
        ProgressBar pb = findViewById(R.id.nabil_aba_ramviewPB);
        pb.setMax(nabilganteng(total / 1048576.0));
        pb.setProgress(nabilganteng(usage / 1048576.0));
    }
    
    public String nabil(long totalMemory) {
        double kb = totalMemory / 1024.0;
        double mb = totalMemory / 1048576.0;
        double gb = totalMemory / 1073741824.0;
        double tb = totalMemory / 1099511627776.0;
        
        DecimalFormat twoDecimalForm = new DecimalFormat("#");
        if (tb > 1) {
            twoDecimalForm = new DecimalFormat("#.#");
            finalValue = twoDecimalForm.format(tb).concat(" TB");
        } else if (gb > 1) {
            twoDecimalForm = new DecimalFormat("#.#");
            finalValue = twoDecimalForm.format(gb).concat(" GB");
        } else if (mb > 1) {
            finalValue = twoDecimalForm.format(mb).concat(" MB");
        } else if (kb > 1) {
            finalValue = twoDecimalForm.format(mb).concat(" KB");
        } else {
            finalValue = twoDecimalForm.format(totalMemory).concat(" Bytes");
        }
        
        return finalValue;
    }
    
    public int nabilganteng(double formate) {       
        DecimalFormat twoDecimalFormPB = new DecimalFormat("#");
        return Integer.valueOf(twoDecimalFormPB.format(formate));
    }
}
