package ru.list.gwozdev;

import com.MainGame;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.managers.GameManager;
import com.managers.InputManager;
import com.managers.NetworkManager;
import com.managers.Player;
import com.managers.Server;
import com.managers.Client;

import java.io.IOException;

import static com.managers.Client.clientListen;
import static com.managers.InputManager.activePlayer;
import static com.managers.InputManager.doingMove;
import static com.managers.InputManager.handleSurrender;
import static com.managers.Player.playerCreator;
import static com.managers.Server.connectionsListner;


public class LastAvanpostGame implements Screen {
    boolean flagFirstMove;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    public static int onlineNumberOfPlayer;

    private float w, h;//переменные для хранения значений размеров высоты и ширины области просмотра нашей игры
    public static MainGame game;

    public  LastAvanpostGame(MainGame game,int numberOfPlayer) throws IOException {
        onlineNumberOfPlayer = numberOfPlayer;
        this.game=game;
        w = Gdx.graphics.getWidth();//узнаём размеры для области просмотра
        h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(w, h);//создаём экземпляр камеры и устанавливаем размеры области просмотра
        camera.setToOrtho(false);// центруем камеру w/2,h/2
        batch = new SpriteBatch();
        if (LastAvanpostGame.onlineNumberOfPlayer==0){
            System.out.println("не по сети");
        }
        else if (LastAvanpostGame.onlineNumberOfPlayer==1){
            new Server();
            connectionsListner();
        }
        else{
            new Client();
        }

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
        if (LastAvanpostGame.onlineNumberOfPlayer==2&&activePlayer.numberOfPlayer==1) {  // поведение клиента, ход сервера
            String coordsMove = null;
            try {
                coordsMove = clientListen();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Vector3 someoneMove = new Vector3();
            someoneMove.fromString(coordsMove); //преобразую строку с координатами касания от клиента в vector3
            handleSurrender(someoneMove);
            doingMove(someoneMove);
        }
        else if(LastAvanpostGame.onlineNumberOfPlayer==1&&activePlayer.numberOfPlayer!=1){  // поведение сервера, ход клиента
            String coordsMove = null; //получаю строку с координатами касания от клиента
                try {
                    coordsMove = Server.serverListen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            Vector3 clientMove = new Vector3();
            clientMove.fromString(coordsMove); //преобразую строку с координатами касания от клиента в vector3
            handleSurrender(clientMove);
            doingMove(clientMove);
        }
        else {
            try {
                Vector3 startVector = new Vector3(w, h, 0);
                if (Player.totalMoves==0 && !flagFirstMove) {               //костыль, передаем 1 раз фейковый клик, чтобы у клиентов прогрузился экран с клетками
                    Server.serverSpeak(startVector.toString());
                    flagFirstMove=true;
                }
                InputManager.handleInput(camera);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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