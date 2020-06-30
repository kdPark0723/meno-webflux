package com.ara.memo.handler.user

import com.ara.memo.dto.user.UserView
import com.ara.memo.service.user.UserResource
import com.ara.memo.util.view.ViewProjection
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class ReadUserByIdHandler(
    private val resource: UserResource
) : AbstractReadUserHandler() {
    override fun handleRequest(request: ServerRequest): Mono<ServerResponse> {
        val userId = request.pathVariable("userId")
        val fields = request.queryParams()["fields"] ?: listOf()
        val user = resource.findById(userId)
        return createResponse(
            user.map { UserView.from(it) }
                .map { ViewProjection.of(it, fields) }
        )
    }
}