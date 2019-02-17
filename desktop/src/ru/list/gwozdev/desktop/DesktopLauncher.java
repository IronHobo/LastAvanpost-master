package ru.list.gwozdev.desktop;

import com.MainGame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.list.gwozdev.LastAvanpostGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Avanpost";
		config.width = 1280;
		config.height = 720;
		new LwjglApplication(new MainGame(), config);
	}
}
