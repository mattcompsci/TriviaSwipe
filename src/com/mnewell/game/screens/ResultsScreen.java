package com.mnewell.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.mnewell.game.ResultData;
import com.mnewell.game.SortByScore;
import com.mnewell.game.TriviaSwipe;



public class ResultsScreen implements Screen, InputProcessor {



    private Stage stage;





    final TriviaSwipe triviaSwipe;
    Array<String> competitors;
    TextButton menu, playAgain, previous, next;

    Label gameOver;
    Label score;
    Label yourScore;


    Label yourQuestionsCorrect;
    Label questionsCorrect;
    Label questionsIncorrect;
    Label yourQuestionsIncorrect;

    Label answered;
    Label answeredCount;
    Label yourTimeSpentPerQuestion;


    Label avgTimeSpent;

    Table table, header, nested;



    Array<ResultData> results;

    AssetManager assetManager;

    int playerIndex;

    ResultData resultData;

    boolean multiplayer = false;

    public ResultsScreen(final TriviaSwipe triviaSwipe, ResultData resultData){
        this.triviaSwipe = triviaSwipe;
        this.resultData = resultData;
        this.assetManager = triviaSwipe.myAssetManager.manager;
        playerIndex = 0;
        initSharedComponents();
        initSinglePlayerComponents();


    }


    public ResultsScreen(final TriviaSwipe triviaSwipe, Array<ResultData> results, Array<String> competitors){
        this.triviaSwipe = triviaSwipe;
        this.results = results;
        results.sort(new SortByScore());
        results.reverse();
        this.competitors = competitors;
        multiplayer = true;
        initSharedComponents();
        initMultiPlayerComponents();


    }




    private void initSharedComponents(){
        stage = new Stage();

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
        Gdx.input.setCatchBackKey(true);


        table = new Table();
        header = new Table();
        nested = new Table();

        table.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(2)));

        table.setWidth(Gdx.graphics.getWidth());
        table.setHeight(Gdx.graphics.getHeight()- triviaSwipe.adHeight);
        table.setY(triviaSwipe.adHeight);
        header.setBackground(triviaSwipe.myAssetManager.header.getDrawable());



        gameOver = new Label("G A M E  O V E R", triviaSwipe.myAssetManager.bigStyle);

        yourScore = new Label("SCORE", triviaSwipe.myAssetManager.mediumStyle);
        score = new Label("", triviaSwipe.myAssetManager.bigStyle);

        answered = new Label("SWIPES", triviaSwipe.myAssetManager.mediumStyle);
        answeredCount = new Label("", triviaSwipe.myAssetManager.bigStyle);

        yourQuestionsCorrect = new Label("CORRECT", triviaSwipe.myAssetManager.mediumStyle);
        questionsCorrect = new Label("", triviaSwipe.myAssetManager.bigStyle);

        yourQuestionsIncorrect = new Label("INCORRECT", triviaSwipe.myAssetManager.mediumStyle);
        questionsIncorrect = new Label("", triviaSwipe.myAssetManager.bigStyle);

        yourTimeSpentPerQuestion = new Label("AVG TIME PER QUESTION", triviaSwipe.myAssetManager.mediumStyle);
        avgTimeSpent = new Label("", triviaSwipe.myAssetManager.bigStyle);


        menu = new TextButton("BACK TO MENU", triviaSwipe.myAssetManager.buttonStyle);
        playAgain = new TextButton("PLAY AGAIN", triviaSwipe.myAssetManager.buttonStyle);

        menu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("menu pressed!");
                triviaSwipe.transitionTo(new MainMenuScreen(triviaSwipe), table);

            }
        });

        playAgain.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(multiplayer){
                    triviaSwipe.transitionTo(new PlayScreen(triviaSwipe, competitors), table);
                } else{
                    triviaSwipe.transitionTo(new SelectModeScreen(triviaSwipe), table);
                }


            }
        });


        table.top();
        table.add(header).height(triviaSwipe.adHeight);
        header.add(gameOver).expand().center();
        table.row();
        table.add(nested).growY().prefWidth(Gdx.graphics.getWidth()/2);






    }
    private void initMultiPlayerComponents() {



        previous = new TextButton("PREVIOUS", triviaSwipe.myAssetManager.buttonStyle);
        previous.setDisabled(true);
        next = new TextButton("NEXT PLAYER", triviaSwipe.myAssetManager.buttonStyle);


        score.setText(results.get(0).score+"");
        answeredCount.setText(results.get(0).answered+"");
        questionsCorrect.setText(results.get(0).questionsCorrect+"");
        questionsIncorrect.setText(results.get(0).questionsIncorrect+"");
        avgTimeSpent.setText(results.get(0).averageTimePerQuestion+"");


        next.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                playerIndex++;
                yourScore.setText((playerIndex + 1) + " PLACE, " + results.get(playerIndex).name);
                score.setText(results.get(playerIndex).score + "");
                answeredCount.setText(results.get(playerIndex).answered + "");
                questionsCorrect.setText(results.get(playerIndex).questionsCorrect + "");
                questionsIncorrect.setText(results.get(playerIndex).questionsIncorrect + "");
                avgTimeSpent.setText(results.get(playerIndex).averageTimePerQuestion + "");

            }
        });


        previous.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playerIndex--;
                yourScore.setText((playerIndex + 1) + " PLACE, " + results.get(playerIndex).name);
                score.setText(results.get(playerIndex).score + "");
                answeredCount.setText(results.get(playerIndex).answered + "");
                questionsCorrect.setText(results.get(playerIndex).questionsCorrect + "");
                questionsIncorrect.setText(results.get(playerIndex).questionsIncorrect + "");
                avgTimeSpent.setText(results.get(playerIndex).averageTimePerQuestion + "");
            }
        });


        nested.add(yourScore).expand().left().colspan(2);
        nested.row();
        nested.add(score).expand().right().colspan(2);
        nested.row();
        nested.add(answered).expand().left().colspan(2);
        nested.row();
        nested.add(answeredCount).expand().right().colspan(2);
        nested.row();
        nested.add(yourQuestionsCorrect).expand().left().colspan(2);
        nested.row();
        nested.add(questionsCorrect).expand().right().colspan(2);
        nested.row();
        nested.add(yourQuestionsIncorrect).expand().left().colspan(2);
        nested.row();
        nested.add(questionsIncorrect).expand().right().colspan(2);
        nested.row();
        nested.add(yourTimeSpentPerQuestion).expand().left().colspan(2);
        nested.row();
        nested.add(avgTimeSpent).expand().right().colspan(2);
        nested.row();
        nested.add(previous).expand().center().width(gameOver.getWidth());
        nested.add(next).expand().center().width(gameOver.getWidth());
        nested.row();
        nested.add(playAgain).expand().center().width(gameOver.getWidth());
        nested.add(menu).expand().center().width(gameOver.getWidth());

        stage.addActor(table);



    }


    private void initSinglePlayerComponents(){


        score.setText(resultData.score+"");
        answeredCount.setText(resultData.answered+"");
        questionsCorrect.setText(resultData.questionsCorrect+"");
        questionsIncorrect.setText(resultData.questionsIncorrect+"");
        avgTimeSpent.setText(resultData.averageTimePerQuestion+"");


        nested.add(yourScore).expand().left();
        nested.row();
        nested.add(score).expand().right();
        nested.row();
        nested.add(answered).expand().left();
        nested.row();
        nested.add(answeredCount).expand().right();
        nested.row();
        nested.add(yourQuestionsCorrect).expand().left();
        nested.row();
        nested.add(questionsCorrect).expand().right();
        nested.row();
        nested.add(yourQuestionsIncorrect).expand().left();
        nested.row();
        nested.add(questionsIncorrect).expand().right();
        nested.row();
        nested.add(yourTimeSpentPerQuestion).expand().left();
        nested.row();
        nested.add(avgTimeSpent).expand().right();
        nested.row();
        nested.add(playAgain).expand().center().width(gameOver.getWidth());
        nested.row();
        nested.add(menu).expand().center().width(gameOver.getWidth());

        stage.addActor(table);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        if(multiplayer){
            if(playerIndex == 0){
                previous.setDisabled(true);
            } else {
                previous.setDisabled(false);
            }

            if(playerIndex == results.size-1) {
                next.setDisabled(true);
            } else {
                next.setDisabled(false);
            }
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
        stage.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            triviaSwipe.transitionTo(new MainMenuScreen(triviaSwipe), table);
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
