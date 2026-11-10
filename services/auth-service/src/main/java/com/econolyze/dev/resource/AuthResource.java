package com.econolyze.dev.resource;

import com.econolyze.dev.dto.LoginRequestDTO;
import com.econolyze.dev.dto.LoginResponseDTO;
import com.econolyze.dev.dto.RegisterRequestDTO;
import com.econolyze.dev.dto.RegisterResponse;
import com.econolyze.dev.entity.User;
import com.econolyze.dev.exception.InvalidTokenException;
import com.econolyze.dev.exception.WrongCredentialsException;
import com.econolyze.dev.service.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.jboss.resteasy.reactive.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/auth")
public class AuthResource {

    @Inject
    UserService userService;

    @POST
    @Path("/register")
    @PermitAll
    public RestResponse<RegisterResponse> register(@Valid RegisterRequestDTO dto) {
        User user = userService.addUser(dto);
        return RestResponse.ok(new RegisterResponse(
                "Seja bem vindo ao Econolyze!",
                user.getUsername()
        ));
    }

    @POST
    @Path("/login")
    @PermitAll
    public RestResponse<LoginResponseDTO> login(@Valid LoginRequestDTO request) {
        try {
            LoginResponseDTO response = userService.login(request.username(), request.password());
            return RestResponse.ok(response);
        } catch (WebApplicationException e) {
            throw new WrongCredentialsException(e.getMessage());
        }
    }

    @POST
    @Path("/logout")
    @PermitAll
    public RestResponse<Void> logout() {
        try {
            userService.revokeRefreshToken();
            return RestResponse.status(RestResponse.Status.NO_CONTENT);
        } catch (WebApplicationException e) {
            log.warn("Tentativa de logout com token inválido: {}", e.getMessage());
            return RestResponse.status(RestResponse.Status.UNAUTHORIZED);
        } catch (Exception e) {
            log.error("Erro interno catastrófico durante o logout", e);
            return RestResponse.status(RestResponse.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Path("/refresh")
    @PermitAll
    public RestResponse<LoginResponseDTO> refresh() {
        try {
            LoginResponseDTO response = userService.refreshTokenFromJWT();
            return RestResponse.ok(response);
        } catch (WebApplicationException e) {
            throw new InvalidTokenException(e.getMessage());
        }
    }

    @POST
    @RolesAllowed("admin")
    @Path("/admin/login")
    public Response adminLogin(@Context SecurityContext securityContext){
        return Response.ok(null).build();
    }

    @GET
    @Path("/ping")
    public RestResponse<String> hello(){
        try {
            return RestResponse.ok("pong!");
        }catch (Exception e){
            return RestResponse.status(RestResponse.Status.valueOf("HElP"));
        }
    }

    private static final Logger log = LoggerFactory.getLogger(AuthResource.class);
}

