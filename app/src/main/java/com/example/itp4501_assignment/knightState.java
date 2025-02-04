package com.example.itp4501_assignment;

import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

public class knightState implements characterState {

    private ImageView knight;

     public knightState(ImageView knight){
         this.knight = knight;
         initialize();
     }

    public void initialize(){
        AnimationDrawable animDrawable_pirate =
                (AnimationDrawable) knight.getDrawable();

        knight.post(new Runnable() {
            public void run() {
                animDrawable_pirate.start();
            }
        });
    }
    public void attack(){
        knight.setImageResource(R.drawable.knight_attack);
        AnimationDrawable animDrawable =(AnimationDrawable) knight.getDrawable();


        knight.post(new Runnable() {
            public void run() {
                animDrawable.start();
            }
        });
    }

    public void idle(){
        knight.setImageResource(R.drawable.knight_idle);
        AnimationDrawable animDrawable =(AnimationDrawable) knight.getDrawable();


        knight.post(new Runnable() {
            public void run() {
                animDrawable.start();
            }
        });
    }
    public void run(){
        knight.setImageResource(R.drawable.knight_run);
        AnimationDrawable animDrawable =(AnimationDrawable) knight.getDrawable();


        knight.post(new Runnable() {
            public void run() {
                animDrawable.start();
            }
        });
    }

    public void walkBack(){
        knight.setImageResource(R.drawable.knight_walk);
        AnimationDrawable animDrawable =(AnimationDrawable) knight.getDrawable();


        knight.post(new Runnable() {
            public void run() {
                animDrawable.start();
            }
        });
    }
}
