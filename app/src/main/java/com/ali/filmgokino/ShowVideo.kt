package com.ali.filmgokino

import android.os.Bundle
import com.google.android.youtube.player.*

class ShowVideo : YouTubeBaseActivity() {
    var Key_User: String = "AIzaSyBDmvKkbYNOsTM3QuY6zOI4rgP8_bcMBlw"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_video)

        var VideoId = intent.getStringExtra("id")
        var aaaa = findViewById<YouTubePlayerView>(R.id.youtube_fragment)
        aaaa.initialize(
            Key_User,
            object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider?,
                    player: YouTubePlayer?,
                    p2: Boolean
                ) {
                    if (player == null) {
                        return
                    }
                    if (p2) {
                        player.play()
                    } else {
                        player.loadVideo(VideoId)
                        player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
                    }
                }

                override fun onInitializationFailure(
                    p0: YouTubePlayer.Provider?,
                    p1: YouTubeInitializationResult?
                ) {

                }
            })
    }
}
