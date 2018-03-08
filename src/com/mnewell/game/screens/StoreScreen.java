package com.mnewell.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mnewell.game.TriviaSwipe;


public class StoreScreen implements Screen, InputProcessor {
    final TriviaSwipe triviaSwipe;

    Table table, headerTable;
    Stage stage;

    Label store;
    Label content;


    public StoreScreen(TriviaSwipe triviaSwipe) {
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

        content = new Label("Coming Soon", triviaSwipe.myAssetManager.bigStyle);
        content.setWrap(true);
        content.setAlignment(Align.center);

        store = new Label("STORE", triviaSwipe.myAssetManager.bigStyle);

        table.addAction(Actions.sequence(Actions.fadeIn(2)));


        table.top();

        table.setWidth(Gdx.graphics.getWidth());
        table.setHeight(Gdx.graphics.getHeight() - triviaSwipe.adHeight);
        table.setY(triviaSwipe.adHeight);

        table.add(headerTable).height(triviaSwipe.adHeight);
        headerTable.add(store);
        headerTable.setBackground(triviaSwipe.myAssetManager.header.getDrawable());
        table.row();
        table.add(content).expand().center().width((Math.round(Gdx.graphics.getWidth() * .80)));



        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


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
