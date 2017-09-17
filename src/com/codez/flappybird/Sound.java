package com.codez.flappybird;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by codez on 2017/9/17.
 */
public class Sound {
    private AudioStream as;

    public Sound() {
    }

    public void play(String filename){
        try {
            FileInputStream url = new FileInputStream(filename);
            as = new AudioStream(url);
            AudioPlayer.player.start(as);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
