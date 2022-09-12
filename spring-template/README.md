Build with
```
docker build --tag=spring-template:latest .
```

After the image builds successfully, run a container from that image.
```
docker run --name spring-template -p8080:8080 spring-template:latest
```

When you are done testing, stop the server and remove the container.
```
docker rm -f spring-template
```
The POST body should be a raw JSON