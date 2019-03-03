package ru.list.gwozdev;

import com.MainGame;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.managers.GameManager;
import com.managers.InputManager;
import com.managers.NetworkManager;

import static com.managers.Player.playerCreator;


public class LastAvanpostGame implements Screen {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    public static int onlineNumberOfPlayer;

    private float w, h;//переменные для хранения значений размеров высоты и ширины области просмотра нашей игры
    public static MainGame game;

    public  LastAvanpostGame(MainGame game,int numberOfPlayer) {
        onlineNumberOfPlayer = numberOfPlayer;
        this.game=game;
        w = Gdx.graphics.getWidth();//узнаём размеры для области просмотра
        h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(w, h);//создаём экземпляр камеры и устанавливаем размеры области просмотра
        camera.setToOrtho(false);// центруем камеру w/2,h/2
        batch = new SpriteBatch();
        GameManager.initialize(w, h);//инициализируем игру

    }

    @Override
    public void dispose() {
        //уничтожаем batch и вызываем метод dispose класса GameManager
        batch.dispose();
        GameManager.dispose();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
// Очищаем экран
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //устанавливаем вид spriteBatch созданному нами объекту camera
        batch.setProjectionMatrix(camera.combined);
        //отрисовываем игровые объекты путём вызова метода renderGame класса GameManager
        InputManager.handleInput(camera);
        batch.begin();
        GameManager.renderGame(batch);
        batch.end();
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

}