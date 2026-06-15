package me.abtu.graphics.ui;

import com.jogamp.newt.event.KeyEvent;
import me.abtu.Main;
import me.abtu.game.entity.player.Player;
import me.abtu.graphics.GraphicsBuffer;
import me.abtu.graphics.buttons.Button;
import me.abtu.util.Color;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PlayerMenu extends GraphicsBuffer {

    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;

    private final ArrayList<PlayerCard> playerCards = new ArrayList<>(2);
    private final HashSet<Integer> availableColors = new HashSet<>(List.of(Color.RED.hex()));

    private final Button addPlayerButton, removePlayerButton, startGameButton;

    private final Main main;

    public PlayerMenu(Main main, String renderer) {
        super(main, renderer);
        this.main = main;

        playerCards.add(new PlayerCard(KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_Q, KeyEvent.VK_E,
                this));
        main.addKeyPressEventListener(playerCards.getFirst().getKeybindEventListener());

        playerCards.add(new PlayerCard(KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SHIFT, KeyEvent.VK_CONTROL,
                this));
        main.addKeyPressEventListener(playerCards.get(1).getKeybindEventListener());

        final int buttonSize = 20;
        final int buttonMargin = 10;
        addPlayerButton = new Button.Builder(HALF_WIDTH - buttonMargin - buttonSize / 2f, REFERENCE_HEIGHT - FIFTH_HEIGHT,
                buttonSize, buttonSize, PConstants.CENTER, this::addPlayer)
                .text("+")
                .hoverExpand(1)
                .build();

        removePlayerButton = new Button.Builder(HALF_WIDTH + buttonMargin + buttonSize / 2f, REFERENCE_HEIGHT - FIFTH_HEIGHT,
                buttonSize, buttonSize, PConstants.CENTER, this::removePlayer)
                .text("-")
                .hoverExpand(1)
                .build();

        final int startButtonWidth = 80;
        final int startButtonHeight = 20;
        startGameButton = new Button.Builder(HALF_WIDTH, REFERENCE_HEIGHT - FIFTH_HEIGHT / 2f,
                startButtonWidth, startButtonHeight, PConstants.CENTER, main::startGame)
                .text("Start Game")
                .hoverExpand(2)
                .build();
    }

    private void addPlayer(Button button) {
        if (playerCards.size() >= MAX_PLAYERS)
            return;

        startGameButton.disable();

        PlayerCard playerCard = new PlayerCard(this);
        playerCards.add(playerCard);
        main.addKeyPressEventListener(playerCard.getKeybindEventListener());
    }

    private void removePlayer(Button button) {
        if (playerCards.size() <= MIN_PLAYERS)
            return;

        PlayerCard playerCard = playerCards.removeLast();
        main.removeKeyPressEventListener(playerCard.getKeybindEventListener());

        updateStartButtonState();
    }

    @Override
    protected void drawBuffer(Main main, PGraphics graphics, float mouseX, float mouseY) {
        addPlayerButton.update(mouseX, mouseY, main.mousePressed);
        removePlayerButton.update(mouseX, mouseY, main.mousePressed);
        startGameButton.update(mouseX, mouseY, main.mousePressed);
        graphics.fill(0);

        graphics.textFont(main.getTitleFont());
        graphics.textAlign(PConstants.CENTER, PConstants.CENTER);
        graphics.textSize(TITLE_SIZE);
        graphics.text("Choose Players", HALF_WIDTH, FIFTH_HEIGHT);

        graphics.rectMode(PConstants.CENTER);

        graphics.fill(Color.WHITE.hex());
        graphics.textFont(main.getDefaultFont());
        int playerCount = playerCards.size();
        for (int i = 0; i < playerCount; i++) {
            PlayerCard playerCard = playerCards.get(i);
            playerCard.draw(graphics, mouseX, mouseY, main.mousePressed, i, playerCount);
        }

        addPlayerButton.draw(graphics);
        removePlayerButton.draw(graphics);
        startGameButton.draw(graphics);
    }

    public void updateStartButtonState() {
        //check if all keybinds are bound; disable start button if not
        for (PlayerCard playerCard : playerCards) {
            for (int keybind : playerCard.getKeybinds()) {
                if (keybind == 0) {
                    startGameButton.disable();
                    return;
                }
            }
        }

        startGameButton.enable();
    }

    public void clearListeningButtons() {
        for (PlayerCard playerCard : playerCards)
            playerCard.clearListeningButton();
    }

    public boolean canBindKey(int keyCode) {
        if (keyCode == KeyEvent.VK_ESCAPE)
            return false;

        for (PlayerCard playerCard : playerCards) {
            int[] keybinds = playerCard.getKeybinds();
            for (int keybind : keybinds) {
                if (keybind == keyCode)
                    return false;
            }
        }
        return true;
    }

    public Player[] getPlayers(Main main) {
        Player[] players = new Player[playerCards.size()];
        for (int i = 0; i < playerCards.size(); i++) {
            PlayerCard playerCard = playerCards.get(i);

            final float horizontalFraction = (float) i / (players.length - 1);
            final PImage spriteLeft = main.loadImage("sprites/player/" + (i + 1) + "_left.png");
            final PImage spriteRight = main.loadImage("sprites/player/" + (i + 1) + "_right.png");
            final Runnable deathEventListener = main::checkForWin;
            final Player player = new Player(playerCard.getKeybinds(), horizontalFraction, deathEventListener, main.getSoundManager(), spriteLeft, spriteRight);

            main.addKeyPressEventListener(player.getKeyPressListener());
            main.addKeyReleaseEventListener(player.getKeyReleaseListener());

            players[i] = player;
        }
        return players;
    }

    public HashSet<Integer> getAvailableColors() {
        return availableColors;
    }

    public void cleanup(Main main) {
        for (PlayerCard playerCard : playerCards)
            main.removeKeyPressEventListener(playerCard.getKeybindEventListener());
    }
}