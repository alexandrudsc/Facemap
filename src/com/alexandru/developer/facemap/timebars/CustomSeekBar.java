package com.alexandru.developer.facemap.timebars;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AbsoluteLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.alexandru.developer.facemap.R;

/**
 * Created by Alexandru on 4/21/14.
 * Horizontal bar.Created to select events for a chosen person from the selected region.
 */
public class CustomSeekBar extends RelativeLayout {

    public static final int PROGRESS_ORIGIN = 2500;

    TextView mTxvSeekBarValue;
    SeekBar mSkbSample;

    public final int ANIM_DURATION=500;

    public CustomSeekBar(Context context) {
        super(context);
        init(context);
    }

    public CustomSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    protected void init(Context context){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_seek_bar, this);

        mTxvSeekBarValue = (TextView) this.findViewById(R.id.txvSeekBarValue);
        mSkbSample = (SeekBar) this.findViewById(R.id.skbSample);
        mSkbSample.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    ShowSeekValue((int)event.getX(), mTxvSeekBarValue.getTop());
                }
                else if(event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    ShowSeekValue((int)event.getX(), mTxvSeekBarValue.getTop());
                }
                else if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    AlphaAnimation fadeOut = new AlphaAnimation(1, 0);
                    fadeOut.setDuration(ANIM_DURATION);
                    fadeOut.setAnimationListener(new FadeOutAnimation());

                    mTxvSeekBarValue.startAnimation(fadeOut);
                }
                return false;
            }
            });

        mSkbSample.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                int x = mSkbSample.getWidth()*progress/mSkbSample.getMax();
                int y = mTxvSeekBarValue.getTop();

                ShowSeekValue(x, y);
                mTxvSeekBarValue.setText(String.valueOf(progress - PROGRESS_ORIGIN) );

                AlphaAnimation fadeOut = new AlphaAnimation(1, 0);
                fadeOut.setDuration(ANIM_DURATION);
                fadeOut.setAnimationListener(new FadeOutAnimation());

                mTxvSeekBarValue.startAnimation(fadeOut);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    protected void ShowSeekValue(int x, int y)
    {
        if(x > 0 && x < mSkbSample.getWidth())
        {

            AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                                             ViewGroup.LayoutParams.WRAP_CONTENT,
                                                               x, y);
            mTxvSeekBarValue.setLayoutParams(lp);
        }
    }

    public void setProgress(int progress){
        mSkbSample.setProgress(progress);
    }

    public int getProgress(){
        return mSkbSample.getProgress();
    }

    public class FadeOutAnimation implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mTxvSeekBarValue.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

}
