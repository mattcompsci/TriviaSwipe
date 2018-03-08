package com.mnewell.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.mnewell.game.TriviaSwipe;


public  class OptionsScreen implements Screen, InputProcessor {

    private final TriviaSwipe triviaSwipe;


    private Stage stage;
    private Table main, headerTbl, nested;

    private Label settingsLabel;
    private TextButton categories, sound;

    private  String comingFrom;
    public OptionsScreen(TriviaSwipe triviaSwipe, String comingFrom) {
        this.triviaSwipe = triviaSwipe;
        this.comingFrom = comingFrom;

        initComponents();
        initListeners();
    }


    private void initComponents() {
        stage = new Stage();
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);

        Gdx.input.setCatchBackKey(true);


        main = new Table();
        headerTbl = new Table();
        nested = new Table();


        main.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(2)));

        categories = new TextButton("CATEGORIES", triviaSwipe.myAssetManager.buttonStyle);
        sound = new TextButton("VOLUME", triviaSwipe.myAssetManager.buttonStyle);
        settingsLabel = new Label("OPTIONS", triviaSwipe.myAssetManager.bigStyle);


        main.setWidth(Gdx.graphics.getWidth());
        main.setHeight(Gdx.graphics.getHeight() - triviaSwipe.adHeight);
        main.setY(triviaSwipe.adHeight);

        main.top();
        main.add(headerTbl).height(triviaSwipe.adHeight);
        headerTbl.setBackground(triviaSwipe.myAssetManager.header.getDrawable());
        headerTbl.add(settingsLabel).expand().center();
        main.row();
        main.add(nested).expand();
        nested.add(categories).width(Gdx.graphics.getWidth() / 2).spaceBottom(categories.getHeight() / 2);
        nested.row();
        nested.add(sound).width(Gdx.graphics.getWidth() / 2).spaceBottom(categories.getHeight() / 2);
        nested.row();


        stage.addActor(main);

    }

    private void initListeners() {
        categories.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectCategories();

            }
        });


        sound.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                editSound();
            }
        });

    }

    private void editSound() {

        final Dialog editSoundDialog = new Dialog("", triviaSwipe.skin);
        editSoundDialog.getBackground().setMinHeight((Gdx.graphics.getHeight() - triviaSwipe.adHeight * 2) / 4);
        editSoundDialog.getBackground().setMinWidth(Gdx.graphics.getWidth() / 3);
        editSoundDialog.getTitleLabel().setStyle(triviaSwipe.myAssetManager.bigStyle);


        TextButton saveButton = new TextButton("SAVE", triviaSwipe.myAssetManager.buttonStyle);
        Label musicLabel = new Label("MUSIC", triviaSwipe.myAssetManager.bigStyle);
        Label buzzerLabel = new Label("BUZZERS", triviaSwipe.myAssetManager.bigStyle);
        Label transitionLabel = new Label("TELEPORT", triviaSwipe.myAssetManager.bigStyle);

        final Slider musicSlider = new Slider(.1f, 1f, .1f, false, triviaSwipe.skin);
        musicSlider.setValue(triviaSwipe.myAssetManager.MUSIC_VOLUME);
        editSoundDialog.setColor(Color.WHITE);
        final Slider buzzerSlider = new Slider(.01f, 1f, .1f, false, triviaSwipe.skin);
        buzzerSlider.setValue(triviaSwipe.myAssetManager.BUZZER_VOLUME);
        final Slider teleportSlider = new Slider(.01f, 1f, .1f, false, triviaSwipe.skin);
        teleportSlider.setValue(triviaSwipe.myAssetManager.TELEPORT_VOLUME);


        editSoundDialog.row();
        editSoundDialog.add(musicLabel);
        editSoundDialog.row();
        editSoundDialog.add(musicSlider);
        editSoundDialog.row();
        editSoundDialog.add(buzzerLabel);
        editSoundDialog.row();
        editSoundDialog.add(buzzerSlider);
        editSoundDialog.row();
        editSoundDialog.add(transitionLabel);
        editSoundDialog.row();
        editSoundDialog.add(teleportSlider);
        editSoundDialog.row();
        editSoundDialog.add(saveButton).expand().center().pad(saveButton.getHeight());
        saveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                triviaSwipe.myAssetManager.MUSIC_VOLUME = musicSlider.getValue();
                triviaSwipe.myAssetManager.BUZZER_VOLUME = buzzerSlider.getValue();
                triviaSwipe.myAssetManager.TELEPORT_VOLUME = teleportSlider.getValue();

                triviaSwipe.myAssetManager.manager.get("mp3/music.mp3",Music.class).setVolume(musicSlider.getValue());
                triviaSwipe.myAssetManager.manager.get("mp3/correct.mp3",Sound.class).setVolume(triviaSwipe.myAssetManager.manager.get("mp3/correct.mp3",Sound.class).play(),buzzerSlider.getValue());
                triviaSwipe.myAssetManager.manager.get("mp3/incorrect.mp3",Sound.class).setVolume(triviaSwipe.myAssetManager.manager.get("mp3/incorrect.mp3",Sound.class).play(),buzzerSlider.getValue());
                triviaSwipe.myAssetManager.manager.get("mp3/teleport.wav", Sound.class).setVolume(triviaSwipe.myAssetManager.manager.get("mp3/teleport.wav",Sound.class).play(),teleportSlider.getValue());


                editSoundDialog.hide();
            }
        });

        editSoundDialog.show(stage);
    }

    private void selectCategories() {
        CheckBox.CheckBoxStyle checkBoxStyle = triviaSwipe.skin.get("radio", CheckBox.CheckBoxStyle.class);
        checkBoxStyle.font = triviaSwipe.myAssetManager.bigStyle.font;
        checkBoxStyle.fontColor = Color.WHITE;


        TextButton saveCategoriesBtn = new TextButton("SAVE", triviaSwipe.myAssetManager.buttonStyle);

        final Array<CheckBox> cb = new Array<CheckBox>();

        final Dialog d = new Dialog("", triviaSwipe.skin);
        d.getBackground().setMinHeight((Gdx.graphics.getHeight() - triviaSwipe.adHeight * 2) / 4);
        d.getBackground().setMinWidth(Gdx.graphics.getWidth() / 3);


        for (int i = 0; i < triviaSwipe.myAssetManager.categories.size; i++) {
            CheckBox checkBox = new CheckBox(triviaSwipe.myAssetManager.categories.get(i), checkBoxStyle);
            checkBox.getImage().setColor(Color.WHITE);
            for (String s : triviaSwipe.myAssetManager.categoriesInPlay) {
                if (s.equalsIgnoreCase(triviaSwipe.myAssetManager.categories.get(i))) {
                    checkBox.setChecked(true);
                }
            }

            cb.add(checkBox);
        }
        for (int i = 0; i < cb.size; i++) {
            d.row();
            d.add(cb.get(i)).center().expand();
        }
        d.row();
        d.add(saveCategoriesBtn).expand().center();
        saveCategoriesBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                for (CheckBox c : cb) {
                    if (c.isChecked()) {
                        if (!triviaSwipe.myAssetManager.categoriesInPlay.contains(c.getText().toString())) {
                            triviaSwipe.myAssetManager.categoriesInPlay.add(c.getText().toString());
                        }
                    } else {
                        triviaSwipe.myAssetManager.categoriesInPlay.remove(c.getText().toString());
                    }

                }
                triviaSwipe.myAssetManager.updateQuestionList();

                d.hide();
            }
        });
        d.show(stage);
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

    public void transitionTo(Screen transferToo){
        final Screen s = transferToo;
        Action completeAction = new Action(){
            public boolean act( float delta ) {
                triviaSwipe.setScreen(s);
                return true;
            }
        };

        triviaSwipe.myAssetManager.manager.get("mp3/teleport.wav", Sound.class).play();
        main.addAction(Actions.sequence(Actions.fadeOut(1), completeAction));

    }
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK) {
            if(comingFrom.equalsIgnoreCase("menu")){
                transitionTo(new MainMenuScreen(triviaSwipe));
            }

            if(comingFrom.equalsIgnoreCase("select mode")) {

                transitionTo(new SelectModeScreen(triviaSwipe));
            }
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
