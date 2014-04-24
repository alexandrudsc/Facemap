package com.alexandru.developer.facemap.timebars;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.alexandru.developer.facemap.R;

/**
 * Created by Alexandru on 4/21/14.
 */
public class TimeBar extends LinearLayout {

    CustomSeekBar progressBar;
    ImageButton button1, button2;
    View root;

    protected int XMLfile = R.layout.time_bar;

    public TimeBar(Context context) {
        super(context);
        init(context);
    }

    public TimeBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    protected void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        root  = inflater.inflate(XMLfile, this);

        progressBar = (CustomSeekBar)root.findViewById(R.id.seek_bar);
        progressBar.setProgress(CustomSeekBar.PROGRESS_ORIGIN);

        button1 = (ImageButton) root.findViewById(R.id.btn_minus);
        button1.setOnTouchListener(new ButtonMinusTouchListener());
        button1.setOnClickListener(new ButtonMinusClickListener());

        button2 = (ImageButton) root.findViewById(R.id.btn_plus);
        button2.setOnTouchListener(new ButtonPlusTouchListener());
    }



    private class ButtonPlusTouchListener implements OnTouchListener{

        private Handler handler;

        Runnable mAction = new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(progressBar.getProgress() + 1);
                handler.postDelayed(this, 100);
            }
        };

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            //Countinous action at down events.Cancel at up events
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    if(handler != null)
                        return true;
                    handler = new Handler();
                    handler.post(mAction);
                    return true;
                case MotionEvent.ACTION_UP:
                    handler.removeCallbacks(mAction);
                    handler = null;
                    return true;
            }
            return false;
        }

    }


    private class ButtonMinusTouchListener implements OnTouchListener{

        private Handler handler;

        Runnable mAction = new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(progressBar.getProgress() - 1);
                handler.postDelayed(this, 100);
            }
        };

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            //Countinous action at down events.Cancel at up events
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    if(handler != null)
                        return true;
                    handler = new Handler();
                    handler.post(mAction);
                    return true;
                case MotionEvent.ACTION_UP:
                    handler.removeCallbacks(mAction);
                    handler = null;
                    return true;
            }
            return false;
        }

    }

    private class ButtonMinusClickListener implements OnClickListener{

        @Override
        public void onClick(View view) {
            progressBar.setProgress(progressBar.getProgress() - 1);
        }
    }

}
