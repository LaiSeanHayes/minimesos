package com.containersol.minimesos.config;

public class ConsulConfig extends ContainerConfigBlock implements ContainerConfig {

    public static final String CONSUL_IMAGE_NAME = "consul"
    public static final String CONSUL_TAG_NAME = "0.7.1"

    public static final int CONSUL_HTTP_PORT = 8500
    public static final int CONSUL_DNS_PORT = 8600

    public ConsulConfig() {
        imageName = CONSUL_IMAGE_NAME
        imageTag = CONSUL_TAG_NAME
    }

}
