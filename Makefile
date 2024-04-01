swagger:
	swag init -g "./cmd/main/main.go" -d "./app/server" --pd --parseInternal -o "./app/server/cmd/docs/swagger" -ot "go"
	swag init -g "./cmd/main/main.go" --pd --parseInternal -d "./app/server" -o "./docs/open_api" -ot "json"
