package com;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import ru.list.gwozdev.LastAvanpostGame;


public class MenuScreen implements Screen {
    SpriteBatch batch; // объект для отрисовки спрайтов нашей игры
    OrthographicCamera camera; // область просмотра нашей игры

    Texture startButtonTexture;
    Texture exitButtonTexture;
    Texture backGroundTexture;
    Texture onlineButtonTexture;
    Sprite startButtonSprite;
    Sprite exitButtonSprite;
    Sprite backGroundSprite;
    Sprite onlineButtonSprite;


    private static float BUTTON_RESIZE_FACTOR = 800f; // задаём относительный размер
    private static float START_VERT_POSITION_FACTOR = 2.7f; // задаём позицию конпки start
    private static float EXIT_VERT_POSITION_FACTOR = 4.2f; // задаём позицию кнопки exit
    private static float ONLINE_VERT_POSITION_FACTOR = 8.7f;
    public static MainGame game; // экземпляр класса MainGame нужен для доступа к вызову метода setScreen
    Vector3 temp = new Vector3(); // временный вектор для "захвата" входных координат
    public MenuScreen(MainGame game){
        this.game = game;

        // получаем размеры экрана устройства пользователя и записываем их в переменнные высоты и ширины
        float height= Gdx.graphics.getHeight();
        float width = Gdx.graphics.getWidth();
        // устанавливаем переменные высоты и ширины в качестве области просмотра нашей игры
        camera = new OrthographicCamera(width,height);
        // этим методом мы центруем камеру на половину высоты и половину ширины
        camera.setToOrtho(false);
        batch = new SpriteBatch();
        // инициализируем текстуры и спрайты
        startButtonTexture = new Texture(Gdx.files.internal("start_button.png"));
        exitButtonTexture = new Texture(Gdx.files.internal("exit_button.png"));
        backGroundTexture = new Texture(Gdx.files.internal("menubackground.jpg"));
        onlineButtonTexture = new Texture(Gdx.files.internal("online_button.png"));
        startButtonSprite = new Sprite(startButtonTexture);
        exitButtonSprite = new Sprite(exitButtonTexture);
        backGroundSprite = new Sprite(backGroundTexture);
        onlineButtonSprite = new Sprite(onlineButtonTexture);
        exitButtonSprite = new Sprite(exitButtonTexture);
        // устанавливаем размер и позиции
        startButtonSprite.setSize(startButtonSprite.getWidth() *(width/BUTTON_RESIZE_FACTOR), startButtonSprite.getHeight()*(width/BUTTON_RESIZE_FACTOR));
        exitButtonSprite.setSize(exitButtonSprite.getWidth() *(width/BUTTON_RESIZE_FACTOR), exitButtonSprite.getHeight()*(width/BUTTON_RESIZE_FACTOR));
        onlineButtonSprite.setSize(onlineButtonSprite.getWidth() *(width/BUTTON_RESIZE_FACTOR), onlineButtonSprite.getHeight()*(width/BUTTON_RESIZE_FACTOR));
        backGroundSprite.setSize(width,height);
        startButtonSprite.setPosition((width/2f -startButtonSprite.getWidth()/2) , width/START_VERT_POSITION_FACTOR);
        exitButtonSprite.setPosition((width/2f -exitButtonSprite.getWidth()/2) , width/EXIT_VERT_POSITION_FACTOR);
        onlineButtonSprite.setPosition((width/2f -onlineButtonSprite.getWidth()/2) , width/ONLINE_VERT_POSITION_FACTOR);

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
        startButtonSprite.draw(batch);
        exitButtonSprite.draw(batch);
        onlineButtonSprite.draw(batch);
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
        startButtonTexture.dispose();
        exitButtonTexture.dispose();
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
            // обработка касания по кнопке Start
            if((touchX>=startButtonSprite.getX()) && touchX<= (startButtonSprite.getX()+startButtonSprite.getWidth()) && (touchY>=startButtonSprite.getY()) && touchY<=(startButtonSprite.getY()+startButtonSprite.getHeight()) ){
                System.out.println("старт нажат");
                game.setScreen(new LastAvanpostGame(game,0)); // Переход к экрану игры
            }
            // обработка касания по кнопке Exit
            else if((touchX>=exitButtonSprite.getX()) && touchX<= (exitButtonSprite.getX()+exitButtonSprite.getWidth()) && (touchY>=exitButtonSprite.getY()) && touchY<=(exitButtonSprite.getY()+exitButtonSprite.getHeight()) ){
                Gdx.app.exit(); // выход из приложения
            }
            else if((touchX>=onlineButtonSprite.getX()) && touchX<= (onlineButtonSprite.getX()+onlineButtonSprite.getWidth()) && (touchY>=onlineButtonSprite.getY()) && touchY<=(onlineButtonSprite.getY()+onlineButtonSprite.getHeight()) ){
                System.out.println("онлайн нажат");
                game.setScreen(new OnlineScreen(game));
            }
        }
    }

}