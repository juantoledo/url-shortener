package io.houmlab.urlshortener.model;

public interface Url {

    public String getCode();

    public void setCode(String code);

    public String getDestination();

    public void setDestination(String destination);

    public boolean isEnabled();

    public void setEnabled(boolean enabled);

    public long getExpirationTime();

    public void setExpirationTime(long expirationTime);

}
