package project.kimora.sellerpintar.services.models;

public class ServiceModel {

    private int serviceImage;
    private String serviceName;


    public ServiceModel(int serviceImage, String serviceName) {
        this.serviceImage= serviceImage;
        this.serviceName = serviceName;

    }

    public ServiceModel() {
    }

    public int getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(int serviceImage) {
        this.serviceImage = serviceImage;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String homeTitle) {
        this.serviceName = serviceName;
    }

}