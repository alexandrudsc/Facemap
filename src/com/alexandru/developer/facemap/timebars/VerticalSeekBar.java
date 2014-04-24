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
 * Created by Alexandru on 4/22/14.
 * Vertical seek bar used to choose a person belonging to the selected region
 */
public class VerticalSeekBar extends RelativeLayout {
    public static final int PROGRESS_ORIGIN = 2500;

    TextView mTxvSeekBarValue;
    SeekBar mSkbSample;

    public final int ANIM_DURATION=500;

    public VerticalSeekBar(Context context) {
        super(context);
        init(context);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    protected void init(Context context){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.vertical_seek_bar, this);
        mTxvSeekBarValue = (TextView) this.findViewById(R.id.txvSeekBarValue);
        mSkbSample = (SeekBar) this.findViewById(R.id.skbSample);
        mSkbSample.setOnTouchListener(new OnTouchListener() {
            //Countinuous action.Display text view near touch event.
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    ShowSeekValue( (int) mSkbSample.getRight(), (int) event.getY());
                }
                else if(event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    ShowSeekValue( (int) mSkbSample.getRight(), (int) event.getY());
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

                int y = mSkbSample.getHeight() * (mSkbSample.getMax() - progress) /mSkbSample.getMax();
                int x = mSkbSample.getRight();

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
        if(x > 0 && x < mSkbSample.getHeight())
        {

            AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    x, y);
            mTxvSeekBarValue.setLayoutParams(lp);
        }
    }

    public void setOnChangeProgressBarListener(SeekBar.OnSeekBarChangeListener l){
        mSkbSample.setOnSeekBarChangeListener(l);
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

