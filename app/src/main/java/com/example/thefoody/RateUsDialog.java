package com.example.thefoody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRatingBar;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

public class RateUsDialog extends Dialog {

    private float userRate = 0;

    public RateUsDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us_dialog);

        final AppCompatButton not_now_btn = findViewById(R.id.not_now_btn);
        final AppCompatButton rate_now_btn = findViewById(R.id.rate_now_btn);
        final RatingBar ratingBar = findViewById(R.id.ratingBar);
        final ImageView rating_imageView = findViewById(R.id.rating_imageView);

        rate_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Thanks for your feedback!",Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        not_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                if(v <=1){
                    rating_imageView.setImageResource(R.drawable.emojifinal1);
                }
                else if(v <=2){
                    rating_imageView.setImageResource(R.drawable.emojifinal2);
                }
                else if(v <=3){
                    rating_imageView.setImageResource(R.drawable.emojifinal3);
                }
                else if(v <=4){
                    rating_imageView.setImageResource(R.drawable.emojifinal4);
                }
                else if(v <=5){
                    rating_imageView.setImageResource(R.drawable.emojifinal5);
                }
                animateImage(rating_imageView);
                userRate = v;
            }
        });
    }

    private void animateImage(ImageView ratingImage){
        ScaleAnimation scaleAnimation =  new ScaleAnimation(0,1f,0,1f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);

        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(200);
        ratingImage.startAnimation(scaleAnimation);
    }
}