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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jshepdevelopment.lovechallenge.LoveChallenge;
import com.jshepdevelopment.lovechallenge.view.GameView;

public class MenuScreen implements Screen{

    private Game game;
    public static LoveChallenge loveChallengeGame;

    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/LadylikeBB.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();

    private TextureRegion backgroundTexture;
    private SpriteBatch batch;
    private Stage stage;
    private Table table;
    private TextButton buttonExit, buttonPlay, buttonTest;
    private Label heading;

    // define various screen sizes and resolutions
    int screenWidth = Gdx.graphics.getWidth();

    int baseWidth = 480;
    float scaleModifier = 0.5f;
    //Define the screen as MDPI as baseline
    GameView.ScreenType screenType = GameView.ScreenType.MDPI;



    public MenuScreen (Game game, LoveChallenge loveChallengeGame) {
        this.game = game;
        this.loveChallengeGame = loveChallengeGame;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1);
        stage.act(delta);
        batch.begin();
        batch.setColor(Color.WHITE);
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        //stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        stage.getViewport().update(width, height, true);
        //menuStage.getViewport().update(width, height, true);

    }

    @Override
    public void show() {

        Gdx.app.log("JSLOG", "screenWidth is " + screenWidth);

        if ( screenWidth <  baseWidth ) screenType = GameView.ScreenType.LDPI;
        if ( screenWidth >= baseWidth * 1 ) screenType = GameView.ScreenType.MDPI;
        if ( screenWidth >= baseWidth * 1.75 ) screenType = GameView.ScreenType.HDPI;
        if ( screenWidth >= baseWidth * 2 ) screenType = GameView.ScreenType.XHDPI;
        if ( screenWidth >= baseWidth * 3 ) screenType = GameView.ScreenType.XXHDPI;
        if ( screenWidth >= baseWidth * 4 ) screenType = GameView.ScreenType.XXXHDPI;

        Gdx.app.log("JSLOG", "screenType is " + screenType.toString());

        // Setting the base size for font MDPI screen
        parameter.size = 32;

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
            parameter.size = 38*2;
            scaleModifier = 8;
        }
        if(screenType== GameView.ScreenType.LDPI) {
            parameter.size = 38;
            scaleModifier = 2;
        }

        parameter.color = Color.RED;
//        parameter.borderWidth = 3;
//        parameter.borderColor = Color.PINK;

        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        textButtonStyle.font = font12;
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        batch = new SpriteBatch();
        backgroundTexture = new TextureRegion(new Texture(Gdx.files.internal("background/background.png")));
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //Button PLAY
        buttonPlay = new TextButton("Play", textButtonStyle);
        buttonPlay.pad(scaleModifier);
        buttonPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        //Button EXIT
        buttonExit = new TextButton("Exit", textButtonStyle);
        buttonExit.pad(scaleModifier);
        buttonExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        //Button TEST for testing Google Play Services
        buttonTest = new TextButton("Test Achievements", textButtonStyle);
        buttonTest.pad(scaleModifier);
        buttonTest.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Gdx.app.exit();
                loveChallengeGame.playServices.showAchievement();
            }
        });

        LabelStyle headingStyle = new LabelStyle(font12, new Color(255, 0, 255, 255));
        heading = new Label("Valentine's Day Love Challenge", headingStyle);

        //putting all that into a table
        table.add(heading);
        table.row();
        table.getCell(heading).spaceBottom(scaleModifier*5);
        table.row();
        table.add(buttonPlay);
        table.getCell(buttonPlay).spaceBottom(scaleModifier);
        table.row();
        table.add(buttonExit);
        table.getCell(buttonExit).spaceBottom(scaleModifier);
        table.row();
        table.add(buttonTest);
        table.getCell(buttonTest).spaceBottom(scaleModifier);
        stage.addActor(table);

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        //stage.getViewport().update(width, height, true);


    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
    }

}
