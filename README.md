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

- `IMGPROXY_SIGNED_URL_ENABLED` (boolean)
- `IMGPROXY_KEY` (hex-encoded key) for more information: https://docs.imgproxy.net/signing_the_url
- `IMGPROXY_SALT` (hex-encoded salt) for more information: https://docs.imgproxy.net/signing_the_url
- `IMGPROXY_BASE_URL` (base url to reach your imgproxy service)

Build the module with the command:
```maven
mvn install
```

Install the module on your Jahia environment following the dedicated tutorial on https://academy.jahia.com/training-kb/tutorials/administrators/installing-a-module

## Usage

Activate the module for your Jahia site.
Add the `jahia-optimized` class to the image you want to optimize.

