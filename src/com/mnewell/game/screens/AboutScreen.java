package com.mnewell.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mnewell.game.TriviaSwipe;

/**
 * Created by my_pc on 9/28/2017.
 */

public class AboutScreen implements Screen, InputProcessor {
    final TriviaSwipe triviaSwipe;
    Table table, headerTable;
    Stage stage;

    Label andrew;
    Label about;
    Label matt;
    Label content;


    public AboutScreen(TriviaSwipe triviaSwipe) {
        this.triviaSwipe = triviaSwipe;
        initComponents();
    }

    private void initComponents() {
        stage = new Stage();

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
        Gdx.input.setCatchBackKey(true);

        table = new Table();
        headerTable = new Table();

        content = new Label("Created by Matthew Newell and Andrew Rader, 2017", triviaSwipe.myAssetManager.mediumStyle);
        content.setWrap(true);
        content.setAlignment(Align.center);
        matt = new Label("coming soon", triviaSwipe.myAssetManager.mediumStyle);
        andrew = new Label("www.andrew-rader.com", triviaSwipe.myAssetManager.mediumStyle);
        about = new Label("ABOUT", triviaSwipe.myAssetManager.bigStyle);

        table.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(2)));


        table.top();
        table.setWidth(Gdx.graphics.getWidth());
        table.setHeight(Gdx.graphics.getHeight() - triviaSwipe.adHeight);
        table.setY(triviaSwipe.adHeight);

        table.add(headerTable).height(triviaSwipe.adHeight);
        headerTable.add(about).expand().center();
        headerTable.setBackground(triviaSwipe.myAssetManager.header.getDrawable());
        table.row();
        table.add(content).expand().center().width((Math.round(Gdx.graphics.getWidth() * .80)));
        ;
        table.row();
        table.add(matt);
        table.row();
        table.add(andrew);


        matt.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("clicked");
            }
        });

        andrew.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("http://www.andrew-rader.com/");
                System.out.println("clicked");
            }
        });

        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {


        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void transitionTo(Screen transferToo) {
        final Screen s = transferToo;
        Action completeAction = new Action() {
            public boolean act(float delta) {
                triviaSwipe.setScreen(s);
                return true;
            }
        };

        triviaSwipe.myAssetManager.manager.get("mp3/teleport.wav", Sound.class).play();
        table.addAction(Actions.sequence(Actions.fadeOut(1), completeAction));


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
        if (keycode == Input.Keys.BACK) {
            transitionTo(new MainMenuScreen(triviaSwipe));
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
