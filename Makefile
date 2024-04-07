swagger:
	cd module/server \
	&& make swagger \
	&& cd ../.. \
	&& pwd \
	&& swag init -g "./module/server/cmd/main/main.go" --pd --parseInternal -o "./docs/api" -ot "json"
macos-build:
	cd module/server && go mod tidy && go build -o ../../build/ticket-place-server ./cmd/main/main.go && cd ../..
