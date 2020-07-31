package com.slenderman.tools;

import javax.sound.sampled.*;
import java.io.File;

class Sound {

  static void play(File file)
  {
    try
    {
      Clip clip = AudioSystem.getClip();
      clip.open(AudioSystem.getAudioInputStream(file));
      clip.start();

      Thread.sleep(clip.getMicrosecondLength()/1000);
    }
    catch (Exception exc)
    {
      exc.printStackTrace(System.out);
    }
  }

//  public static void main(String[] args) {
//    File oneMinSound=new File("vampire-hiss.wav");
//    play(oneMinSound);
//  }
}
