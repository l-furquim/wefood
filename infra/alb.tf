resource "aws_lb" "gateway_alb" {
  name = "gateway-alb"
  internal = false
  load_balancer_type = "application"
  subnets = aws_subnet.public[*].id
  security_groups = [aws_security_group.ecs_sg.id]

  tags = {
    Enviroment = var.env
  }

}

resource "aws_lb_target_group" "gateway_tg" {
  name = "gateway-tg"
  port = 8086
  protocol = "HTTP"
  vpc_id = aws_vpc.main.id

  health_check {
    path = "/actuator/health"
    interval = 30
    timeout = 5
    healthy_threshold = 2
    unhealthy_threshold = 2
    matcher = "200-399"
  }

}

resource "aws_lb_listener" "gateway_listener" {
  load_balancer_arn = aws_lb.gateway_alb.arn
  port = 8086
  protocol = "HTTP"

  default_action {
    type = "forward"
    target_group_arn = aws_lb_target_group.gateway_tg.arn
  }

}