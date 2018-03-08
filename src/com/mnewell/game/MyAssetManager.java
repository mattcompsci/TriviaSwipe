package com.mnewell.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import java.util.ArrayList;
import java.util.HashSet;



public class MyAssetManager {


    public final TriviaSwipe triviaSwipe;
    public final AssetManager manager = new AssetManager();
    public Array<Question> questionList;
    public Array<Question> gameList;

    public Array<String> categories;
    public ArrayList<String> categoriesInPlay;

    public TextButton.TextButtonStyle buttonStyle;

    public Label.LabelStyle bigStyle;
    public Label.LabelStyle mediumStyle;

    public Image header;


    public static float MUSIC_VOLUME;
    public static float BUZZER_VOLUME;
    public static float TELEPORT_VOLUME;





    public MyAssetManager(TriviaSwipe triviaSwipe){
        this.triviaSwipe = triviaSwipe;
        questionList = loadQuestions();
        gameList = loadQuestions();
        categories = getCategories();
        populateCategoriesInPlay();



        loadImages();
        loadMusic();
        loadSounds();
        loadStyles();
        manager.finishLoading();

        loadWidgets();


    }


    private void populateCategoriesInPlay(){
        categoriesInPlay = new ArrayList<String>();

        for(String s: categories){
            categoriesInPlay.add(s);
        }

    }

    private void loadWidgets() {
        header = new Image(manager.get("ui_images/header.png", Texture.class));
    }


    public void loadStyles(){

        buttonStyle = triviaSwipe.skin.get("big", TextButton.TextButtonStyle.class);
        buttonStyle.font = triviaSwipe.skin.getFont("title");
        buttonStyle.fontColor = Color.WHITE;

        bigStyle = triviaSwipe.skin.get("title-white", Label.LabelStyle.class);
        mediumStyle = triviaSwipe.skin.get("default", Label.LabelStyle.class);
        mediumStyle.fontColor = Color.WHITE;


    }

    public Array<String> getCategories() {
        categories = new Array<String>();

        HashSet<String> hs = new HashSet<String>();

        for (int i = 0; i < questionList.size; i++) {

            if(!hs.add(questionList.get(i).category)){

            } else {
                if(questionList.get(i).category.equalsIgnoreCase("")){

                } else {
                    categories.add(questionList.get(i).category);

                }
            }
        }

        for(String s: categories){
            System.out.println(s + " is a category");
        }
        return categories;
    }

    private Array<Question> loadQuestions() {
        Array<Question> questionList = new Array<Question>();
        FileHandle fileHandle = Gdx.files.internal("questions/questions_csv.txt");
        String text = fileHandle.readString();

        String[] questions = text.split(";");
        for (int i = 0; i < questions.length; i++) {
            String[] questionData = questions[i].split(",");
            Question question = new Question(questionData[0], questionData[1], questionData[2], questionData[3], questionData[4]);
            questionList.add(question);

        }

        questionList.shuffle();
        return questionList;

    }







    private void loadImages(){
        manager.load("ui_images/logo.png", Texture.class);
        manager.load("ui_images/header.png", Texture.class);
        manager.load("ui_images/background.png", Texture.class);
        manager.load("ui_images/left.png", Texture.class);
        manager.load("ui_images/right.png", Texture.class);
    }

    public void loadTexture(String path){
        manager.load("questions/images/" + path, Texture.class);

    }

    public void unloadTexture(String path){
        manager.unload(path);
    }

    public void loadSounds(){
        manager.load("mp3/correct.mp3", Sound.class);
        manager.load("mp3/incorrect.mp3", Sound.class);
        manager.load("mp3/teleport.wav", Sound.class);
    }

    public void loadMusic(){
        manager.load("mp3/music.mp3", Music.class);
    }


    public void updateQuestionList() {

        gameList.clear();

        System.out.println("updating question list");
        System.out.println("question list size " + questionList.size);
        System.out.println("game list size "  + gameList.size);

        for(Question q: questionList){
            System.out.println(q.category);
            if(categoriesInPlay.contains(q.category)){
                gameList.add(q);
                System.out.println("question added category = "+ q.category);
            }
        }
        gameList.shuffle();

    }
}
