package com.utechia.domain.enum

enum class OrderEnum(val order: String) {

    ID("orderId"),
    Type("type"),
    Pending("pending"),
    Cancel("cancelled"),
    Delivered("delivered"),
    Wait("waiting"),
    Prepare("preparing"),
    UserCancel("cancelled_by_user"),
    TeaBoyCancel("cancelled_by_teaboy")

}