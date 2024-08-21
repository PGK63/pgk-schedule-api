package ru.pgk.api_gateway.filters

import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import ru.pgk.auth_service.lib.AdminType
import ru.pgk.auth_service.lib.SecurityServiceGrpc
import ru.pgk.auth_service.lib.UserRole
import ru.pgk.auth_service.lib.ValidateAccessTokenRequest

@Component
class AuthFilter(
    @GrpcClient("security")
    private val securityService: SecurityServiceGrpc.SecurityServiceBlockingStub
): AbstractGatewayFilterFactory<AuthFilter.Config>(Config::class.java) {

    data class Config(
        val routes: List<Route> = emptyList()
    )

    data class Route(
        val name: String,
        val methods: Set<HttpMethod> = emptySet(),
        val roles: Set<UserRole> = emptySet(),
        val adminTypes: Set<AdminType> = emptySet()
    )

    override fun apply(config: Config?): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            config?.let { config ->
                val originalUri = exchange.request.uri.toString()
                val method = exchange.request.method
                config.routes.forEach { route ->
                    if(originalUri.contains(route.name) && (route.methods.isEmpty() || route.methods.contains(method))) {
                        val token = getAuthHeader(exchange.request)
                        if (token != null && isTokenValid(token, route.roles, route.adminTypes)) {
                            return@GatewayFilter chain.filter(exchange)
                        }
                        exchange.response.setStatusCode(HttpStatus.UNAUTHORIZED)
                        return@GatewayFilter exchange.response.setComplete()
                    }
                }
            }
            chain.filter(exchange)
        }
    }

    private fun getAuthHeader(request: ServerHttpRequest): String? {
        val bearer = request.headers.getFirst(HttpHeaders.AUTHORIZATION) ?: return null
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7)
        }
        return null
    }

    private fun isTokenValid(token: String, roles: Set<UserRole>, adminTypes: Set<AdminType>): Boolean {
        val request = ValidateAccessTokenRequest.newBuilder()
            .setAccessToken(token)
            .addAllUserRoles(roles)
            .addAllAdminTypes(adminTypes)
            .build()

        return securityService.validateAccessToken(request).value
    }
}