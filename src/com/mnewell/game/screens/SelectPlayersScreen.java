package com.mnewell.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import com.mnewell.game.TriviaSwipe;


public class SelectPlayersScreen implements Screen, InputProcessor {
    private final TriviaSwipe triviaSwipe;

    private Stage stage;
    private Table main, headerTable, nestedTable, buttonTable;
    private List<String> playerList;

    Label createGame;

    private TextButton addPlayer, startButton, removeButton;
    private List<String> categoryList;


    public SelectPlayersScreen(final TriviaSwipe myGdxGame) {
        this.triviaSwipe = myGdxGame;


        initComponents();
        initListeners();

    }

    private void initComponents(){
        stage = new Stage();


        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
        Gdx.input.setCatchBackKey(true);

        categoryList = new List<String>(triviaSwipe.skin);

        playerList = new List(triviaSwipe.skin);

        startButton = new TextButton("START", triviaSwipe.myAssetManager.buttonStyle);
        removeButton = new TextButton("REMOVE", triviaSwipe.myAssetManager.buttonStyle);
        addPlayer = new TextButton("NEW USER", triviaSwipe.myAssetManager.buttonStyle);
        createGame = new Label("CREATE GAME", triviaSwipe.myAssetManager.bigStyle);
        createGame.setAlignment(Align.center);


        main = new Table();
        headerTable = new Table();
        nestedTable = new Table();
        buttonTable = new Table();




        ScrollPane p = new ScrollPane(playerList, triviaSwipe.skin);

        main.setWidth(Gdx.graphics.getWidth());
        main.setHeight(Gdx.graphics.getHeight()- triviaSwipe.adHeight);
        main.setY(triviaSwipe.adHeight);




        main.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(2)));

        main.top();
        main.add(headerTable).height(triviaSwipe.adHeight);
        headerTable.setBackground(triviaSwipe.myAssetManager.header.getDrawable());
        headerTable.add(createGame);
        main.row();
        main.add(nestedTable).grow().center();
        nestedTable.add(p).expand().size(Gdx.graphics.getWidth()/3, (Gdx.graphics.getHeight() - triviaSwipe.adHeight*2)/2);


        buttonTable.add(addPlayer).expand().prefWidth(Gdx.graphics.getWidth()/3);
        buttonTable.row();
        buttonTable.add(removeButton).expand().prefWidth(Gdx.graphics.getWidth()/3);
        buttonTable.row();
        nestedTable.add(buttonTable).grow().center();
        main.row();
        main.add(startButton).expand().prefWidth(Gdx.graphics.getWidth()/2);

        stage.addActor(main);


    }

    private void initListeners(){
System.out.println("initing listeners");
        addPlayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                final Dialog addPlayerDialog = new Dialog("", triviaSwipe.skin);
                addPlayerDialog.getBackground().setMinHeight((Gdx.graphics.getHeight()- triviaSwipe.adHeight*2)/4);
                addPlayerDialog.getBackground().setMinWidth(Gdx.graphics.getWidth()/3);
                addPlayerDialog.row();
                final TextField textField = new TextField("New user", triviaSwipe.skin);
                TextButton save = new TextButton("SAVE", triviaSwipe.myAssetManager.buttonStyle);
                TextButton close = new TextButton("CLOSE", triviaSwipe.myAssetManager.buttonStyle);
                save.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        if(isValid(textField.getText())){
                            playerList.getItems().add(textField.getText());
                            addPlayerDialog.hide();
                        } else {
                            textField.setText("");
                        }
                    }
                });

                close.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        addPlayerDialog.hide();
                    }
                });

                addPlayerDialog.add(textField).expand().center().prefWidth(addPlayerDialog.getBackground().getMinWidth()/2).colspan(21);
                addPlayerDialog.row();

                addPlayerDialog.add(save).expand().center();
                addPlayerDialog.add(close).expand().center();

                addPlayerDialog.show(stage);



            }
        });

        removeButton.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playerList.getItems().removeIndex(playerList.getSelectedIndex());

            }
        });


        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                triviaSwipe.transitionTo(new PlayScreen(triviaSwipe, playerList.getItems()), main);
            }
        });



    }

    private boolean isValid(String name){
        for(String x: playerList.getItems()){
            if(x.contains(name)){
                final Dialog dialog = new Dialog("", triviaSwipe.skin);
                dialog.getBackground().setMinHeight((Gdx.graphics.getHeight()- triviaSwipe.adHeight*2)/4);
                dialog.getBackground().setMinWidth(Gdx.graphics.getWidth()/3);
                dialog.row();

                dialog.add(new Label("Invalid!", triviaSwipe.myAssetManager.bigStyle)).expand().center();
                dialog.row();
                final TextButton textButton = new TextButton("Ok", triviaSwipe.myAssetManager.buttonStyle);
                textButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        dialog.hide();
                    }
                });
                dialog.add(textButton).expand().center();


                dialog.show(stage);
                return false;
            }
        }
        return true;
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        if(playerList.getSelectedIndex() == -1){
            removeButton.setDisabled(true);
        } else {
            removeButton.setDisabled(false);
        }

        if(playerList.getItems().size < 2){
            startButton.setDisabled(true);
        } else {
            startButton.setDisabled(false);
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
