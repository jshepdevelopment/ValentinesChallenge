package com.jshepdevelopment.lovechallenge.screens;

/**
 * Created by Jason Shepherd on 1/6/2017.
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jshepdevelopment.lovechallenge.LoveChallenge;
import com.jshepdevelopment.lovechallenge.view.GameView;

public class EndScreen implements Screen {

    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/LadylikeBB.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();

    private TextureRegion backgroundTexture;
    private SpriteBatch spriteBatch;
    private int score;

    private TextButton buttonBack, buttonScore;
    private Label heading;
    private Label scoreLabel;
    private Game game;
    private Stage stage;
    private Table table;

    // define various screen sizes and resolutions
    int screenWidth = Gdx.graphics.getWidth();

    int baseWidth = 480;
    float scaleModifier = 0.5f;
    //Define the screen as MDPI as baseline
    GameView.ScreenType screenType = GameView.ScreenType.MDPI;

    public static LoveChallenge loveChallengeGame;

    public EndScreen(int score, Game game, LoveChallenge loveChallengeGame) {
        this.score = score;
        this.game = game;
        this.loveChallengeGame = loveChallengeGame;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        spriteBatch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void show() {

        // Submit the score to play services
        loveChallengeGame.playServices.submitScore(score);

        if ( screenWidth <  baseWidth ) screenType = GameView.ScreenType.LDPI;
        if ( screenWidth >= baseWidth * 1 ) screenType = GameView.ScreenType.MDPI;
        if ( screenWidth >= baseWidth * 1.5 ) screenType = GameView.ScreenType.HDPI;
        if ( screenWidth >= baseWidth * 2 ) screenType = GameView.ScreenType.XHDPI;
        if ( screenWidth >= baseWidth * 3 ) screenType = GameView.ScreenType.XXHDPI;
        if ( screenWidth >= baseWidth * 4 ) screenType = GameView.ScreenType.XXXHDPI;

        // Setting the base size for font MDPI screen
        parameter.size = 38;

        // Set sizes relative to screen type
        if(screenType== GameView.ScreenType.XXXHDPI) {
            parameter.size = 38*4;
            scaleModifier = 20;
        }
        if(screenType== GameView.ScreenType.XXHDPI) {
            parameter.size = 38*3;
            scaleModifier = 15;
        }
        if(screenType== GameView.ScreenType.XHDPI) {
            parameter.size = 38*2;
            scaleModifier = 12;
        }
        if(screenType== GameView.ScreenType.HDPI) {
            parameter.size = 38*2;
            scaleModifier = 8;
        }
        if(screenType== GameView.ScreenType.MDPI) {
            parameter.size = 32;
            scaleModifier = 1;
        }
        if(screenType== GameView.ScreenType.LDPI) {
            parameter.size = 24;
            scaleModifier = 1;
        }

        parameter.color = Color.RED;

        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        textButtonStyle.font = font12;
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        backgroundTexture = new TextureRegion(new Texture(Gdx.files.internal("background/background.png")));
        spriteBatch = new SpriteBatch();

        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Label.LabelStyle headingStyle = new Label.LabelStyle(font12, new Color(255, 0, 255, 255));

        heading = new Label("", headingStyle);
        scoreLabel = new Label(score + " taps in ten seconds!", headingStyle);

        buttonBack = new TextButton("Try Again", textButtonStyle);
        buttonBack.pad(scaleModifier);
        buttonBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game, loveChallengeGame));
            }
        });

        buttonScore = new TextButton("High Scores", textButtonStyle);
        buttonScore.pad(scaleModifier);
        buttonScore.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loveChallengeGame.playServices.showScore();
                //game.setScreen(new MenuScreen(game, loveChallengeGame));
            }
        });

        table.add(heading);
        table.row();
        table.getCell(heading).spaceBottom(scaleModifier*5);
        table.row();
        table.add(scoreLabel);
        table.getCell(scoreLabel).spaceBottom(scaleModifier*5);
        table.row();
        table.add(buttonBack);
        table.getCell(buttonBack).spaceBottom(scaleModifier*5);
        table.row();
        table.add(buttonScore);
        stage.addActor(table);

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }

}