package com.onemangrove.xstriker

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Graphics

class Main : Game() {
  override fun create() {
    Gdx.graphics.setWindowedMode(750, 1334);
    Gdx.graphics.setFullscreenMode(Gdx.graphics.displayMode)
    setScreen(com.onemangrove.xstriker.PlayMode())
  }
}
