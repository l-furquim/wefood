resource "aws_security_group" "ecs_sg" {
  name = "ecs-sg"
  description = "Allow acess to gateway microservice"
  vpc_id = aws_vpc.main.id

  ingress {
    from_port = 8086
    to_port = 8086
    protocol = "tcp"
    cidr_blocks = var.sg_cidr
  }

  ingress {
    from_port = 0
    to_port = 0
    protocol = "-1"
    cidr_blocks = [aws_vpc.main.cidr_block]
  }

  egress {
    from_port = 0
    to_port = 0
    protocol = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}