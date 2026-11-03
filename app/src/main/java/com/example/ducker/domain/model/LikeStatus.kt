package com.example.ducker.domain.model

// Le choix fermé des statuts
enum class LikeStatus(val label: String) {
    Like("Like"),
    DisLike("Dislike"),
    UnRanted("Unranted")
}
