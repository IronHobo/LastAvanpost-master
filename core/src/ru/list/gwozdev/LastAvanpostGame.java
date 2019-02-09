package ru.list.gwozdev;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.managers.GameManager;
import com.managers.InputManager;

import static com.managers.Player.playerCreator;


public class LastAvanpostGame extends ApplicationAdapter {

		private OrthographicCamera camera;
		private SpriteBatch batch;
		//переменные для хранения значений размеров
		//высоты и ширины области просмотра нашей игры
		private float w,h;

		@Override
		public void create() {
			//узнаём размеры для области просмотра
			w = Gdx.graphics.getWidth();
			h = Gdx.graphics.getHeight();
			//создаём экземпляр камеры и устанавливаем размеры области просмотра
			camera = new OrthographicCamera(w,h);
			// центруем камеру w/2,h/2
			camera.setToOrtho(false);
			batch = new SpriteBatch();
			//инициализируем игру
			//временно, пока нет менюшки
			GameManager.initialize(w,h);
		}

		@Override
		public void dispose() {
			//уничтожаем batch и вызываем метод dispose класса GameManager
			batch.dispose();
			GameManager.dispose();
		}

		@Override
		public void render() {
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

	}