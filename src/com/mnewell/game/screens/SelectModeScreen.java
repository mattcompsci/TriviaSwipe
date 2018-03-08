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
import com.badlogic.gdx.utils.Align;

import com.mnewell.game.TriviaSwipe;


public class SelectModeScreen implements Screen, InputProcessor{



    final TriviaSwipe triviaSwipe;
    Stage stage;


    Table main;
    Table nested;
    Table headerTbl;

    TextButton relaxedModeBtn, multiPlayerModeBtn, competitiveModeBtn, modeSettings;

    Label selectModeLbl;

    InputMultiplexer multiplexer;

    AssetManager assetManager;

    public SelectModeScreen(TriviaSwipe triviaSwipe) {
        this.triviaSwipe = triviaSwipe;
        this.assetManager = triviaSwipe.myAssetManager.manager;

        initComponents();
        initListeners();
    }

    private void initComponents() {
        stage = new Stage();
        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
        Gdx.input.setCatchBackKey(true);


        main = new Table();
        headerTbl = new Table();
        nested = new Table();


        relaxedModeBtn = new TextButton("RELAXED", triviaSwipe.myAssetManager.buttonStyle);
        multiPlayerModeBtn = new TextButton("MULTIPLAYER", triviaSwipe.myAssetManager.buttonStyle);
        competitiveModeBtn = new TextButton("CHALLENGE", triviaSwipe.myAssetManager.buttonStyle);
        modeSettings = new TextButton("OPTIONS", triviaSwipe.myAssetManager.buttonStyle);
        selectModeLbl = new Label("SELECT MODE", triviaSwipe.myAssetManager.bigStyle);
        selectModeLbl.setAlignment(Align.center);


        main.setWidth(Gdx.graphics.getWidth());
        main.setHeight(Gdx.graphics.getHeight() - triviaSwipe.adHeight);
        main.setY(triviaSwipe.adHeight);


        main.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(2)));

        main.top();
        main.add(headerTbl).height(triviaSwipe.adHeight);
        headerTbl.setBackground(triviaSwipe.myAssetManager.header.getDrawable());
        headerTbl.add(selectModeLbl).expand().center();
        main.row();
        main.add(nested).expand();
        nested.add(relaxedModeBtn).width(Gdx.graphics.getWidth()/2).spaceBottom(relaxedModeBtn.getHeight()/2);
        nested.row();
        nested.add(competitiveModeBtn).width(Gdx.graphics.getWidth()/2).spaceBottom(relaxedModeBtn.getHeight()/2);
        nested.row();
        nested.add(multiPlayerModeBtn).width(Gdx.graphics.getWidth()/2).spaceBottom(relaxedModeBtn.getHeight()/2);
        nested.row();
        nested.add(modeSettings).width(Gdx.graphics.getWidth()/2);
        stage.addActor(main);



    }


    private void initListeners() {
        relaxedModeBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                triviaSwipe.transitionTo(new PlayScreen(triviaSwipe, false), main);
            }
        });

        multiPlayerModeBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                triviaSwipe.transitionTo(new SelectPlayersScreen(triviaSwipe), main);

            }
        });

        competitiveModeBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                triviaSwipe.transitionTo(new PlayScreen(triviaSwipe), main);
            }
        });

        modeSettings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                triviaSwipe.transitionTo(new OptionsScreen(triviaSwipe, "select mode"), main);

            }
        });
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

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
            triviaSwipe.transitionTo(new MainMenuScreen(triviaSwipe), main);
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
