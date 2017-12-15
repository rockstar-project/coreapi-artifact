## Template API

#### Download & install development tools:
* [Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
* [Maven](https://maven.apache.org/install.html)
* [Docker Toolbox](https://www.docker.com/templates/docker-toolbox)
* [Spring Tool Suite](https://spring.io/tools/sts)
* [jq](https://stedolan.github.io/jq/)

#### Download Code
Clone git repository

```
	$ git clone https://github.ibm.com/apigility/coreapi-artifact.git
	$ cd coreapi-artifact
```

#### Run Microservices

* Create virtual machine and point to the docker context inside the vm

	```
	$ docker-machine create -d virtualbox --virtualbox-memory 4096 coreapi-artifact-vb-node
	$ eval $(docker-machine env coreapi-artifact-vb-node)
	```

* Start microservice

	```
	$ ./build_and_deploy.sh
	```

* Connect to the API endpoint to verify microservices are up and running

	```
	$ curl -s http://$(docker-machine ip coreapi-artifact-vb-node):8765 | jq .
	```

	```
		"_links": {
			"artifacts": {
				"href": "http://localhost:8080/artifacts{?page,size,sort}",
				"artifactd": true
			},
			"profile": {
				"href": "http://localhost:8080/profile"
			}
		}
	```

#### Develop Code

Import Maven Project using the following steps:

* Inside STS, click through `File->Import->Maven`
* Select `Existing Maven Projects`
* Click `Browse` and navigate to the workspace folder where you had clone the repository in the previous step
* Select `coreapi-artifact` project and click `Open`
