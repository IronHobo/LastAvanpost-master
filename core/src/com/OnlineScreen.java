package com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OnlineScreen implements Screen {
    SpriteBatch batch; // объект для отрисовки спрайтов нашей игры
    OrthographicCamera camera; // область просмотра нашей игры
    Sprite createMatchButtonSprite;
    Sprite joinMatchSprite;
    Sprite backGroundSprite;
    Texture backGroundTexture;
    Texture createMatchButton;
    Texture joinMatch;
    

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
        batch.dispose();

    }
}
