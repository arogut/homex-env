package com.arogut.homex.data.service;

import com.arogut.homex.data.repository.DeviceRepository;
import com.arogut.homex.data.model.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Flux<Device> getAll() {
        return Flux.fromIterable(deviceRepository.findAll());
    }

    public Mono<Device> getById(String id) {
        return Mono.justOrEmpty(deviceRepository.findById(id));
    }

    public Mono<Boolean> existsById(String id) {
        return Mono.just(deviceRepository.existsById(id));
    }

    public Mono<Device> add(Device device) {
        return Mono.just(deviceRepository.findByMacAddress(device.getMacAddress())
                .orElse(deviceRepository.save(device)));
    }
}
