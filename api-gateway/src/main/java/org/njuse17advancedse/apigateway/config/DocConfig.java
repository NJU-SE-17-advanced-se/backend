package org.njuse17advancedse.apigateway.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

@Configuration
@Primary
public class DocConfig implements SwaggerResourcesProvider {
  private final RouteLocator routeLocator;

  @Override
  public List<SwaggerResource> get() {
    // excluded service
    Set<String> excludedIds = new HashSet<>();
    excludedIds.add("config-server");

    List<SwaggerResource> resources = new ArrayList<>();
    List<Route> routes = routeLocator.getRoutes();
    routes.forEach(
      route -> {
        String id = route.getId();
        if (!excludedIds.contains(id)) {
          resources.add(
            swaggerResource(
              id,
              route.getFullPath().replace("**", "v3/api-docs"),
              "1.0.0"
            )
          );
        }
      }
    );
    return resources;
  }

  private SwaggerResource swaggerResource(
    String name,
    String location,
    String version
  ) {
    SwaggerResource swaggerResource = new SwaggerResource();
    swaggerResource.setName(name);
    swaggerResource.setLocation(location);
    swaggerResource.setSwaggerVersion(version);
    return swaggerResource;
  }

  public DocConfig(RouteLocator routeLocator) {
    this.routeLocator = routeLocator;
  }
}
