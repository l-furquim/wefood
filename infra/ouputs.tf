output "vpc_id" {
  value = aws_vpc.main.id
}

output "ecs_security_group_id" {
  value = aws_security_group.ecs_sg.id
}

output "lb_dns" {
  value = aws_lb.gateway_alb.dns_name
}

