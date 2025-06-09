To run the program, you need docker installed

Instructions:
Run on terminal the following commands

docker build -t cost-matrix-service .

docker run -d -p 7070:7070 --name cost-matrix-service cost-matrix-service

docker-compose up -d
