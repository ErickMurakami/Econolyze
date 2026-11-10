package dev.econolyze.application.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(componentModel = "jakarta-cdi", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface QuarkusMapperConfig {
}