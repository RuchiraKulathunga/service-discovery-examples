package io.vertx.example.service.discovery;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceDiscoveryOptions;
import io.vertx.servicediscovery.types.HttpEndpoint;

public class Publish extends AbstractVerticle {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(Publish.class.getName());
  }

  @Override
  public void start() {
    ServiceDiscovery discovery = ServiceDiscovery.create(vertx,
                          new ServiceDiscoveryOptions()
                            .setAnnounceAddress("service-announce")
                            .setName("my-name"));

    Record record = new Record()
      .setType("eventbus-service-proxy")
      .setLocation(new JsonObject().put("endpoint", "the-service-address"))
      .setName("my-service")
      .setMetadata(new JsonObject().put("some-label", "some-value"));

    discovery.publish(record, ar -> {
      if (ar.succeeded()) {
        // publication succeeded
        Record publishedRecord = ar.result();
      } else {
        // publication failed
      }
    });

     // Record creation from a type
    record = HttpEndpoint.createRecord("some-rest-api", "localhost", 8080, "/api");
    discovery.publish(record, ar -> {
      if (ar.succeeded()) {
        // publication succeeded
        Record publishedRecord = ar.result();
      } else {
        // publication failed
      }
    });

    discovery.close();
  }
}
