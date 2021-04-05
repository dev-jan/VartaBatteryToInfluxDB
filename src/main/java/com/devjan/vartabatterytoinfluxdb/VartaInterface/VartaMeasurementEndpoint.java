package com.devjan.vartabatterytoinfluxdb.VartaInterface;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class VartaMeasurementEndpoint {
  public Mono<VartaMeasurementModel> getMeasurements(String baseUrl) {
    WebClient client = WebClient.create(baseUrl);

    return client.get().uri("/cgi/ems_data.xml")
      .retrieve()
      .bodyToMono(VartaMeasurementModel.class);
  }
}
