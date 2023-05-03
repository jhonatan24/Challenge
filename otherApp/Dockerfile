FROM golang:1.19
WORKDIR /app
COPY go.mod go.sum ./
RUN go mod download
COPY *.go ./
ENV PORT 8090
RUN CGO_ENABLED=0 GOOS=linux go build -o main .
EXPOSE $PORT

# Run
CMD ["/app/main"]