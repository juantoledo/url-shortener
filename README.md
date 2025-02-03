# Houmlab URL Shortener

Educational and self-hosted URL shortener written in Java / Spring MVC. This project serves as an exercise to demonstrate some fundamental development concepts:

### Dev Containers

This project can be easily deployed using the [Dev Containers](https://containers.dev/) definition.

### Golden Signals

The most important indicators of a service's health:

- **Latency**: The time it takes to process a request. It is important to distinguish between the latency of successful requests and the latency of failed requests.

- **Traffic**: The demand placed on your system, often measured in requests per second.

- **Errors**: The rate of failed requests. These failures could be due to the system (e.g., a 500 Internal Server Error), the client (e.g., a 400 Bad Request), or an explicit error (e.g., "Could not connect to the database").

- **Saturation**: How "full" your service is. This is a measure of system utilization. If the system is at 100% utilization, it is saturated.

https://sre.google/sre-book/monitoring-distributed-systems/#xref_monitoring_golden-signals

This project includes all the necessary configurations to export its usage metrics to a Prometheus server. These metrics are then consumed by Grafana.

### OpenAPI

OpenAPI is a specification that allows describing a service’s capabilities in a standardized way. Some annotations are included to demonstrate the use of this spec.

### Docker Compose

A stack is included, which is created when launching the project with Dev Containers. This stack contains everything necessary for developing and using this service locally, including MongoDB, Redis, Grafana, and Prometheus.

# Usage

The project includes a Dev Containers definition that sets up the development environment, MongoDB for storage, Redis for caching, and Grafana and Prometheus for visualization if needed.

### Required Software on the Local Machine with Dev Containers

- [Docker](https://www.docker.com/products/docker-desktop/)

- [Visual Studio Code (recommended for seamless Dev Containers integration)](https://code.visualstudio.com/)

- [Dev Containers Extension for VSCode](https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-containers)

Once these components are installed on the local machine, simply open the project directory. VSCode will automatically detect the presence of the `.devcontainer` directory and suggest opening the repository and infrastructure in an isolated container.

**A `launch.json` file is included, which instructs VSCode on how to start the app, already configured with the local parameters required for Spring's bootstrap:**

![](docs/launch.png)

You only need to call the various API operations defined in SwaggerUI.

## Endpoints

### OpenAPI Definition (Swagger UI)

Contains the API definition with its operations in a SwaggerUI instance.

http://localhost:8080/swagger-ui/index.html  

![](/docs/openapi.png)

### Metrics

#### Prometheus Exporter

Metrics are exported at the following endpoint:

http://localhost:8080/actuator/prometheus  

Currently exported metrics:

```
management.endpoints.web.exposure.include=health,info,prometheus
management.metrics.export.prometheus.enabled=true
management.metrics.enable.spring.mvc=true
management.metrics.web.server.auto-time-requests=true
management.metrics.enable.logback=true
```


The above configuration allows collecting metrics such as response times, access logs, response codes, and error logs, in addition to all system metrics (CPU, memory, etc.).

#### Prometheus Server

http://localhost:9090/

![](/docs/prometheus.png)

#### Grafana Server UI

http://localhost:3000/

#### Golden Signals Dashboard

This project includes a predefined dashboard that can be enriched and adapted to specific needs. Simply download [the file included in this repository](/telemetry/houmlab-golden-signals-dashboard.json) and import it into Grafana.

![dashboard](docs/grafana.png)