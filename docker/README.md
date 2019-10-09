# How to update the base docker image

The base docker image (`jacogeld/coastal-base:latest`) is used for testing.
From time to time it needs to be updated.  Follow these steps:

  1. Edit `Dockerfile-base`
  2. Check that it works by creating a container (`docker run -it coastal-full`) and running
     * `git clone https://github.com/DeepseaPlatform/coastal.git`
     * `cd coastal`
     * `./gradlew check`
  3. Perform a final build:
     * `docker build --tag coastal-base --file Dockerfile-base .`
  4. Re-tag the image:
     * `docker tag coastal-base jacogeld/coastal-base:latest`
  5. Log in to Docker:
     * `docker login --username jacogeld --password-stdin`
  6. Push the image to the repository:
     * `docker push jacogeld/coastal-base:latest`
  
# Steps to create the production image

The full docker image (`jacogeld/coastal-full`) is used for running COASTAL
inside docker.

  * ``docker build --build-arg update=`date '+%s'` --tag coastal-full --file Dockerfile-full .``
  * `docker tag coastal-full jacogeld/coastal-full:latest`
  * `docker login --username jacogeld --password-stdin`
  * `docker push jacogeld/coastal-full:latest`
