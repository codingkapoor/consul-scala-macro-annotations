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
$ unzip myservice-0.1.0-SNAPSHOT.zip
$ cd myservice-0.1.0-SNAPSHOT
$ bin/start.sh
$ tail -f logs/stdout.log
```

## Direct browser `@localhost:8080`
Root API endpoint should return following string: 
> Microservices can use `@EnableServiceDiscovery` macro-annotation to register themselves to Consul out of the box.

## Consul Health Checks

`curl http://localhost:8500/v1/health/checks/v0.1.0-SNAPSHOT_myservice?pretty`

### Healthy
```
[
    {
        "Node": "inblrlt-shivam",
        "CheckID": "service:2fd993e4931427b89f1bc832a6192ee0",
        "Name": "Service 'v0.1.0-SNAPSHOT_myService' check",
        "Status": "passing",
        "Notes": "",
        "Output": "Sun Dec 23 20:37:06 IST 2018: MyService is running\n",
        "ServiceID": "2fd993e4931427b89f1bc832a6192ee0",
        "ServiceName": "v0.1.0-SNAPSHOT_myService",
        "ServiceTags": [
            "tag1",
            "tag2"
        ],
        "Definition": {},
        "CreateIndex": 13070,
        "ModifyIndex": 13102
    }
]
```

### Unhealthy

```
[
    {
        "Node": "inblrlt-shivam",
        "CheckID": "service:2fd993e4931427b89f1bc832a6192ee0",
        "Name": "Service 'v0.1.0-SNAPSHOT_myService' check",
        "Status": "warning",
        "Notes": "",
        "Output": "Sun Dec 23 20:40:18 IST 2018: MyService is not running\n",
        "ServiceID": "2fd993e4931427b89f1bc832a6192ee0",
        "ServiceName": "v0.1.0-SNAPSHOT_myService",
        "ServiceTags": [
            "tag1",
            "tag2"
        ],
        "Definition": {},
        "CreateIndex": 13070,
        "ModifyIndex": 13124
    }
]
```
