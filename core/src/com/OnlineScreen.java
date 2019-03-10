package com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.io.IOException;

import ru.list.gwozdev.LastAvanpostGame;

public class OnlineScreen implements Screen {
    SpriteBatch batch; // объект для отрисовки спрайтов нашей игры
    OrthographicCamera camera; // область просмотра нашей игры
    Sprite createMatchButtonSprite;
    Sprite joinMatchSprite;
    Sprite backGroundSprite;
    Sprite backButtonSprite;
    Texture backGroundTexture;
    Texture createMatchTexture;
    Texture joinMatchTexture;
    Texture backButtonTexture;

    private static float BUTTON_RESIZE_FACTOR = 800f; // задаём относительный размер
    private static float createMatch_VERT_POSITION_FACTOR = 2.7f; // задаём позицию конпки start
    private static float joinMatch_VERT_POSITION_FACTOR = 4.2f; // задаём позицию кнопки exit
    private static float back_VERT_POSITION_FACTOR = 8.7f;
    public static MainGame game; // экземпляр класса MainGame нужен для доступа к вызову метода setScreen
    Vector3 temp = new Vector3(); // временный вектор для "захвата" входных координат
    public OnlineScreen(MainGame game){
        OnlineScreen.game = game;

        // получаем размеры экрана устройства пользователя и записываем их в переменнные высоты и ширины
        float height= Gdx.graphics.getHeight();
        float width = Gdx.graphics.getWidth();
        // устанавливаем переменные высоты и ширины в качестве области просмотра нашей игры
        camera = new OrthographicCamera(width,height);
        // этим методом мы центруем камеру на половину высоты и половину ширины
        camera.setToOrtho(false);
        batch = new SpriteBatch();
        // инициализируем текстуры и спрайты
        createMatchTexture = new Texture(Gdx.files.internal("createMatch_button.png"));
        joinMatchTexture = new Texture(Gdx.files.internal("join_button.png"));
        backGroundTexture = new Texture(Gdx.files.internal("menubackground.jpg"));
        backButtonTexture = new Texture(Gdx.files.internal("back_button.png"));
        createMatchButtonSprite = new Sprite(createMatchTexture);
        joinMatchSprite = new Sprite(joinMatchTexture);
        backGroundSprite = new Sprite(backGroundTexture);
        backButtonSprite = new Sprite(backButtonTexture);

        // устанавливаем размер и позиции
        createMatchButtonSprite.setSize(createMatchButtonSprite.getWidth() *(width/BUTTON_RESIZE_FACTOR), createMatchButtonSprite.getHeight()*(width/BUTTON_RESIZE_FACTOR));
        joinMatchSprite.setSize(joinMatchSprite.getWidth() *(width/BUTTON_RESIZE_FACTOR), joinMatchSprite.getHeight()*(width/BUTTON_RESIZE_FACTOR));
        backButtonSprite.setSize(backButtonSprite.getWidth() *(width/BUTTON_RESIZE_FACTOR), backButtonSprite.getHeight()*(width/BUTTON_RESIZE_FACTOR));
        backGroundSprite.setSize(width,height);
        createMatchButtonSprite.setPosition((width/2f -createMatchButtonSprite.getWidth()/2) , width/createMatch_VERT_POSITION_FACTOR);
        joinMatchSprite.setPosition((width/2f -joinMatchSprite.getWidth()/2) , width/joinMatch_VERT_POSITION_FACTOR);
        backButtonSprite.setPosition((width/2f -backButtonSprite.getWidth()/2) , width/back_VERT_POSITION_FACTOR);

    }


    @Override
    public void show() {
    }
    @Override
    public void render(float delta) {
        // Очищаем экран
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);// устанавливаем в экземпляр spritebatch вид с камеры (области просмотра)

        //отрисовка игровых объектов
        batch.begin();
        backGroundSprite.draw(batch);
        createMatchButtonSprite.draw(batch);
        joinMatchSprite.draw(batch);
        backButtonSprite.draw(batch);
        handleTouch();
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
        dispose();
    }
    @Override
    public void dispose() {
        createMatchTexture.dispose();
        joinMatchTexture.dispose();
        backButtonTexture.dispose();
//        batch.dispose();

    }
    void handleTouch(){
        // Проверяем были ли касание по экрану?
        if(Gdx.input.justTouched()) {
            // Получаем координаты касания и устанавливаем эти значения в временный вектор
            temp.set(Gdx.input.getX(),Gdx.input.getY(), 0);
            // получаем координаты касания относительно области просмотра нашей камеры
            camera.unproject(temp);
            float touchX = temp.x;
            float touchY= temp.y;
            // обработка касания по кнопке create match
            if((touchX>=createMatchButtonSprite.getX()) && touchX<= (createMatchButtonSprite.getX()+createMatchButtonSprite.getWidth()) && (touchY>=createMatchButtonSprite.getY()) && touchY<=(createMatchButtonSprite.getY()+createMatchButtonSprite.getHeight()) ){
                System.out.println("create match нажат");
                try {
                    game.setScreen(new LastAvanpostGame(game,1)); // Переход к экрану игры, игрок-сервер
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // обработка касания по кнопке join
            else if((touchX>=joinMatchSprite.getX()) && touchX<= (joinMatchSprite.getX()+joinMatchSprite.getWidth()) && (touchY>=joinMatchSprite.getY()) && touchY<=(joinMatchSprite.getY()+joinMatchSprite.getHeight()) ){
                System.out.println("join match нажат");
                try {
                    game.setScreen(new LastAvanpostGame(game,2)); // Переход к экрану игры, игрок-клиент
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if((touchX>=backButtonSprite.getX()) && touchX<= (backButtonSprite.getX()+backButtonSprite.getWidth()) && (touchY>=backButtonSprite.getY()) && touchY<=(backButtonSprite.getY()+backButtonSprite.getHeight()) ){
                System.out.println("back нажат");
                OnlineScreen.game.setScreen(new MenuScreen(game));
            }
        }
    }
}
