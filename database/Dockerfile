FROM postgres:12-alpine
COPY *.sql /docker-entrypoint-initdb.d/

ENV POSTGRES_DB finance
ENV POSTGRES_USER ChangeMe
ENV POSTGRES_PASSWORD ChangeMe