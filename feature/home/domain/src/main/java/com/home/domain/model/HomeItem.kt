package com.home.domain.model

data class HomeItem(
    val id: String,
    val title: String,
    val description: String
)

val dummyHomeItems = listOf(
    HomeItem(
        id = "1",
        title = "Item 1",
        description = "This is the first item in the list. It contains some interesting content."
    ),
    HomeItem(
        id = "2",
        title = "Item 2",
        description = "This is the second item. It has different content than the first one."
    ),
    HomeItem(
        id = "3",
        title = "Item 3",
        description = "The third item showcases another example with unique information."
    ),
    HomeItem(
        id = "4",
        title = "Item 4",
        description = "Item number four provides additional details and context."
    ),
    HomeItem(
        id = "5",
        title = "Item 5",
        description = "Fifth item demonstrates variety in the list of items."
    ),
    HomeItem(
        id = "6",
        title = "Item 6",
        description = "The sixth item completes our sample data set."
    ),
    HomeItem(
        id = "7",
        title = "Item 7",
        description = "Item number seven provides additional details and context."
    ),
    HomeItem(
        id = "8",
        title = "Item 8",
        description = "Item number eight provides additional details and context."
    ),
    HomeItem(
        id = "9",
        title = "Item 9",
        description = "Item number nine provides additional details and context."
    )
)