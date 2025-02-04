package com.example.itp4501_assignment;

import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

public class pirateState implements characterState{

    ImageView pirate;

    public  pirateState(ImageView pirate){
        this.pirate = pirate;
        initialize();
    }

    public void initialize(){
        AnimationDrawable animDrawable_pirate =
                (AnimationDrawable) pirate.getDrawable();

        pirate.post(new Runnable() {
            public void run() {
                animDrawable_pirate.start();
            }
        });
    }

    public void attack(){
        pirate.setImageResource(R.drawable.pirate_attack);
        AnimationDrawable animDrawable =(AnimationDrawable) pirate.getDrawable();


        pirate.post(new Runnable() {
            public void run() {
                animDrawable.start();
            }
        });
    }

    public void idle(){
        pirate.setImageResource(R.drawable.pirate_idle);
        AnimationDrawable animDrawable =(AnimationDrawable) pirate.getDrawable();


        pirate.post(new Runnable() {
            public void run() {
                animDrawable.start();
            }
        });
        //animDrawable.stop();
    }

    public void run(){
        pirate.setImageResource(R.drawable.pirate_run);
        AnimationDrawable animDrawable =(AnimationDrawable) pirate.getDrawable();


        pirate.post(new Runnable() {
            public void run() {
                animDrawable.start();
            }
        });
    }

    public void walkBack(){
        pirate.setImageResource(R.drawable.pirate_walk);
        AnimationDrawable animDrawable =(AnimationDrawable) pirate.getDrawable();


        pirate.post(new Runnable() {
            public void run() {
                animDrawable.start();
            }
        });
    }
}
