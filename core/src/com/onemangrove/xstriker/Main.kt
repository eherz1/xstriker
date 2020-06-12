package com.onemangrove.xstriker

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx

class Main : Game() {

    override fun create() {
        Gdx.graphics.setWindowedMode(1024, 576);
        setScreen(com.onemangrove.xstriker.PlayMode())
    }

}
