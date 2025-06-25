resource "aws_ecs_service" "ecs_service" {
  name            = "wefood-api"
  cluster         = aws_ecs_cluster.ecs_cluster.id
  task_definition = aws_ecs_task_definition.ecs_task.arn
  launch_type     = "FARGATE"
  desired_count   = 1

  network_configuration {
    subnets          = [aws_subnet.public[*].id]
    security_groups  = [aws_security_group.ecs_sg.id]
    assign_public_ip = true
  }

  load_balancer {
    container_name = "wefood-gateway"
    container_port = 8086
    target_group_arn = aws_lb_target_group.gateway_tag.arn
  }

  #depends_on = [aws_lb_listener.http]
}
