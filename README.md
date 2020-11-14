# Flexproxy

# Building the software

## Prerequisites 

- JDK 11 and up
- Maven 3.6 and up

## Steps

- Clone this repo
- Type `mvn clean install`

# Running the application

Type `mvn -pl application exec:java`

# What is it for?

This is not a proxy. It can be used as a proxy but:

- You can alter specific requests to return a different status code.
- You can let it return a specific file to alter the response you would normally get from a specific service.

# Configuration

Example configuration

```xml
<configuration>
  <!-- Port the application runs on -->
  <port>8080</port>
  <services>
    <service>
      <!-- Starting point of the service -->
      <name>XX</name>
      <mountpoint>api/service</mountpoint>
      <proxy_url>[[ANY URL]]</proxy_url>
      <!-- Log all data passing through -->
      <debug>true</debug>
      <endpoints>
        <endpoint>
          <method>GET</method>
          <url match="endswith">/4</url>
          <!-- Return a different status -->
          <status>
            <status_code>404</status_code>
            <message>Not found</message>
          </status>
        </endpoint>
        <endpoint>
          <method>GET</method>
          <url match="contains">/7</url>
          <!-- Return a different status -->
          <status>
            <status_code>500</status_code>
            <message>Oeps</message>
          </status>
        </endpoint>
        <endpoint>
          <method>GET</method>
          <url>/5</url>
          <!-- Return a different response -->
          <static_file>
            <content_type>text/json</content_type>
            <file_name>/full/path/to/file.json</file_name>
          </static_file>
        </endpoint>
      </endpoints>
    </service>
  </services>
</configuration>
```
The `match` attribute on `url` can contain the following values:
 
 * equals - Unspecified match will be this and the entire URL after the mountpoint must match.
 * endswith - the end of the url must be the same.
 * startswith - the start of the url must match.
 * contains - the given url must be a part of the url requested.

# What's next?

* Test more
* Try some stuff with other methods apart from GET
* Add regular expression handling for specific use cases