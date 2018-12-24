# consul-scala-macro-annotations

## Start Consul

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
$ git clone git@github.com:codingkapoor/consul-scala-macro-annotations.git
```

## Build Package
```
$ cd consul-scala-macro-annotations
$ sbt> project myservice
$ sbt> universal:packageBin
```

## Start Service
```
$ cd consul-scala-macro-annotations/myservice/target/universal
$ unzip consul-scala-macro-annotations.zip
$ cd consul-scala-macro-annotations
$ bin/start.sh
$ tail -f logs/stdout.log
```

## Direct browser `@localhost:8080`
Root API endpoint should return following string: 
> Microservices can use `@EnableServiceDiscovery` macro-annotation to register themselves to Consul out of the box.
