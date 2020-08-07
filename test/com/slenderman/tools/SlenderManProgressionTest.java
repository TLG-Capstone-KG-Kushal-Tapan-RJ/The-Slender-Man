package com.slenderman.tools;

import com.slenderman.actors.SlenderMan;
import org.junit.Test;

import java.util.Timer;

import static org.junit.Assert.*;

public class SlenderManProgressionTest {

  @Test
  public void positiveSlenderManProgressionTest() throws InterruptedException {
    int minutes = 1;
    SlenderManProgression test = new SlenderManProgression(minutes);
    test.timer = new Timer();
    test.timer.schedule(
      new SlenderManProgression.SlenderManTimer(), minutes * 1_000);

    // This is needed so that the above tasks have enough time to complete once.
    Thread.sleep(1_001);

    assertEquals(1, SlenderMan.getCompletions());
  }

  @Test (expected = IllegalArgumentException.class)
  public void negativeSlenderManProgressionTestBadInputException() throws InterruptedException {
    int minutes = -15;
    SlenderManProgression test = new SlenderManProgression(minutes);
    test.timer = new Timer();
    test.timer.schedule(
      new SlenderManProgression.SlenderManTimer(), minutes * 1_000);

    // This is needed so that the above tasks have enough time to complete once.
    Thread.sleep(1_001);

    assertNotEquals(1, SlenderMan.getCompletions());
  }
}
