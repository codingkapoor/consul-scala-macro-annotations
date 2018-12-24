# consul-scala-macro-annotations

## Start Consul Agent

```
$ consul agent -config-file=config.consul.json &
```

**config.consul.json**
```
{
    "ui": true,
    "retry_join": ["172.20.20.31"],
    "advertise_addr": "172.20.20.1",
    "data_dir": "/tmp/consul",
    "disable_remote_exec": false,
    "enable_script_checks": true
}
```
## Clone Repo
```
$ git clone git@github.com:codingkapoor/scala-macro-annotations-consul-service-registration.git
```

## Build Package
```
$ cd scala-macro-annotations-consul-service-registration
$ sbt> project myservice
$ sbt> universal:packageBin
```

## Start Service
```
$ cd scala-macro-annotations-consul-service-registration/myservice/target/universal
$ unzip scala-macro-annotations-consul-service-registration.zip
$ cd scala-macro-annotations-consul-service-registration
$ bin/start.sh
$ tail -f logs/stdout.log
```

## Direct browser `@localhost:8080`
*Microservices can use `@EnableServiceDiscovery` macro-annotation to register themselves to Consul out of the box.*
