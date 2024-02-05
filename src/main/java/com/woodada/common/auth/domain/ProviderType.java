package com.woodada.common.auth.domain;

public enum ProviderType {

    GOOGLE;

    String getName() {
        return this.name().toLowerCase();
    }
}
