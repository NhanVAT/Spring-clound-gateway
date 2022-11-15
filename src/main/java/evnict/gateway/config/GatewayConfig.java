package evnict.gateway.config;

import evnict.gateway.filter.JwtAuthenticationFilter;
import evnict.gateway.model.ServiceInfo;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import java.time.Duration;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class GatewayConfig {

  private final JwtAuthenticationFilter filter;

  private final Environment environment;

  private ServiceInfo serviceQTHT = new ServiceInfo();
  private ServiceInfo serviceDMuc = new ServiceInfo();
  private ServiceInfo serviceDichVu = new ServiceInfo();
  private ServiceInfo serviceBDongTThao = new ServiceInfo();
  private ServiceInfo serviceChiSoKHang = new ServiceInfo();
  private ServiceInfo sercommon = new ServiceInfo();
  private ServiceInfo serviceHDonPSinh = new ServiceInfo();
  private ServiceInfo serviceHDonDChinh = new ServiceInfo();
  private ServiceInfo serviceHoSoTBi = new ServiceInfo();
  private ServiceInfo serviceHopDong = new ServiceInfo();
  private ServiceInfo serviceInterface = new ServiceInfo();
  private ServiceInfo serviceFileDTu = new ServiceInfo();
  private ServiceInfo serviceBCaoLichSu = new ServiceInfo();
  private ServiceInfo serviceKTraGSatMBD = new ServiceInfo();
  private ServiceInfo serviceBCaoThang = new ServiceInfo();
  private ServiceInfo serviceReport = new ServiceInfo();
  private ServiceInfo serviceTTienCNo = new ServiceInfo();

  public GatewayConfig(JwtAuthenticationFilter filter, Environment environment) {
    this.filter = filter;
    this.environment = environment;

    serviceDichVu.id = environment.getProperty("microservice.serviceDichVu.id");
    serviceDichVu.uri = environment.getProperty("microservice.serviceDichVu.uri");
    serviceDichVu.path = environment.getProperty("microservice.serviceDichVu.path");

    serviceBDongTThao.id = environment.getProperty("microservice.serviceBDongTThao.id");
    serviceBDongTThao.uri = environment.getProperty("microservice.serviceBDongTThao.uri");
    serviceBDongTThao.path = environment.getProperty("microservice.serviceBDongTThao.path");

    serviceChiSoKHang.id = environment.getProperty("microservice.serviceChiSoKHang.id");
    serviceChiSoKHang.uri = environment.getProperty("microservice.serviceChiSoKHang.uri");
    serviceChiSoKHang.path = environment.getProperty("microservice.serviceChiSoKHang.path");

    sercommon.id = environment.getProperty("microservice.sercommon.id");
    sercommon.uri = environment.getProperty("microservice.sercommon.uri");
    sercommon.path = environment.getProperty("microservice.sercommon.path");

    serviceHDonPSinh.id = environment.getProperty("microservice.serviceHDonPSinh.id");
    serviceHDonPSinh.uri = environment.getProperty("microservice.serviceHDonPSinh.uri");
    serviceHDonPSinh.path = environment.getProperty("microservice.serviceHDonPSinh.path");

    serviceHDonDChinh.id = environment.getProperty("microservice.serviceHDonDChinh.id");
    serviceHDonDChinh.uri = environment.getProperty("microservice.serviceHDonDChinh.uri");
    serviceHDonDChinh.path = environment.getProperty("microservice.serviceHDonDChinh.path");

    serviceHoSoTBi.id = environment.getProperty("microservice.serviceHoSoTBi.id");
    serviceHoSoTBi.uri = environment.getProperty("microservice.serviceHoSoTBi.uri");
    serviceHoSoTBi.path = environment.getProperty("microservice.serviceHoSoTBi.path");

    serviceHopDong.id = environment.getProperty("microservice.serviceHopDong.id");
    serviceHopDong.uri = environment.getProperty("microservice.serviceHopDong.uri");
    serviceHopDong.path = environment.getProperty("microservice.serviceHopDong.path");

    serviceInterface.id = environment.getProperty("microservice.serviceInterface.id");
    serviceInterface.uri = environment.getProperty("microservice.serviceInterface.uri");
    serviceInterface.path = environment.getProperty("microservice.serviceInterface.path");

    serviceFileDTu.id = environment.getProperty("microservice.serviceFileDTu.id");
    serviceFileDTu.uri = environment.getProperty("microservice.serviceFileDTu.uri");
    serviceFileDTu.path = environment.getProperty("microservice.serviceFileDTu.path");

    serviceBCaoLichSu.id = environment.getProperty("microservice.serviceBCaoLichSu.id");
    serviceBCaoLichSu.uri = environment.getProperty("microservice.serviceBCaoLichSu.uri");
    serviceBCaoLichSu.path = environment.getProperty("microservice.serviceBCaoLichSu.path");

    serviceKTraGSatMBD.id = environment.getProperty("microservice.serviceKTraGSatMBD.id");
    serviceKTraGSatMBD.uri = environment.getProperty("microservice.serviceKTraGSatMBD.uri");
    serviceKTraGSatMBD.path = environment.getProperty("microservice.serviceKTraGSatMBD.path");

    serviceBCaoThang.id = environment.getProperty("microservice.serviceBCaoThang.id");
    serviceBCaoThang.uri = environment.getProperty("microservice.serviceBCaoThang.uri");
    serviceBCaoThang.path = environment.getProperty("microservice.serviceBCaoThang.path");

    serviceReport.id = environment.getProperty("microservice.serviceReport.id");
    serviceReport.uri = environment.getProperty("microservice.serviceReport.uri");
    serviceReport.path = environment.getProperty("microservice.serviceReport.path");

    serviceQTHT.id = environment.getProperty("microservice.serviceQTHT.id");
    serviceQTHT.uri = environment.getProperty("microservice.serviceQTHT.uri");
    serviceQTHT.path = environment.getProperty("microservice.serviceQTHT.path");

    serviceDMuc.id = environment.getProperty("microservice.serviceDMuc.id");
    serviceDMuc.uri = environment.getProperty("microservice.serviceDMuc.uri");
    serviceDMuc.path = environment.getProperty("microservice.serviceDMuc.path");

    serviceTTienCNo.id = environment.getProperty("microservice.serviceTTienCNo.id");
    serviceTTienCNo.uri = environment.getProperty("microservice.serviceTTienCNo.uri");
    serviceTTienCNo.path = environment.getProperty("microservice.serviceTTienCNo.path");
  }

  @Bean
  public ReactiveResilience4JCircuitBreakerFactory reactiveResilience4JCircuitBreakerFactory(
      CircuitBreakerRegistry circuitBreakerRegistry) {
    ReactiveResilience4JCircuitBreakerFactory reactiveResilience4JCircuitBreakerFactory = new ReactiveResilience4JCircuitBreakerFactory();
    reactiveResilience4JCircuitBreakerFactory.configureCircuitBreakerRegistry(
        circuitBreakerRegistry);

    TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
        .timeoutDuration(Duration.ofSeconds(2)).cancelRunningFuture(true).build();

    reactiveResilience4JCircuitBreakerFactory.configure(
        builder -> builder.timeLimiterConfig(timeLimiterConfig).build(), serviceDichVu.id,
        serviceBDongTThao.id, serviceChiSoKHang.id, sercommon.id, serviceDMuc.id,
        serviceHDonPSinh.id, serviceHDonDChinh.id, serviceHoSoTBi.id, serviceHopDong.id,
        serviceQTHT.id, serviceInterface.id, serviceFileDTu.id, serviceBCaoLichSu.id,
        serviceKTraGSatMBD.id, serviceBCaoThang.id, serviceReport.id, serviceTTienCNo.id);
    return reactiveResilience4JCircuitBreakerFactory;
  }

  @Bean
  public RouteLocator routes(RouteLocatorBuilder builder) {
    return builder.routes().route(serviceDichVu.id,
            r -> r.path(serviceDichVu.path).filters(f -> f.filter(filter)).uri(serviceDichVu.uri))
        .route(serviceBDongTThao.id,
            r -> r.path(serviceBDongTThao.path).filters(f -> f.filter(filter))
                .uri(serviceBDongTThao.uri)).route(serviceChiSoKHang.id,
            r -> r.path(serviceChiSoKHang.path).filters(f -> f.filter(filter))
                .uri(serviceChiSoKHang.uri)).route(sercommon.id,
            r -> r.path(sercommon.path).filters(f -> f.filter(filter)).uri(sercommon.uri))
        .route(serviceDMuc.id,
            r -> r.path(serviceDMuc.path).filters(f -> f.filter(filter)).uri(serviceDMuc.uri))
        .route(serviceHDonPSinh.id,
            r -> r.path(serviceHDonPSinh.path).filters(f -> f.filter(filter))
                .uri(serviceHDonPSinh.uri)).route(serviceHDonDChinh.id,
            r -> r.path(serviceHDonDChinh.path).filters(f -> f.filter(filter))
                .uri(serviceHDonDChinh.uri)).route(serviceHoSoTBi.id,
            r -> r.path(serviceHoSoTBi.path).filters(f -> f.filter(filter)).uri(serviceHoSoTBi.uri))
        .route(serviceHopDong.id,
            r -> r.path(serviceHopDong.path).filters(f -> f.filter(filter)).uri(serviceHopDong.uri))
        .route(serviceQTHT.id,
            r -> r.path(serviceQTHT.path).filters(f -> f.filter(filter)).uri(serviceQTHT.uri))
        .route(serviceInterface.id,
            r -> r.path(serviceInterface.path).filters(f -> f.filter(filter))
                .uri(serviceInterface.uri)).route(serviceFileDTu.id,
            r -> r.path(serviceFileDTu.path).filters(f -> f.filter(filter)).uri(serviceFileDTu.uri))
        .route(serviceBCaoLichSu.id,
            r -> r.path(serviceBCaoLichSu.path).filters(f -> f.filter(filter))
                .uri(serviceBCaoLichSu.uri)).route(serviceKTraGSatMBD.id,
            r -> r.path(serviceKTraGSatMBD.path).filters(f -> f.filter(filter))
                .uri(serviceKTraGSatMBD.uri)).route(serviceBCaoThang.id,
            r -> r.path(serviceBCaoThang.path).filters(f -> f.filter(filter))
                .uri(serviceBCaoThang.uri)).route(serviceReport.id,
            r -> r.path(serviceReport.path).filters(f -> f.filter(filter)).uri(serviceReport.uri))
        .route(serviceTTienCNo.id, r -> r.path(serviceTTienCNo.path).filters(f -> f.filter(filter))
            .uri(serviceTTienCNo.uri)).build();
  }
}
