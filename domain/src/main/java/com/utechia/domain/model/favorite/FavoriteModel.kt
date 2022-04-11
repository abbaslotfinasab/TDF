package com.utechia.domain.model.favorite

import com.utechia.domain.model.refreshment.RefreshmentModel

data class FavoriteModel(
    val createdAt: String?,
    val food: RefreshmentModel?,
    val id: Int?,
    val updatedAt: String?,
)
