package com.slenderman.scenes;

import com.slenderman.actors.Item;
import com.slenderman.actors.ItemDirector;
import com.slenderman.actors.Player;
import com.slenderman.game.Console;
import com.slenderman.tools.Sound;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Shed extends Scene {
  private boolean _max_iteration_not_reached;
  public final int MAX_ITERATION_DISPLAY_STORIES = 10;
  private Scanner choice;
  private Player player;
  private final ArrayList<Item> itemsInThisScene = ItemDirector.getItemsForScene("shed");
  private final Item Key = ItemDirector.findThisItem("key", itemsInThisScene);

  public final String FILE_BASE_NAME = "storyShedNoColor";
  public final String PATH = "com.slenderman.scenes.files.";

  ResourceBundle.Control rbc =
      ResourceBundle.Control.getControl(ResourceBundle.Control.FORMAT_DEFAULT);
  ResourceBundle bundle = ResourceBundle.getBundle(PATH + FILE_BASE_NAME, Locale.US, rbc);

  public Shed() {
    setSceneName("shed");
    setItemsInScene(itemsInThisScene);
  }

  public Shed(
      Scene sceneToTheNorth, Scene sceneToTheSouth, Scene sceneToTheEast, Scene sceneToTheWest) {
    super(sceneToTheNorth, sceneToTheSouth, sceneToTheEast, sceneToTheWest);
    setSceneName("shed");
    setItemsInScene(itemsInThisScene);
  }

  @Override
  public void enter(Scanner in, Player player) throws Exception {
    Console.clearScreen();
    this.player = player;
    choice = in;
    Console.updateMap(this.getSceneName());
    inFrontOfShed();
  }

  private void inFrontOfShed() throws InterruptedException, FileNotFoundException {
    String choice;

    String shed =
      "<pre color='lime'>                            +&-              </pre>"+
        "<pre color='lime'>                          _.-^-._    .--.  </pre>"+
        "<pre color='lime'>                       .-'   _   '-. |__|	</pre>"+
        "<pre color='lime'>                      /     |_|     \\|  | </pre>"+
        "<pre color='lime'>                     /               \\  |	</pre>"+
        "<pre color='lime'>                    /|     _____     |\\ |	</pre>"+
        "<pre color='lime'>                     |    |==|==|    |  |	</pre>"+
        "<pre color='lime'> |---|---|---|---|---|    |--|--|    |  |	</pre>"+
        "<pre color='lime'> |---|---|---|---|---|    |==|==|    |  |	</pre>"+
        "<pre color='lime'>^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^	</pre>";

    Console.updateImage(shed);
    displayStories("inFrontShed");

    choice = playerChoice();
    if (choice.equals("0")) {
      stepIntoTheShed();
    } else if (choice.equals("1")) {
      goSomewhereElse();
    } else {
      displayStories("inFrontShed_WrongInput");

      inFrontOfShed();
    }
  }

  private void goSomewhereElse() throws InterruptedException {
    displayStories("goSomewhereElse");
  }

  private void stepIntoTheShed() throws InterruptedException, FileNotFoundException {
    displayStories("stepIntoTheShed");
    Sound.play(new File("./Speech/Shed/coatPocket.mp3"));
    takeShinyThingChoice();
  }

  private void takeShinyThingChoice() throws InterruptedException, FileNotFoundException {
    displayStories("takeShinyThingChoice");
    String choice = playerChoice().toUpperCase();
    if (choice.equals("Y")) {
      grabShinyThingYes();
    } else if (choice.equals("N")) {
      inFrontOfShed();
    } else {
      displayStories("takeShinyThingChoice_WrongInput");
      takeShinyThingChoice();
    }
  }

  private void grabShinyThingYes() throws InterruptedException, FileNotFoundException {
    System.out.println(textPainter(bundle.getString("grabShinyThingYes_0")));
    Thread.sleep(3000);
    System.out.println(textPainter(bundle.getString("grabShinyThingYes_1")));
    System.out.println(textPainter(bundle.getString("grabShinyThingYes_2")));
    Thread.sleep(2000);
    displayStories("grabShinyThingYes_Note");
    Sound.play(new File("./Speech/Shed/note.mp3"));
    exitShed();
  }

  private void exitShed() throws InterruptedException {
    player.addItemToInventory(Key);
    getItemsInScene().remove(Key);

    Thread.sleep(2000);
    System.out.println(textPainter(bundle.getString("exitShed_0")));

    Thread.sleep(2000);
    System.out.println(textPainter(bundle.getString("exitShed_1")));

    goSomewhereElse();
  }

  private String playerChoice() {
    return choice.nextLine();
  }

  /**
   * Coloring the fonts
   *
   * <p>{0} : Scene.ANSI_GREEN {1} : Scene.ANSI_BLUE {2} : Scene.ANSI_RED {3} : Scene.ANSI_BLACK {4}
   * : Scene.ANSI_WHITE
   */
  private String textPainter(String text) {
    return MessageFormat.format(
        text,
        Scene.ANSI_GREEN,
        Scene.ANSI_BLUE,
        Scene.ANSI_RED,
        Scene.ANSI_BLACK,
        Scene.ANSI_WHITE);
  }

  /** For accessing and displaying stories in Resource Bundle file */
  private void displayStories(String key) throws InterruptedException {
    Thread.sleep(3000);
    Console.clearScreen();
    _max_iteration_not_reached = false;
    for (int i = 0; i < MAX_ITERATION_DISPLAY_STORIES; i++) {
      try {
        System.out.println(textPainter(bundle.getString(key + "[" + i + "]")));
      } catch (MissingResourceException e) {
        _max_iteration_not_reached = true;
        break;
      }
    }
  }

  @Override
  public String toString() {
    return "Shed{" +
      "FILE_BASE_NAME='" + FILE_BASE_NAME + '\'' +
      ", PATH='" + PATH + '\'' +
      ", rbc=" + rbc +
      ", bundle=" + bundle +
      ", _max_iteration_not_reached=" + _max_iteration_not_reached +
      ", MAX_ITERATION_DISPLAY_STORIES=" + MAX_ITERATION_DISPLAY_STORIES +
      ", choice=" + choice +
      ", player=" + player +
      ", itemsInThisScene=" + itemsInThisScene +
      ", Key=" + Key +
      "} " + super.toString();
  }
}
