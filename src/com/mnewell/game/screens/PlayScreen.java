package com.mnewell.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.mnewell.game.ResultData;
import com.mnewell.game.TriviaSwipe;
import com.mnewell.game.Question;

import java.util.Random;


public class PlayScreen implements Screen, InputProcessor {

    final TriviaSwipe triviaSwipe;


    Stage stage;

    int score = 0;

    Label scoreLbl;
    Label option1;
    Label option2;
    Label timeLeftInRound;
    Image questionImage;
    Label questionLbl;

    Table main;
    Table data;
    Image arrowLeft, arrowRight;

    Label categoryLbl;

    Table nested;

    Question q;

    float timePerRound;

    float acc = 0f;

    Sound correctSound, incorrectSound;

    boolean gameEnding = false;


    Array<Integer> times = new Array<Integer>();
    Array<ResultData> results;

    AssetManager assetManager;
    Table getReadyTable;
    Label getReadyLbl;
    TextButton start;

    Array<String> competitors;
    String currentPlayer;


    Array<Question> incorrectQuestions;
    int roundCount = 0;
    int numberOfRounds = 0;
    int questionsCorrect = 0;
    int questionsIncorrect = 0;
    int questionIndex = 0;
    boolean timer = true;
    boolean roundInMotion;
    private boolean multiplayer = false;

    public PlayScreen(final TriviaSwipe triviaSwipe, Array<String> competitors) {
        this.triviaSwipe = triviaSwipe;
        assetManager = triviaSwipe.myAssetManager.manager;
        this.competitors = competitors;
        currentPlayer = competitors.get(0);
        numberOfRounds = competitors.size;
        results = new Array<ResultData>();
        multiplayer = true;
        triviaSwipe.myAssetManager.gameList.shuffle();
        initComponents();
        initMultiplayerComponents();
        buildGetReadyTable();


    }

    public PlayScreen(final TriviaSwipe triviaSwipe) {
        this.triviaSwipe = triviaSwipe;
        assetManager = triviaSwipe.myAssetManager.manager;
        triviaSwipe.myAssetManager.gameList.shuffle();
        initComponents();
        buildGameTable();

    }

    public PlayScreen(final TriviaSwipe triviaSwipe, boolean timer) {
        this.triviaSwipe = triviaSwipe;
        assetManager = triviaSwipe.myAssetManager.manager;
        this.timer = timer;
        triviaSwipe.myAssetManager.gameList.shuffle();
        initComponents();
        buildGameTable();
    }


    private void buildGetReadyTable() {
        getReadyTable = new Table();
        getReadyTable.top();
        getReadyTable.setWidth(Gdx.graphics.getWidth());
        getReadyTable.setHeight(Gdx.graphics.getHeight() - triviaSwipe.adHeight);
        getReadyTable.setY(triviaSwipe.adHeight);
        getReadyTable.row();
        getReadyTable.add(getReadyLbl).expand();
        getReadyTable.row();
        getReadyTable.add(start).expand();
        stage.addActor(getReadyTable);
    }

    private void buildGameTable() {


        main = new Table();
        data = new Table();
        nested = new Table();

        main.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(2)));

        main.setWidth(Gdx.graphics.getWidth());
        main.setHeight(Gdx.graphics.getHeight() - triviaSwipe.adHeight);
        main.setY(triviaSwipe.adHeight);


        main.top();
        main.add(nested).height(triviaSwipe.adHeight);
        nested.setBackground(triviaSwipe.myAssetManager.header.getDrawable());
        nested.add(categoryLbl).expand().center();
        main.row();
        main.add(data).prefHeight(Math.round((Gdx.graphics.getHeight() - triviaSwipe.adHeight * 2) * .9)).expand().center();
        data.row();
        data.add(timeLeftInRound).expand().left().uniform();
        data.add(scoreLbl).expand().right().uniform();
        data.row();
        data.add(questionLbl).expand().center().colspan(2).width((Math.round(Gdx.graphics.getWidth() * .80)));
        data.row();
        data.add(option1).expand().left().width((Math.round(Gdx.graphics.getWidth() * .40)));
        data.add(option2).expand().right().width((Math.round(Gdx.graphics.getWidth() * .40)));
        data.row();
        data.add(arrowLeft).expand().left();
        data.add(arrowRight).expand().right();
        data.row();
        data.add(questionImage).prefHeight(Math.round(Gdx.graphics.getWidth() * .80)).colspan(2).prefWidth(Math.round(Gdx.graphics.getWidth() * .80)).colspan(2);


        stage.addActor(main);
    }


    private void initComponents() {
        stage = new Stage();

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
        Gdx.input.setCatchBackKey(true);

        q = triviaSwipe.myAssetManager.gameList.get(0);
        questionIndex = 0;
        incorrectQuestions = new Array<Question>();
        times = new Array<Integer>();
        timePerRound = 90;
        correctSound = assetManager.get("mp3/correct.mp3", Sound.class);
        incorrectSound = assetManager.get("mp3/incorrect.mp3", Sound.class);

        arrowLeft = new Image(assetManager.get("ui_images/left.png", Texture.class));
        arrowRight = new Image(assetManager.get("ui_images/right.png", Texture.class));
        categoryLbl = new Label(q.category, triviaSwipe.myAssetManager.bigStyle);
        scoreLbl = new Label("Score: " + score, triviaSwipe.myAssetManager.bigStyle);
        scoreLbl.setColor(Color.GREEN);
        scoreLbl.setAlignment(Align.right);
        option1 = new Label(q.rightAnswer, triviaSwipe.myAssetManager.bigStyle);
        option1.setAlignment(Align.left);
        option1.setWrap(true);
        option2 = new Label(q.wrongAnswer, triviaSwipe.myAssetManager.bigStyle);
        option2.setAlignment(Align.right);
        option2.setWrap(true);
        triviaSwipe.myAssetManager.loadTexture(q.path);
        assetManager.finishLoading();
        questionImage = new Image(assetManager.get("questions/images/" + q.path, Texture.class));
        questionImage.setAlign(Align.center);
        questionLbl = new Label(q.query, triviaSwipe.myAssetManager.bigStyle);
        questionLbl.setWrap(true);
        questionLbl.setAlignment(Align.center);
        timeLeftInRound = new Label("Time left: " + timePerRound, triviaSwipe.myAssetManager.bigStyle);

        if (!timer) {
            timeLeftInRound.setText("Time left: --");
        }
        timeLeftInRound.setAlignment(Align.left);

        stage.addListener(new ActorGestureListener() {
            public void fling(InputEvent event, float velocityX, float velocityY, int button) {

                if (velocityX > 0) {

                    if (option2.getText().toString().equalsIgnoreCase(q.rightAnswer)) {
                        correct();
                    } else {
                        incorrect();
                    }

                } else {

                    if (option1.getText().toString().equalsIgnoreCase(q.rightAnswer)) {
                        correct();

                    } else {
                        incorrect();
                    }
                }
            }
        });
    }

    private void initMultiplayerComponents() {

        start = new TextButton("START", triviaSwipe.myAssetManager.buttonStyle);
        getReadyLbl = new Label(currentPlayer + ", it's your turn.", triviaSwipe.myAssetManager.bigStyle);

        start.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                roundInMotion = true;
                for (Actor a : stage.getActors()) {
                    a.remove();
                }
                buildGameTable();


            }
        });

    }


    private void endGame() {
        ResultData rd = new ResultData(score, questionsCorrect, questionsIncorrect, times);
        triviaSwipe.transitionTo(new ResultsScreen(triviaSwipe, rd), main);

    }

    private void endRound() {

        ResultData rd = new ResultData(currentPlayer, score, questionsCorrect, questionsIncorrect, times);
        results.add(rd);

        roundInMotion = false;

        roundCount++;
        if (roundCount >= numberOfRounds) {
            triviaSwipe.transitionTo(new ResultsScreen(triviaSwipe, results, competitors), main);

        } else {
            questionIndex = 0;
            score = 0;
            questionsCorrect = 0;
            questionsIncorrect = 0;
            currentPlayer = competitors.get(roundCount);
            timePerRound = 90;
            for (Actor a : stage.getActors()) {
                a.remove();
            }
            getReadyLbl.setText(currentPlayer + ", it's your turn.");
            buildGetReadyTable();
        }
    }


    private void incorrect() {
        times.add(Math.round(acc));
        acc = 0f;
        questionIndex++;
        questionsIncorrect++;
        incorrectSound.play();
        timePerRound -= 3;
        score--;
        scoreLbl.setText("Score: " + score);


        changeQuestion();

    }

    private void correct() {
        times.add(Math.round(acc));
        acc = 0f;
        triviaSwipe.myAssetManager.unloadTexture("questions/images/" + q.path);

        questionsCorrect++;
        correctSound.play();
        score++;
        scoreLbl.setText("Score: " + score);

        changeQuestion();


    }

    private void changeQuestion() {
        questionIndex++;


        if (questionIndex >= triviaSwipe.myAssetManager.gameList.size) {
            if (!multiplayer) {
                endGame();
            } else {
                endRound();
            }
        } else {
            q = triviaSwipe.myAssetManager.gameList.get(questionIndex);

            triviaSwipe.myAssetManager.loadTexture(q.path);
            assetManager.finishLoading();
            questionImage.setDrawable(new SpriteDrawable(new Sprite(assetManager.get("questions/images/" + q.path, Texture.class))));
            questionLbl.setText(q.query);
            categoryLbl.setText(q.category);

            Random r = new Random();
            int randomNumber = r.nextInt(2);

            if (randomNumber == 0) {
                option1.setText(q.rightAnswer);
                option2.setText(q.wrongAnswer);
            } else {
                option2.setText(q.rightAnswer);
                option1.setText(q.wrongAnswer);
            }
        }


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        update(delta);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }


    private void update(float delta) {

        if (timer) {
            timeLeftInRound.setText("Time left: " + Math.round(timePerRound) + "s");
            acc += delta;
            timePerRound -= delta;
            if (timePerRound <= 0) {
                if (!gameEnding) {
                    if (!multiplayer) {
                        endGame();
                    } else {
                        endRound();
                    }
                }
                gameEnding = true;
            }
        }

        if (score < 0) {
            scoreLbl.setColor(Color.RED);
        } else {
            scoreLbl.setColor(Color.GREEN);
        }
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        correctSound.dispose();
        incorrectSound.dispose();
        stage.dispose();

    }


    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK) {
            triviaSwipe.transitionTo(new SelectModeScreen(triviaSwipe), main);
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
