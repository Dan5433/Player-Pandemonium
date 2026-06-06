package me.abtu.graphics.ui;

import com.jogamp.newt.event.KeyEvent;
import me.abtu.Main;
import me.abtu.game.Player;
import me.abtu.graphics.GraphicsBuffer;
import me.abtu.graphics.buttons.Button;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PVector;

import java.util.ArrayList;

public class PlayerMenu extends GraphicsBuffer {

    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;

    private final ArrayList<PlayerCard> playerCards = new ArrayList<>(2);

    private final Button addPlayerButton, removePlayerButton, startGameButton;

    private final Main main;

    public PlayerMenu(Main main, int resizeMode) {
        super(main, resizeMode);
        this.main = main;

        playerCards.add(new PlayerCard(KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_Q, KeyEvent.VK_E,
                this::clearListeningButtons, this::canBindKey, this::updateStartButtonState));
        main.addKeyPressEventListener(playerCards.getFirst().getKeybindEventListener());

        playerCards.add(new PlayerCard(KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SHIFT, KeyEvent.VK_CONTROL,
                this::clearListeningButtons, this::canBindKey, this::updateStartButtonState));
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

        PlayerCard playerCard = new PlayerCard(this::clearListeningButtons, this::canBindKey, this::updateStartButtonState);
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

        graphics.fill(255);
        final float cardMargin = REFERENCE_WIDTH / 64f;
        final float cardWidth = REFERENCE_WIDTH / 5f;
        final float cardHeight = REFERENCE_HEIGHT / 2.4f;
        int numPlayers = playerCards.size();
        for (int i = 0; i < numPlayers; i++) {
            PlayerCard playerCard = playerCards.get(i);

            Button leftKeybindButton = playerCard.getLeftKeybindButton();
            Button rightKeybindButton = playerCard.getRightKeybindButton();
            Button jumpKeybindButton = playerCard.getJumpKeybindButton();
            Button primaryKeybindButton = playerCard.getPrimaryKeybindButton();
            Button secondaryKeybindButton = playerCard.getSecondaryKeybindButton();

            float offset = i - (numPlayers - 1) / 2f;
            float cardX = HALF_WIDTH + offset * (cardWidth + cardMargin);
            graphics.fill(255);
            graphics.rect(cardX, HALF_HEIGHT, cardWidth, cardHeight);


            PVector buttonTranslate = new PVector();
            graphics.translate(cardX, HALF_HEIGHT - cardHeight / 2f);
            buttonTranslate.add(new PVector(cardX, HALF_HEIGHT - cardHeight / 2f));

            //draw player keybinds
            {
                graphics.fill(0);
                graphics.textFont(main.getDefaultFont());
                graphics.textSize(SMALL_TEXT_SIZE);

                final float textMargin = 3;
                graphics.textAlign(PConstants.CENTER, PConstants.TOP);
                graphics.text("Player " + (i + 1), 0, textMargin);

                graphics.translate(-cardWidth / 2f, SMALL_TEXT_SIZE);
                buttonTranslate.add(new PVector(-cardWidth / 2f, SMALL_TEXT_SIZE));
                graphics.textAlign(PConstants.LEFT, PConstants.TOP);

                graphics.translate(textMargin, textMargin + SMALL_TEXT_SIZE);
                buttonTranslate.add(new PVector(textMargin, textMargin + SMALL_TEXT_SIZE));
                graphics.text("Left:", 0, 0);
                leftKeybindButton.update(mouseX - buttonTranslate.x, mouseY - buttonTranslate.y, main.mousePressed);
                leftKeybindButton.draw(graphics);

                graphics.translate(0, SMALL_TEXT_SIZE);
                buttonTranslate.y += SMALL_TEXT_SIZE;
                graphics.text("Right:", 0, 0);
                rightKeybindButton.update(mouseX - buttonTranslate.x, mouseY - buttonTranslate.y, main.mousePressed);
                rightKeybindButton.draw(graphics);

                graphics.translate(0, SMALL_TEXT_SIZE);
                buttonTranslate.y += SMALL_TEXT_SIZE;
                graphics.text("Jump:", 0, 0);
                jumpKeybindButton.update(mouseX - buttonTranslate.x, mouseY - buttonTranslate.y, main.mousePressed);
                jumpKeybindButton.draw(graphics);

                graphics.translate(0, SMALL_TEXT_SIZE);
                buttonTranslate.y += SMALL_TEXT_SIZE;
                graphics.text("Primary:", 0, 0);
                primaryKeybindButton.update(mouseX - buttonTranslate.x, mouseY - buttonTranslate.y, main.mousePressed);
                primaryKeybindButton.draw(graphics);

                graphics.translate(0, SMALL_TEXT_SIZE);
                buttonTranslate.y += SMALL_TEXT_SIZE;
                graphics.text("Secondary:", 0, 0);
                secondaryKeybindButton.update(mouseX - buttonTranslate.x, mouseY - buttonTranslate.y, main.mousePressed);
                secondaryKeybindButton.draw(graphics);


                graphics.translate(-textMargin, -(textMargin + SMALL_TEXT_SIZE * 5));
                graphics.translate(-(cardX - cardWidth / 2f), -(HALF_HEIGHT - cardHeight / 2f + SMALL_TEXT_SIZE));
            }
        }

        addPlayerButton.draw(graphics);
        removePlayerButton.draw(graphics);
        startGameButton.draw(graphics);
    }

    private void updateStartButtonState() {
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

    private void clearListeningButtons() {
        for (PlayerCard playerCard : playerCards)
            playerCard.clearListeningButton();
    }

    private boolean canBindKey(int keyCode) {
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

            float horizontalFraction = (float) i / (players.length - 1);
            Player player = new Player(playerCard.getKeybinds(), horizontalFraction);

            main.addKeyPressEventListener(player.getKeyPressListener());
            main.addKeyReleaseEventListener(player.getKeyReleaseListener());

            players[i] = player;
        }
        return players;
    }
}