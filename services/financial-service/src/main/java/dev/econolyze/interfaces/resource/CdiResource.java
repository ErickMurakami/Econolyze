package dev.econolyze.interfaces.resource;

import dev.econolyze.application.dto.CdiRateDTO;
import dev.econolyze.application.services.CdiService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

@Path("/api/cdi")
public class CdiResource {

    @Inject
    CdiService cdiService;

    @GET
    @Path("/rate")
    public RestResponse<CdiRateDTO> getCdiRate() {
        return RestResponse.ok(cdiService.getCdiRateInfo());
    }

    @GET
    @Path("/rate/async")
    public Uni<RestResponse<BigDecimal>> getCdiRateAsync() {
        log.info("Transmitindo a taxa CDI");
        return cdiService.getCurrentCdiRateAsync()
                .onItem().transform(RestResponse::ok)
                .onFailure().recoverWithItem(RestResponse.serverError());
    }

    @GET
    @Path("/info")
    public RestResponse<CdiRateDTO> getCdiInfo() {
        CdiRateDTO info = cdiService.getCdiRateInfo();
        return RestResponse.ok(info);
    }

    @POST
    @Path("/refresh")
    public RestResponse<String> refreshCdiRate() {
        cdiService.updateCdiRate();
        return RestResponse.ok("CDI rate updated");
    }

    private static final Logger log = LoggerFactory.getLogger(CdiResource.class);
}