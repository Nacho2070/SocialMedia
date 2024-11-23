FROM ubuntu:latest
LABEL authors="Nacho Ariza"

ENTRYPOINT ["top", "-b"]