package com.gameproject.factories;

import com.almasb.fxgl.app.scene.*;
import com.gameproject.Menu.Menu;
import org.jetbrains.annotations.NotNull;

public class MenuFactory extends SceneFactory {
    @NotNull
    @Override
    public FXGLMenu newGameMenu() {
        return new Menu(MenuType.GAME_MENU);
    }

    @NotNull
    @Override
    public FXGLMenu newMainMenu() {
        return new Menu(MenuType.MAIN_MENU);
    }

    @NotNull
    @Override
    public IntroScene newIntro() {
        return super.newIntro();
    }

    @NotNull
    @Override
    public LoadingScene newLoadingScene() {
        return super.newLoadingScene();
    }

    @NotNull
    @Override
    public StartupScene newStartup(int width, int height) {
        return super.newStartup(width, height);
    }
}
