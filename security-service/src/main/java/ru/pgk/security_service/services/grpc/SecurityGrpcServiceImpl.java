package ru.pgk.security_service.services.grpc;

import com.google.protobuf.BoolValue;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.pgk.security_service.jwt.JwtTokenProvider;
import ru.pgk.security_service.lib.SecurityServiceGrpc;
import ru.pgk.security_service.lib.ValidateAccessTokenRequest;

@GrpcService
@RequiredArgsConstructor
public class SecurityGrpcServiceImpl extends SecurityServiceGrpc.SecurityServiceImplBase {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void validateAccessToken(ValidateAccessTokenRequest request, StreamObserver<BoolValue> responseObserver) {
        // Проверяем, действителен ли токен
        boolean isValidAccessToken = jwtTokenProvider.validateAccessToken(request.getAccessToken());

        String userRole = jwtTokenProvider.getUserRole(request.getAccessToken());
        String adminType = jwtTokenProvider.getAdminType(request.getAccessToken());

        boolean isValidUserRole = request.getUserRolesList().isEmpty() ||
                request.getUserRolesList().stream().anyMatch(role -> role.name().equals(userRole));
        boolean isValidAdminType = request.getAdminTypesList().isEmpty() ||
                request.getAdminTypesList().stream().anyMatch(type -> type.name().equals(adminType));

        boolean isValid = isValidAccessToken && isValidUserRole && isValidAdminType;
        responseObserver.onNext(BoolValue.newBuilder().setValue(isValid).build());
        responseObserver.onCompleted();
    }
}
