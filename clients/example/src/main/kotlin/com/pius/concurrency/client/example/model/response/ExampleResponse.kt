package com.pius.concurrency.client.example.model.response

import com.pius.concurrency.client.example.spec.response.ExampleResponseDto

data class ExampleResponse(
    val userId: Long,
    val postId: Long,
    val title: String,
    val content: String,
) {
    constructor(result: ExampleResponseDto) : this(
        result.userId,
        result.id,
        result.title,
        result.body
    ) {

    }
}
