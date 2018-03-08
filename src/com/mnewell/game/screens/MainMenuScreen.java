package com.mnewell.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mnewell.game.TriviaSwipe;


public class MainMenuScreen implements Screen {

    final TriviaSwipe triviaSwipe;


    Stage stage;

    Image logo;

    TextButton play, options, store, about, highscores;

    Table main, nested;

    AssetManager assetManager;


    public MainMenuScreen(final TriviaSwipe triviaSwipe) {
        this.triviaSwipe = triviaSwipe;
        this.assetManager = triviaSwipe.myAssetManager.manager;
        initComponents();

    }

    private void initComponents() {
        stage = new Stage();


        Gdx.input.setInputProcessor(stage);
        logo = new Image(assetManager.get("ui_images/logo.png", Texture.class));


        main = new Table();
        nested = new Table();
        highscores = new TextButton("HIGHSCORES", triviaSwipe.myAssetManager.buttonStyle);
        about = new TextButton("ABOUT", triviaSwipe.myAssetManager.buttonStyle);
        play = new TextButton("PLAY", triviaSwipe.myAssetManager.buttonStyle);
        options = new TextButton("OPTIONS", triviaSwipe.myAssetManager.buttonStyle);
        store = new TextButton("STORE", triviaSwipe.myAssetManager.buttonStyle);

        main.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(2)));

        main.setWidth(Gdx.graphics.getWidth());
        main.setHeight(Gdx.graphics.getHeight() - triviaSwipe.adHeight);
        main.setY(triviaSwipe.adHeight);

        main.top();
        main.add(logo).height(triviaSwipe.adHeight + (triviaSwipe.adHeight / 2)).width(Gdx.graphics.getWidth());
        main.row();
        main.add(nested).expand();
        nested.add(play).width(Gdx.graphics.getWidth() / 2).spaceBottom(play.getHeight() / 2);
        nested.row();
        nested.add(options).width(Gdx.graphics.getWidth() / 2).spaceBottom(play.getHeight() / 2);
        nested.row();
        nested.add(store).width(Gdx.graphics.getWidth() / 2).spaceBottom(play.getHeight() / 2);
        nested.row();
        nested.add(about).width(Gdx.graphics.getWidth() / 2);
        nested.row();
        stage.addActor(main);

        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                triviaSwipe.transitionTo(new SelectModeScreen(triviaSwipe), main);

            }
        });

        options.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                triviaSwipe.transitionTo(new OptionsScreen(triviaSwipe, "menu"), main);
            }
        });


        about.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                triviaSwipe.transitionTo(new AboutScreen(triviaSwipe), main);
            }

        });

        store.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                triviaSwipe.transitionTo(new StoreScreen(triviaSwipe), main);
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
}
