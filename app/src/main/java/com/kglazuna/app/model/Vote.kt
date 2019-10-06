package com.kglazuna.app.model

class Vote(var image_id: String, var value: Int) {

    enum class Value(val value: Int) {
        VOTE_UP(1),
        VOTE_DOWN(0)
    }
}
