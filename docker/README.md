# How to update the base docker image

The base docker image (`jacogeld/coastal-base:latest`) is used for testing.
From time to time it needs to be updated.  Follow these steps:

  1. Edit `Dockerfile-base`
  2. Check that it works by creating a container and running
    * `git clone https://github.com/DeepseaPlatform/coastal.git`
    * `cd coastal`
    * `./gradlew check`
  3. Perform a final build:
    * `docker build . -t coastal-local -f Dockerfile-base`
  4. Re-tag the image:
    * `docker tag coastal-local jacogeld/coastal-base:latests`
  5. Log in to Docker:
    * `docker login --username jacogeld --password '...'`
  6. Push the image to the repository:
    * `docker push jacogeld/coastal-base:latest`
  