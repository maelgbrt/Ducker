package com.example.ducker.domain.data

import kotlinx.serialization.Serializable

@Serializable
data class LikeDislike(
    val proprioUser: String,
    val targetUser: String
)