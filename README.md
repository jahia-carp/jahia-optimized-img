# jahia-optimized-img
> A Jahia module that optimized and lazy load images in pages.

## Requirements

This module need imgproxy (imgproxy.net) server set and running

In locale, let’s assume you have Docker installed on your machine. 
Then you can pull an official imgproxy image, and run it.

```docker
docker pull darthsim/imgproxy:latest
docker run -p 8080:8080 -it darthsim/imgproxy
```

## Installation

Download or Fork this project.

Updates the module configuration (JahiaOptimizedImgConstants)

Build the module with the command:
```maven
mvn install
```

Install the module on your Jahia environment following the dedicated tutorial on https://academy.jahia.com/training-kb/tutorials/administrators/installing-a-module
