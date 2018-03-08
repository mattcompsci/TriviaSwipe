package com.mnewell.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mnewell.game.screens.MainMenuScreen;


public class TriviaSwipe extends Game {

    public MyAssetManager myAssetManager;
    public int adHeight;
    public Skin skin;

    SpriteBatch spriteBatch;

    public TriviaSwipe(int adHeight) {
        this.adHeight = adHeight;
    }


    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("skin/ui.json"));
        myAssetManager = new MyAssetManager(this);
        myAssetManager.manager.get("mp3/music.mp3", Music.class).setVolume(myAssetManager.MUSIC_VOLUME);
        myAssetManager.manager.get("mp3/music.mp3", Music.class).play();
        this.setScreen(new MainMenuScreen(this));

    }

    public void renderBackground() {
        spriteBatch.begin();
        spriteBatch.draw(myAssetManager.manager.get("ui_images/background.png", Texture.class), 0, 0, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
        spriteBatch.end();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderBackground();
        super.render();
    }

    @Override
    public void dispose() {
        myAssetManager.manager.dispose();
        spriteBatch.dispose();

    }

    public void transitionTo(final Screen transferToo, Table table) {
        final Screen s = transferToo;
        Action completeAction = new Action() {
            public boolean act(float delta) {
                setScreen(s);
                return true;
            }
        };
        myAssetManager.manager.get("mp3/teleport.wav", Sound.class).play();
        table.addAction(Actions.sequence(Actions.fadeOut(1), completeAction));


    }
}
