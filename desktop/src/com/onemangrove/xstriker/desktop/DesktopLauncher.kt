package com.onemangrove.xstriker.desktop

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.onemangrove.xstriker.Main

class DesktopLauncher {

    companion object {

        @JvmStatic
        fun main(arg: Array<String>?) {
            val config = Lwjgl3ApplicationConfiguration()
            Lwjgl3Application(com.onemangrove.xstriker.Main(), config)
        }

    }

}
